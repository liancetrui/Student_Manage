package com.Narylr.Student_Manage.Tools;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.*;
import java.util.Scanner;

public class Registration {
    public Registration() {
        try {
            new Driver().getConnection();
            registerUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerUser() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("************** 注册 **************");
        System.out.print("请输入ID:");
        String u_id = scanner.nextLine();
        System.out.print("请输入用户名:");
        String u_name = scanner.nextLine();
        System.out.print("请输入密码:");
        String u_pwd = scanner.nextLine();

        // 检查数据库中是否已经存在管理员
        boolean isAdminExist = new CheckRoot().checkUserRoot();

        String u_root;
        if (!isAdminExist) {
            // 如果没有管理员，则当前用户是管理员
            u_root = "0";
        } else {
            // 如果已有管理员，则当前用户是普通用户
            u_root = "1";
        }

        // 插入用户数据
        String sql = "INSERT INTO users (u_ID, u_NAME, u_PWD, u_ROOT) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        preparedStatement.setString(1, u_id);
        preparedStatement.setString(2, u_name);
        preparedStatement.setString(3, u_pwd);
        preparedStatement.setString(4, u_root);

        int result = preparedStatement.executeUpdate();
        try{ if (result > 0) {
            System.out.println("注册成功！请返回登录页面进行登录。");
            new Default(u_id, u_name);
            // 如果注册的是管理员，登录页面不需要再跳转到登录界面，普通用户才需要
            if (u_root.equals("1")) {
                new Login();
            }
        } else {
            System.out.println("注册失败，请重试！");
        }
        }catch (Exception e){
            System.out.println("ID不能重复");
        }

        preparedStatement.close();
    }
}
