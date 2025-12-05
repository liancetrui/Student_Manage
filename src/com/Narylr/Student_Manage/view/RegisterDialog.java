package com.Narylr.Student_Manage.view;

import com.Narylr.Student_Manage.service.AuthService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * 注册对话框类，用于提供用户注册界面。
 * 用户可以在此界面中输入用户ID、用户名、密码及确认密码完成注册操作。
 */
public class RegisterDialog extends JDialog {
    private final AuthService authService;
    private JTextField userIdField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cancelButton;

    /**
     * 构造方法，初始化注册对话框。
     *
     * @param parent       父窗口（Frame），对话框将相对于该窗口居中显示
     * @param authService  认证服务对象，用于处理注册逻辑
     */
    public RegisterDialog(Frame parent, AuthService authService) {
        super(parent, "用户注册", true);
        this.authService = authService;
        initializeUI();
    }

    /**
     * 初始化用户界面组件并进行布局设置。
     * 包括标题标签、表单面板（含各输入项）、按钮面板等。
     */
    private void initializeUI() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 标题部分
        JLabel titleLabel = new JLabel("新用户注册", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 表单区域：使用GridBagLayout实现灵活布局
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 用户ID输入框
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("用户ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        userIdField = new JTextField(20);
        formPanel.add(userIdField, gbc);

        // 用户名输入框
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("用户名:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        userNameField = new JTextField(20);
        formPanel.add(userNameField, gbc);

        // 密码输入框
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("密码:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        // 确认密码输入框
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("确认密码:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        confirmPasswordField = new JPasswordField(20);
        formPanel.add(confirmPasswordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 按钮区域：包括“注册”与“取消”两个按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        registerButton = new JButton("注册");
        registerButton.setPreferredSize(new Dimension(80, 30));
        registerButton.addActionListener(e -> handleRegister());

        cancelButton = new JButton("取消");
        cancelButton.setPreferredSize(new Dimension(80, 30));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * 处理用户的注册请求。
     * 验证输入信息是否合法，并调用认证服务执行实际注册流程。
     * 若注册成功则提示用户并关闭当前对话框；若失败或出现异常，则弹出相应错误提示。
     */
    private void handleRegister() {
        String userId = userIdField.getText().trim();
        String userName = userNameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // 输入验证：检查必填字段是否为空
        if (userId.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写所有字段", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 密码一致性校验
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
            confirmPasswordField.setText("");
            return;
        }

        try {
            boolean success = authService.register(userId, userName, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "注册成功！请返回登录。", "成功", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "注册失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            // 数据库相关异常处理
            if (ex.getMessage().contains("Duplicate")) {
                JOptionPane.showMessageDialog(this, "用户ID已存在", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "数据库错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            ex.printStackTrace();
        }
    }
}
