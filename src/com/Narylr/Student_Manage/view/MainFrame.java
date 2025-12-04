package com.Narylr.Student_Manage.view;

import com.Narylr.Student_Manage.service.AuthService;
import com.Narylr.Student_Manage.service.PermissionService;
import com.Narylr.Student_Manage.service.StudentService;

import javax.swing.*;
import java.awt.*;

/**
 * Main Frame - Student Management System
 */
public class MainFrame extends JFrame {
    private final AuthService authService;
    private final StudentService studentService;
    private final PermissionService permissionService;
    private JTabbedPane tabbedPane;

    public MainFrame(AuthService authService) {
        this.authService = authService;
        this.studentService = new StudentService();
        this.permissionService = new PermissionService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("学生管理系统 - " + authService.getCurrentUser().getUserName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu bar
        createMenuBar();

        // Tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Student Management Panel
        StudentManagementPanel studentPanel = new StudentManagementPanel(
            studentService, 
            permissionService, 
            authService
        );
        tabbedPane.addTab("学生管理", studentPanel);

        // User Management Panel (Admin only)
        if (authService.isCurrentUserAdmin()) {
            UserManagementPanel userPanel = new UserManagementPanel(permissionService);
            tabbedPane.addTab("用户管理", userPanel);
        }

        add(tabbedPane, BorderLayout.CENTER);

        // Status bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String role = authService.isCurrentUserAdmin() ? "管理员" : "普通用户";
        JLabel statusLabel = new JLabel("当前用户: " + authService.getCurrentUser().getUserName() + 
                                       " (" + role + ")");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("文件");
        
        JMenuItem logoutItem = new JMenuItem("注销");
        logoutItem.addActionListener(e -> handleLogout());
        
        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(logoutItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Help Menu
        JMenu helpMenu = new JMenu("帮助");
        
        JMenuItem aboutItem = new JMenuItem("关于");
        aboutItem.addActionListener(e -> showAbout());

        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

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

    private void showAbout() {
        JOptionPane.showMessageDialog(
            this,
            "学生管理系统 v2.0\n\n基于Java Swing开发\n\n功能：学生信息管理、用户权限管理",
            "关于",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
