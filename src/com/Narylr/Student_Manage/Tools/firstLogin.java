package com.Narylr.Student_Manage.Tools;

import java.sql.SQLException;
import java.util.Scanner;

public class firstLogin {
    public static boolean rootUser = false;

    public firstLogin() throws SQLException {
        // 创建一个 CheckRoot 对象，避免重复创建
        CheckRoot checkRoot = new CheckRoot();

        // 如果用户表中有管理员账号
        if (checkRoot.checkUserRoot()) {
            rootUser = true;  // 管理员存在，设置 rootUser 为 true
            new Login();  // 进入登录界面
        } else {
            // 如果没有管理员账号，提示用户注册管理员
            System.out.println("第一次使用该系统，请注册管理员账号");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("************** 注册 **************");
                System.out.println("1. 注册管理员");
                System.out.println("2. 退出");
                System.out.print("请输入序号:");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
                        new Registration();  // 注册管理员
                        rootUser = true;  // 注册管理员后设置 rootUser 为 true
                        // 注册成功后直接跳转到登录界面，而不是继续提示注册
                        new Login();
                        return; // 退出 while 循环，避免重复进入注册流程
                    }
                    case "2" -> {
                        System.out.println("退出中...");
                        // 退出时直接退出程序
                        System.exit(0);
                    }
                    default -> {
                        System.out.println("输入无效，请重新选择！");
                    }
                }
            }
        }
    }
}
