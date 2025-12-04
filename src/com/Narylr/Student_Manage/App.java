package com.Narylr.Student_Manage;

import com.Narylr.Student_Manage.service.AuthService;
import com.Narylr.Student_Manage.view.LoginFrame;
import com.Narylr.Student_Manage.view.RegisterDialog;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Main Application Entry Point - Student Management System with GUI
 */
public class App {
    public static void main(String[] args) {
        // Set Look and Feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Initialize database connection
                System.out.println("欢迎使用学生数据管理系统");
                System.out.println("正在初始化数据库连接...");
                
                // Create auth service
                AuthService authService = new AuthService();
                
                // Check if admin exists
                if (!authService.adminExists()) {
                    // Show first-time setup dialog
                    showFirstTimeSetup(authService);
                } else {
                    // Show login frame
                    LoginFrame loginFrame = new LoginFrame(authService);
                    loginFrame.setVisible(true);
                }
            } catch (Exception e) {
                System.err.println("应用程序启动失败: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                    "应用程序启动失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }

    private static void showFirstTimeSetup(AuthService authService) {
        JOptionPane.showMessageDialog(null,
            "欢迎首次使用学生管理系统！\n请先注册管理员账户。",
            "首次使用",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Create a temporary frame for the dialog
        JFrame tempFrame = new JFrame();
        tempFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        RegisterDialog registerDialog = new RegisterDialog(tempFrame, authService);
        registerDialog.setVisible(true);
        
        // After registration, show login frame
        LoginFrame loginFrame = new LoginFrame(authService);
        loginFrame.setVisible(true);
    }
}
