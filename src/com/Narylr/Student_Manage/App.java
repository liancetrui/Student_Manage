package com.Narylr.Student_Manage;

import com.Narylr.Student_Manage.service.AuthService;
import com.Narylr.Student_Manage.view.LoginFrame;
import com.Narylr.Student_Manage.view.RegisterDialog;

import javax.swing.*;
import java.sql.SQLException;

/**
 * 主应用程序入口类 - 学生管理系统图形界面程序
 * 负责初始化系统环境、设置外观风格、检查管理员账户并启动登录或注册流程
 */
public class App {

    /**
     * 应用程序主入口方法
     * 设置系统默认外观，初始化数据库连接，根据是否已存在管理员账户决定显示首次设置对话框还是登录窗口
     * 所有GUI操作在事件调度线程中执行以确保线程安全
     *
     * @param args 命令行参数数组（本应用未使用）
     */
    public static void main(String[] args) {
        // 设置系统默认外观风格
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 在事件调度线程中运行GUI组件以保证线程安全性
        SwingUtilities.invokeLater(() -> {
            try {
                // 初始化数据库连接
                System.out.println("欢迎使用学生数据管理系统");
                System.out.println("正在初始化数据库连接...");

                // 创建认证服务实例用于处理用户验证逻辑
                AuthService authService = new AuthService();

                // 检查系统中是否存在管理员账户
                if (!authService.adminExists()) {
                    // 显示首次使用设置向导，引导用户创建管理员账户
                    showFirstTimeSetup(authService);
                } else {
                    // 显示标准登录界面供已有用户登录
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

    /**
     * 首次使用设置流程
     * 当检测到系统无管理员账户时调用此方法，提示用户进行初始配置并创建第一个管理员账户
     * 注册完成后自动跳转至登录界面
     *
     * @param authService 认证服务对象，提供注册和验证功能
     */
    private static void showFirstTimeSetup(AuthService authService) {
        JOptionPane.showMessageDialog(null,
            "欢迎首次使用学生管理系统！\n请先注册管理员账户。",
            "首次使用",
            JOptionPane.INFORMATION_MESSAGE);

        // 创建临时框架作为模态对话框的父容器
        JFrame tempFrame = new JFrame();
        tempFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RegisterDialog registerDialog = new RegisterDialog(tempFrame, authService);
        registerDialog.setVisible(true);

        // 用户完成注册后显示登录界面
        LoginFrame loginFrame = new LoginFrame(authService);
        loginFrame.setVisible(true);
    }
}
