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
 * 学生管理面板类，用于展示、操作学生信息。
 * 提供了增删改查以及权限控制功能。
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

    /**
     * 构造方法，初始化学生管理面板并加载数据。
     *
     * @param studentService 学生服务对象，提供对学生数据的操作接口
     * @param permissionService 权限服务对象，用于判断当前用户是否有读写权限
     * @param authService 认证服务对象，用于获取当前登录用户的信息
     */
    public StudentManagementPanel(StudentService studentService,
                                  PermissionService permissionService,
                                  AuthService authService) {
        this.studentService = studentService;
        this.permissionService = permissionService;
        this.authService = authService;
        initializeUI();
        loadStudentData();
    }

    /**
     * 初始化界面组件布局及事件绑定。
     */
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 创建顶部搜索栏面板
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("学号搜索:"));
        searchField = new JTextField(15);
        searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> handleSearch());
        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        // 表格模型设置，列名定义，并禁用编辑功能
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

        // 按钮区域创建与事件绑定
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

        // 根据用户权限更新按钮状态
        updateButtonPermissions();
    }

    /**
     * 更新按钮的可用性状态，根据当前用户的权限决定是否启用相关操作按钮。
     */
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

    /**
     * 加载所有学生数据到表格中。
     */
    private void loadStudentData() {
        try {
            tableModel.setRowCount(0); // 清空现有数据
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

    /**
     * 处理搜索请求，按学号查找特定学生。
     */
    private void handleSearch() {
        String studentNo = searchField.getText().trim();
        if (studentNo.isEmpty()) {
            loadStudentData(); // 若输入为空则重新加载全部数据
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

    /**
     * 打开添加学生的对话框。
     */
    private void handleAdd() {
        StudentDialog dialog = new StudentDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            null,
            studentService
        );
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            loadStudentData(); // 添加成功后刷新列表
        }
    }

    /**
     * 编辑选中的学生信息。
     */
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
                loadStudentData(); // 修改成功后刷新列表
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "获取学生信息失败: " + e.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 删除选中的学生记录。
     */
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
                    loadStudentData(); // 删除成功后刷新列表
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                    "删除失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 查看选中学生的详细信息。
     */
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
