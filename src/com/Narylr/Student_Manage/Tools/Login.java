package com.Narylr.Student_Manage.Tools;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    public static boolean loggedIn = false;

    public Login() throws SQLException {
        new Driver().getConnection();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("************** 登录 **************");
            System.out.println("1. 登录");
            System.out.println("2. 注册");
            System.out.println("3. 退出");
            System.out.print("请输入序号:");

            String i = scanner.nextLine();

            switch (i) {
                case "1" -> {
                    if (loggedIn) {
                        System.out.println("已登录，无需重复登录！");
                        break;
                    }

                    // 登录操作
                    System.out.print("请输入ID:");
                    String id = scanner.nextLine();
                    System.out.print("请输入密码:");
                    String password = scanner.nextLine();

                    // 使用Judge进行登录验证
                    new Judge(id, password);  // 这个类负责验证登录并设置 loggedIn 状态
                    if (loggedIn) {
                        System.out.println("登录成功！");
                    } else {
                        System.out.println("登录失败，请检查ID或密码！");
                    }
                }
                case "2" -> new Registration();  // 注册功能
                case "3" -> {
                    System.out.println("退出中...");
                    System.exit(0);  // 退出程序
                }
                default -> {
                    System.out.println("输入无效，请重新选择！");
                }
            }

            // 如果已登录，退出循环
            if (loggedIn) {
                break; // 已登录后，退出循环
            }
        }
    }
}
