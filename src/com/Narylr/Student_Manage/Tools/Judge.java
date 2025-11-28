package com.Narylr.Student_Manage.Tools;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Judge {
    // 判断用户是否已登录
    public static boolean loggedIn = false;

    public Judge(String u_id, String u_pwd) throws SQLException {
        // 用try-with-resources保证PreparedStatement和ResultSet的关闭
        String sql = "SELECT * FROM users WHERE u_ID = ?";
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, u_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // 如果密码匹配，登录成功
                    if (u_pwd.equals(resultSet.getString(3))) {
                        System.out.println("登录成功");
                        loggedIn = true;
                        // 成功后跳转到修改页面或其他操作
                        new Modification(u_id);
                    } else {
                        System.out.println("账号或密码不正确");
                    }
                } else {
                    System.out.println("未找到该用户！");
                }
            }
        } catch (SQLException e) {
            System.out.println("数据库查询错误: " + e.getMessage());
            throw e; // 可根据需求进一步处理异常
        }
    }
}
