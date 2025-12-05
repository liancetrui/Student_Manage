package com.Narylr.Student_Manage.view;

import com.Narylr.Student_Manage.model.User;
import com.Narylr.Student_Manage.service.AuthService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * 登录窗口类，提供用户登录界面及功能。
 * 包含用户ID输入框、密码输入框以及登录、注册和退出按钮。
 */
public class LoginFrame extends JFrame {
    private final AuthService authService;
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton exitButton;

    /**
     * 构造方法，初始化登录窗口并设置认证服务。
     *
     * @param authService 认证服务对象，用于处理用户的登录与注册逻辑
     */
    public LoginFrame(AuthService authService) {
        this.authService = authService;
        initializeUI();
    }

    /**
     * 初始化登录界面的UI组件布局和样式。
     * 设置标题、大小、关闭操作等基本属性，并构建表单面板和按钮区域。
     */
    private void initializeUI() {
        setTitle("学生管理系统 - 登录");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 主面板使用边框布局管理器
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // 标题标签
        JLabel titleLabel = new JLabel("学生管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 表单面板采用网格包布局以实现灵活控件排列
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 用户ID输入项
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("用户ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        userIdField = new JTextField(20);
        formPanel.add(userIdField, gbc);

        // 密码输入项
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("密码:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 按钮面板居中放置三个操作按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        loginButton = new JButton("登录");
        loginButton.setPreferredSize(new Dimension(80, 30));
        loginButton.addActionListener(e -> handleLogin());

        registerButton = new JButton("注册");
        registerButton.setPreferredSize(new Dimension(80, 30));
        registerButton.addActionListener(e -> handleRegister());

        exitButton = new JButton("退出");
        exitButton.setPreferredSize(new Dimension(80, 30));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 添加回车键监听事件到密码字段上以便快速触发登录动作
        passwordField.addActionListener(e -> handleLogin());
    }

    /**
     * 处理登录请求的方法。
     * 获取用户输入的信息进行验证，并根据结果跳转至主界面或提示错误信息。
     */
    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入用户ID和密码", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User user = authService.login(userId, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "登录成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new MainFrame(authService).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "用户ID或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "数据库错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * 打开注册对话框供新用户完成账户创建流程。
     */
    private void handleRegister() {
        RegisterDialog registerDialog = new RegisterDialog(this, authService);
        registerDialog.setVisible(true);
    }
}
