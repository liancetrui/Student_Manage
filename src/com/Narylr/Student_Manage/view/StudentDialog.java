package com.Narylr.Student_Manage.view;

import com.Narylr.Student_Manage.model.Student;
import com.Narylr.Student_Manage.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Student Add/Edit Dialog
 */
public class StudentDialog extends JDialog {
    private final StudentService studentService;
    private final Student existingStudent;
    private boolean confirmed = false;

    private JTextField studentNoField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField gradeField;
    private JTextField telephoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JButton saveButton;
    private JButton cancelButton;

    public StudentDialog(Frame parent, Student student, StudentService studentService) {
        super(parent, student == null ? "新增学生" : "修改学生", true);
        this.existingStudent = student;
        this.studentService = studentService;
        initializeUI();
        
        if (student != null) {
            populateFields(student);
        }
    }

    private void initializeUI() {
        setSize(450, 450);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel(
            existingStudent == null ? "新增学生信息" : "修改学生信息", 
            SwingConstants.CENTER
        );
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Student No
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("学号:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        studentNoField = new JTextField(20);
        studentNoField.setEditable(existingStudent == null); // Only editable for new students
        formPanel.add(studentNoField, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("姓名:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Age
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("年龄:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        ageField = new JTextField(20);
        formPanel.add(ageField, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("性别:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        genderField = new JTextField(20);
        formPanel.add(genderField, gbc);

        // Grade
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("年级:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gradeField = new JTextField(20);
        formPanel.add(gradeField, gbc);

        // Telephone
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("电话:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        telephoneField = new JTextField(20);
        formPanel.add(telephoneField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("邮箱:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("地址:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        addressField = new JTextField(20);
        formPanel.add(addressField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        saveButton = new JButton("保存");
        saveButton.setPreferredSize(new Dimension(80, 30));
        saveButton.addActionListener(e -> handleSave());

        cancelButton = new JButton("取消");
        cancelButton.setPreferredSize(new Dimension(80, 30));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void populateFields(Student student) {
        studentNoField.setText(student.getStudentNo());
        nameField.setText(student.getName());
        ageField.setText(student.getAge() != null ? student.getAge().toString() : "");
        genderField.setText(student.getGender());
        gradeField.setText(student.getGrade() != null ? student.getGrade().toString() : "");
        telephoneField.setText(student.getTelephone());
        emailField.setText(student.getEmail());
        addressField.setText(student.getAddress());
    }

    private void handleSave() {
        // Validation
        String studentNo = studentNoField.getText().trim();
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String gender = genderField.getText().trim();
        String gradeStr = gradeField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (studentNo.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "学号和姓名不能为空", 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parse integer fields
        Integer age = null;
        Integer grade = null;

        try {
            if (!ageStr.isEmpty()) {
                age = Integer.parseInt(ageStr);
            }
            if (!gradeStr.isEmpty()) {
                grade = Integer.parseInt(gradeStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "年龄和年级必须是数字", 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create student object
        Student student = new Student(studentNo, name, age, gender, grade, telephone, email, address);

        try {
            boolean success;
            if (existingStudent == null) {
                // Add new student
                success = studentService.addStudent(student);
            } else {
                // Update existing student
                success = studentService.updateStudent(student);
            }

            if (success) {
                confirmed = true;
                JOptionPane.showMessageDialog(this, 
                    existingStudent == null ? "新增成功" : "修改成功", 
                    "成功", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "操作失败: " + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
