package com.Narylr.Student_Manage.Tools;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Authorization {
    public Authorization() {
        new Driver().getConnection();
    }

    // 检查用户是否存在
    private boolean checkUsersExists(String u_id) throws SQLException {
        String sql = "SELECT * FROM users WHERE u_ID = ?";
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, u_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // 授权或撤销授权的通用方法
    private void updatePermission(String u_id, String permissionType, String value) throws SQLException {
        String sql = "UPDATE permissions SET " + permissionType + " = ? WHERE p_ID = ?";
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, u_id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println(permissionType + " 修改成功");
            } else {
                System.out.println(permissionType + " 修改失败");
            }
        }
    }

    // 授权用户
    public void authorize() throws SQLException {
        System.out.print("请输入授权的用户ID(u_ID):");
        Scanner scanner = new Scanner(System.in);
        String u_id = scanner.nextLine();

        if (!checkUsersExists(u_id)) {
            System.out.println("该用户不存在");
            return;
        }

        System.out.println("1.授权读(允许查看学生数据)");
        System.out.println("2.授权写(允许删改增加学生数据)");
        System.out.print("请输入选择:");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                updatePermission(u_id, "p_READ", "Y");
                break;
            case "2":
                updatePermission(u_id, "p_WRITE", "Y");
                break;
            default:
                System.out.println("输入无效，请重新选择！");
        }
    }

    // 撤销授权
    public void deleteAuthorization() throws SQLException {
        System.out.print("请输入授权的用户ID(u_ID):");
        Scanner scanner = new Scanner(System.in);
        String u_id = scanner.nextLine();

        if (!checkUsersExists(u_id)) {
            System.out.println("该用户不存在");
            return;
        }

        System.out.println("1.撤销授权读(允许查看学生数据)");
        System.out.println("2.撤销授权写(允许删改增加学生数据)");
        System.out.print("请输入选择:");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                updatePermission(u_id, "p_READ", "N");
                break;
            case "2":
                updatePermission(u_id, "p_WRITE", "N");
                break;
            default:
                System.out.println("输入无效，请重新选择！");
        }
    }

    // 授权或撤销权限
    public void authorizeUser() throws SQLException {
        System.out.println("1.授权");
        System.out.println("2.取消授权");
        System.out.println("3.退出");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                authorize();
                break;
            case "2":
                deleteAuthorization();
                break;
            case "3":
                break;
            default:
                System.out.println("输入无效，请重新选择！");
        }
    }
}
