package com.Narylr.Student_Manage.view;

import com.Narylr.Student_Manage.service.AuthService;
import com.Narylr.Student_Manage.service.PermissionService;
import com.Narylr.Student_Manage.service.StudentService;

import javax.swing.*;
import java.awt.*;

/**
 * 主窗口类 - 学生管理系统主界面
 * 负责初始化整个系统的图形用户界面，包括菜单栏、标签页面板以及状态栏。
 * 根据当前用户的权限显示不同的功能模块。
 */
public class MainFrame extends JFrame {
    private final AuthService authService;
    private final StudentService studentService;
    private final PermissionService permissionService;
    private JTabbedPane tabbedPane;

    /**
     * 构造方法，用于创建主窗口实例
     *
     * @param authService 认证服务对象，提供用户认证相关功能
     */
    public MainFrame(AuthService authService) {
        this.authService = authService;
        this.studentService = new StudentService();
        this.permissionService = new PermissionService();
        initializeUI();
    }

    /**
     * 初始化用户界面组件
     * 包括设置窗口标题、大小、关闭操作等基本属性，
     * 创建菜单栏、标签页（根据权限控制是否显示用户管理面板）及状态栏。
     */
    private void initializeUI() {
        setTitle("学生管理系统 - " + authService.getCurrentUser().getUserName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建顶部菜单栏
        createMenuBar();

        // 初始化中心区域的标签页容器
        tabbedPane = new JTabbedPane();

        // 添加学生管理面板到标签页中
        StudentManagementPanel studentPanel = new StudentManagementPanel(
            studentService,
            permissionService,
            authService
        );
        tabbedPane.addTab("学生管理", studentPanel);

        // 如果当前用户是管理员，则添加用户管理面板
        if (authService.isCurrentUserAdmin()) {
            UserManagementPanel userPanel = new UserManagementPanel(permissionService);
            tabbedPane.addTab("用户管理", userPanel);
        }

        add(tabbedPane, BorderLayout.CENTER);

        // 创建并添加底部状态栏，显示当前用户信息及其角色
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String role = authService.isCurrentUserAdmin() ? "管理员" : "普通用户";
        JLabel statusLabel = new JLabel("当前用户: " + authService.getCurrentUser().getUserName() +
                                       " (" + role + ")");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    /**
     * 创建应用程序菜单栏
     * 包含“文件”和“帮助”两个主菜单项。
     * “文件”菜单下有注销和退出选项，“帮助”菜单下有关于系统的信息展示。
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // 文件菜单
        JMenu fileMenu = new JMenu("文件");

        JMenuItem logoutItem = new JMenuItem("注销");
        logoutItem.addActionListener(e -> handleLogout());

        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(logoutItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // 帮助菜单
        JMenu helpMenu = new JMenu("帮助");

        JMenuItem aboutItem = new JMenuItem("关于");
        aboutItem.addActionListener(e -> showAbout());

        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    /**
     * 处理用户注销请求
     * 弹出确认对话框让用户选择是否真的要注销登录。
     * 若用户确认注销，则执行登出逻辑，并跳转回登录页面。
     */
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "确定要注销登录吗？",
            "确认注销",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            authService.logout();
            this.dispose();
            new LoginFrame(authService).setVisible(true);
        }
    }

    /**
     * 显示系统相关信息
     * 当用户点击“关于”菜单项时弹出消息对话框，
     * 展示软件版本号、技术栈及主要功能简介。
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(
            this,
            "学生管理系统 v2.0\n\n基于Java Swing开发\n\n功能：学生信息管理、用户权限管理",
            "关于",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
