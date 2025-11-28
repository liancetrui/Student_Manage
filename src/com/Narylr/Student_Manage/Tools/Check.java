package com.Narylr.Student_Manage.Tools;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Check {

    // 构造方法，初始化数据库连接
    public Check() {
        new Driver().getConnection();  // 获取数据库连接
    }

    /**
     * 查询当前用户的个人信息
     * @param s_no 用户的学号
     * @throws SQLException SQL执行异常
     */
    public void checkStudentUser(String s_no) throws SQLException {
        System.out.println("开始查询你的个人信息");
        sleep(1000);  // 模拟延迟，给用户展示信息的时间

        String sql = "SELECT * FROM student WHERE s_NO = ?";
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, s_no);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // 输出查询到的学生信息
                System.out.println("学号: " + resultSet.getInt("s_NO"));
                System.out.println("姓名: " + resultSet.getString("s_NAME"));
                System.out.println("年龄: " + resultSet.getInt("s_AGE"));
                System.out.println("性别: " + resultSet.getString("s_GENDER"));
                System.out.println("年级: " + resultSet.getInt("s_GRADE"));
                System.out.println("电话: " + resultSet.getString("s_TEL"));
                System.out.println("电子邮箱: " + resultSet.getString("s_EMAIL"));
                System.out.println("住址: " + resultSet.getString("s_ADDRESS"));
                sleep(1000);  // 模拟延迟
            } else {
                System.out.println("没有该用户的信息");
            }
        } catch (SQLException e) {
            System.out.println("查询出错: " + e.getMessage());
        }
    }

    /**
     * 管理员查看学生信息
     * @throws SQLException SQL执行异常
     */
    public void checkStudentRoot() throws SQLException {
        System.out.print("请输入学生学号 (s_NO):");
        Scanner scanner = new Scanner(System.in);
        String s_no = scanner.nextLine();

        String sql = "SELECT * FROM student WHERE s_NO = ?";
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, s_no);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // 输出查询到的学生信息
                System.out.println("学号: " + resultSet.getInt("s_NO"));
                System.out.println("姓名: " + resultSet.getString("s_NAME"));
                System.out.println("年龄: " + resultSet.getInt("s_AGE"));
                System.out.println("性别: " + resultSet.getString("s_GENDER"));
                System.out.println("年级: " + resultSet.getInt("s_GRADE"));
                System.out.println("电话: " + resultSet.getString("s_TEL"));
                System.out.println("电子邮箱: " + resultSet.getString("s_EMAIL"));
                System.out.println("住址: " + resultSet.getString("s_ADDRESS"));
                sleep(1000);  // 模拟延迟
            } else {
                System.out.println("未找到该学号(s_NO)");
                sleep(500);  // 模拟延迟
            }
        } catch (SQLException e) {
            System.out.println("查询出错: " + e.getMessage());
        }
    }

    /**
     * 查询用户的权限信息
     * @throws SQLException SQL执行异常
     */
    public void checkPermissions() throws SQLException {
        System.out.print("请输入用户ID (u_ID):");
        Scanner scanner = new Scanner(System.in);
        String p_id = scanner.nextLine();

        String sql = "SELECT * FROM permissions WHERE p_ID = ?";
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, p_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // 输出查询到的权限信息
                System.out.println("读权限: " + resultSet.getString("p_READ"));
                System.out.println("写权限: " + resultSet.getString("p_WRITE"));
                sleep(1000);  // 模拟延迟
            } else {
                System.out.println("未找到该用户(u_ID)");
                sleep(500);  // 模拟延迟
            }
        } catch (SQLException e) {
            System.out.println("查询出错: " + e.getMessage());
        }
    }
    /**
     * 查询用户的信息
     * @throws SQLException SQL执行异常
     */
    public void checkUsersData() throws SQLException{
        String sql = "SELECT u_ID, u_NAME, u_PWD, u_ROOT FROM users";  // 查询 users 表中的字段

        // 使用 try-with-resources 自动关闭资源
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // 输出表头
            System.out.println("用户ID\t用户名\t密码\t管理员权限");

            // 遍历结果集并输出每一行数据
            while (resultSet.next()) {
                String userId = resultSet.getString("u_ID");
                String userName = resultSet.getString("u_NAME");
                String userPwd = resultSet.getString("u_PWD");
                String userRoot = resultSet.getString("u_ROOT");

                // 输出用户数据
                System.out.println(userId + "\t" + userName + "\t" + userPwd + "\t" + (userRoot.equals("1") ? "管理员" : "普通用户"));
            }
        } catch (SQLException e) {
            System.out.println("查询用户数据时出错: " + e.getMessage());
        }
        sleep(1000);
    }
    /**
     * 通用的休眠方法，用于模拟延迟，方便用户查看信息
     * @param milliseconds 延迟时间，单位为毫秒
     */
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);  // 休眠指定时间
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
