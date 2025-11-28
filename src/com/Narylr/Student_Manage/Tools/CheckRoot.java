package com.Narylr.Student_Manage.Tools;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.*;

public class CheckRoot {
    public CheckRoot(){
        new Driver().getConnection();
    }

    // 检查数据库中是否已有管理员
    public boolean checkUserRoot() throws SQLException {
        String sql = "SELECT u_ROOT FROM users WHERE u_ROOT = '0'"; // 查找管理员
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        // 如果查询到至少一条记录，表示已有管理员
        return resultSet.next();
    }

    // 根据用户ID来检查是否为管理员
    public boolean checkRootUser(String u_id) throws SQLException {
        String sql = "SELECT u_ROOT FROM users WHERE u_ID = ?";
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        preparedStatement.setString(1, u_id);

        ResultSet resultSet = preparedStatement.executeQuery();

        // 确保结果集有数据
        if (resultSet.next()) {
            return resultSet.getString("u_ROOT").equals("0"); // 0代表管理员
        } else {
            // 如果没有找到用户ID，返回false表示不是管理员
            return false;
        }
    }
}
