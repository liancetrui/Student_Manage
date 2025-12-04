package com.Narylr.Student_Manage.view;

import com.Narylr.Student_Manage.model.Student;
import com.Narylr.Student_Manage.service.AuthService;
import com.Narylr.Student_Manage.service.PermissionService;
import com.Narylr.Student_Manage.service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Student Management Panel
 */
public class StudentManagementPanel extends JPanel {
    private final StudentService studentService;
    private final PermissionService permissionService;
    private final AuthService authService;
    
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton viewButton;
    private JTextField searchField;
    private JButton searchButton;

    public StudentManagementPanel(StudentService studentService, 
                                  PermissionService permissionService,
                                  AuthService authService) {
        this.studentService = studentService;
        this.permissionService = permissionService;
        this.authService = authService;
        initializeUI();
        loadStudentData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel with search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("学号搜索:"));
        searchField = new JTextField(15);
        searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> handleSearch());
        topPanel.add(searchField);
        topPanel.add(searchButton);
        
        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"学号", "姓名", "年龄", "性别", "年级", "电话", "邮箱", "地址"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        addButton = new JButton("新增学生");
        addButton.addActionListener(e -> handleAdd());
        
        editButton = new JButton("修改学生");
        editButton.addActionListener(e -> handleEdit());
        
        deleteButton = new JButton("删除学生");
        deleteButton.addActionListener(e -> handleDelete());
        
        viewButton = new JButton("查看详情");
        viewButton.addActionListener(e -> handleView());
        
        refreshButton = new JButton("刷新");
        refreshButton.addActionListener(e -> loadStudentData());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Set button permissions
        updateButtonPermissions();
    }

    private void updateButtonPermissions() {
        try {
            boolean isAdmin = authService.isCurrentUserAdmin();
            boolean hasWritePermission = isAdmin || 
                permissionService.hasWritePermission(authService.getCurrentUser().getUserId());
            boolean hasReadPermission = isAdmin || 
                permissionService.hasReadPermission(authService.getCurrentUser().getUserId());

            addButton.setEnabled(hasWritePermission);
            editButton.setEnabled(hasWritePermission);
            deleteButton.setEnabled(hasWritePermission);
            viewButton.setEnabled(hasReadPermission);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentData() {
        try {
            tableModel.setRowCount(0);
            List<Student> students = studentService.getAllStudents();
            
            for (Student student : students) {
                Object[] row = {
                    student.getStudentNo(),
                    student.getName(),
                    student.getAge(),
                    student.getGender(),
                    student.getGrade(),
                    student.getTelephone(),
                    student.getEmail(),
                    student.getAddress()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "加载学生数据失败: " + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void handleSearch() {
        String studentNo = searchField.getText().trim();
        if (studentNo.isEmpty()) {
            loadStudentData();
            return;
        }

        try {
            Student student = studentService.getStudent(studentNo);
            tableModel.setRowCount(0);
            
            if (student != null) {
                Object[] row = {
                    student.getStudentNo(),
                    student.getName(),
                    student.getAge(),
                    student.getGender(),
                    student.getGrade(),
                    student.getTelephone(),
                    student.getEmail(),
                    student.getAddress()
                };
                tableModel.addRow(row);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "未找到学号为 " + studentNo + " 的学生", 
                    "提示", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "搜索失败: " + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAdd() {
        StudentDialog dialog = new StudentDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), 
            null, 
            studentService
        );
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadStudentData();
        }
    }

    private void handleEdit() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "请先选择要修改的学生", 
                "提示", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentNo = (String) tableModel.getValueAt(selectedRow, 0);
        
        try {
            Student student = studentService.getStudent(studentNo);
            StudentDialog dialog = new StudentDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), 
                student, 
                studentService
            );
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                loadStudentData();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "获取学生信息失败: " + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "请先选择要删除的学生", 
                "提示", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentNo = (String) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "确定要删除学生 " + studentName + " (学号: " + studentNo + ") 吗？",
            "确认删除",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = studentService.deleteStudent(studentNo);
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "删除成功", 
                        "成功", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadStudentData();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "删除失败: " + e.getMessage(), 
                    "错误", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleView() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "请先选择要查看的学生", 
                "提示", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentNo = (String) tableModel.getValueAt(selectedRow, 0);
        
        try {
            Student student = studentService.getStudent(studentNo);
            if (student != null) {
                String details = String.format(
                    "学号: %s\n姓名: %s\n年龄: %s\n性别: %s\n年级: %s\n电话: %s\n邮箱: %s\n地址: %s",
                    student.getStudentNo(),
                    student.getName(),
                    student.getAge(),
                    student.getGender(),
                    student.getGrade(),
                    student.getTelephone(),
                    student.getEmail(),
                    student.getAddress()
                );
                
                JOptionPane.showMessageDialog(this, 
                    details, 
                    "学生详细信息", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "获取学生信息失败: " + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
