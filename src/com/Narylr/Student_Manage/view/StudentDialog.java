package com.Narylr.Student_Manage.view;

import com.Narylr.Student_Manage.model.Student;
import com.Narylr.Student_Manage.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * 学生信息添加/编辑对话框
 * <p>
 * 此对话框用于添加新学生或编辑现有学生的详细信息。根据传入的 Student 对象是否为空，
 * 来判断当前是新增还是编辑模式。
 * </p>
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

    /**
     * 构造方法，初始化学生对话框
     *
     * @param parent         父窗口（Frame）
     * @param student        要编辑的学生对象，若为 null 表示新增学生
     * @param studentService 学生服务类实例，用于执行数据库操作
     */
    public StudentDialog(Frame parent, Student student, StudentService studentService) {
        super(parent, student == null ? "新增学生" : "修改学生", true);
        this.existingStudent = student;
        this.studentService = studentService;
        initializeUI();

        if (student != null) {
            populateFields(student);
        }
    }

    /**
     * 初始化用户界面组件
     * 包括标题、表单字段以及按钮等控件，并设置布局与事件监听器
     */
    private void initializeUI() {
        setSize(450, 450);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 标题标签
        JLabel titleLabel = new JLabel(
            existingStudent == null ? "新增学生信息" : "修改学生信息",
            SwingConstants.CENTER
        );
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 学号输入框
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("学号:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        studentNoField = new JTextField(20);
        studentNoField.setEditable(existingStudent == null); // 只有在新增时才允许编辑学号
        formPanel.add(studentNoField, gbc);

        // 姓名输入框
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("姓名:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // 年龄输入框
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("年龄:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        ageField = new JTextField(20);
        formPanel.add(ageField, gbc);

        // 性别输入框
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("性别:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        genderField = new JTextField(20);
        formPanel.add(genderField, gbc);

        // 年级输入框
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("年级:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gradeField = new JTextField(20);
        formPanel.add(gradeField, gbc);

        // 电话输入框
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("电话:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        telephoneField = new JTextField(20);
        formPanel.add(telephoneField, gbc);

        // 邮箱输入框
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("邮箱:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // 地址输入框
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("地址:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        addressField = new JTextField(20);
        formPanel.add(addressField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 按钮面板
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

    /**
     * 将已有学生的信息填充到各个输入字段中
     *
     * @param student 已存在的学生对象
     */
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

    /**
     * 处理“保存”按钮点击事件
     * 进行数据校验并调用服务层进行数据库操作（新增或更新）
     */
    private void handleSave() {
        // 获取所有输入字段的内容并去除前后空格
        String studentNo = studentNoField.getText().trim();
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String gender = genderField.getText().trim();
        String gradeStr = gradeField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        // 必填项验证：学号和姓名不能为空
        if (studentNo.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "学号和姓名不能为空",
                "错误",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 解析整数类型字段（年龄和年级），处理可能发生的格式异常
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

        // 创建新的学生对象
        Student student = new Student(studentNo, name, age, gender, grade, telephone, email, address);

        try {
            boolean success;
            if (existingStudent == null) {
                // 新增学生记录
                success = studentService.addStudent(student);
            } else {
                // 更新已有的学生记录
                success = studentService.updateStudent(student);
            }

            if (success) {
                confirmed = true;
                JOptionPane.showMessageDialog(this,
                    existingStudent == null ? "新增成功" : "修改成功",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose(); // 关闭对话框
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "操作失败: " + e.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 判断本次操作是否成功提交（即用户点击了保存且操作成功）
     *
     * @return 如果操作成功则返回 true，否则返回 false
     */
    public boolean isConfirmed() {
        return confirmed;
    }
}
