package com.Narylr.Student_Manage.Tools;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete {
    public Delete() {
        new Driver().getConnection();
    }
    //student表是否存在该学号
    public static PreparedStatement getStudent(String s_no) throws SQLException {
        String sql = "SELECT * FROM STUDENT WHERE s_NO = ?";
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        preparedStatement.setString(1, s_no);
        return preparedStatement;
    }
    //user表是否存在该用户
    public static PreparedStatement getUsers(String u_id) throws SQLException {
        String sql = "SELECT * FROM users WHERE u_ID = ?";
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        preparedStatement.setString(1, u_id);
        return preparedStatement;
    }
    //用户删除学生信息
    public void deleteStudentUser(String s_no) throws SQLException{
        System.out.print("即将删除你的信息");

        String sql = "DELETE FROM STUDENT WHERE s_NO = ?";
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        preparedStatement.setString(1, s_no);

        PreparedStatement preparedStatement1 = getStudent(s_no);
        ResultSet resultSet = preparedStatement1.executeQuery();

        resultSet.next();
        System.out.print("请输入YES来确认,输入其他为放弃:");
        Scanner scanner1 = new Scanner(System.in);
        String yes = scanner1.nextLine();
        if (yes.equals("YES")){
            preparedStatement.executeUpdate();
            System.out.println("删除成功");
            try {
                Thread.sleep(500);//单位是ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else {
            System.out.println("放弃删除");
        }

        preparedStatement.close();
        preparedStatement1.close();
    }
    //   root删除学生信息
    public void deleteStudentRoot() throws SQLException{
        System.out.print("请输入要删除学生的学号(s_NO):");
        Scanner scanner = new Scanner(System.in);
        String s_no = scanner.nextLine();

        String sql = "DELETE FROM STUDENT WHERE s_NO = ?";
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        preparedStatement.setString(1, s_no);

        PreparedStatement preparedStatement1 = getStudent(s_no);
        ResultSet resultSet = preparedStatement1.executeQuery();

        if (resultSet.next()){
            System.out.print("请输入YES来确认,输入其他为放弃:");
            Scanner scanner1 = new Scanner(System.in);
            String yes = scanner1.nextLine();
            if (yes.equals("YES")){
                preparedStatement.executeUpdate();
                System.out.println("删除成功");
                try {
                    Thread.sleep(500);//单位是ms
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else {
                System.out.println("放弃删除");
            }
        }else {
            System.out.println("未找到该学号(SNO)");
        }

        preparedStatement.close();
        preparedStatement1.close();
    }
    //删除用户信息
    public void deleteUsers() throws SQLException{
        System.out.print("请输入要删除用户的ID(u_ID):");
        Scanner scanner = new Scanner(System.in);
        String u_id = scanner.nextLine();
        //删除用户表
        String sql = "DELETE FROM users WHERE u_ID = ?";
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        preparedStatement.setString(1, u_id);

        PreparedStatement preparedStatement1 = getUsers(u_id);
        ResultSet resultSet = preparedStatement1.executeQuery();
        //删除权限表
        String sql1 = "DELETE FROM permissions WHERE p_ID = ?";
        PreparedStatement preparedStatement2 = Driver.connection.prepareStatement(sql1);
        preparedStatement2.setString(1, u_id);

        if (resultSet.next()){
            System.out.print("请输入YES来确认,输入其他为放弃:");
            Scanner scanner1 = new Scanner(System.in);
            String yes = scanner1.nextLine();
            if (yes.equals("YES")){
                preparedStatement.executeUpdate();
                preparedStatement2.executeUpdate();
                System.out.println("删除成功");
                try {
                    Thread.sleep(500);//单位是ms
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else {
                System.out.println("放弃删除");
            }
        }else {
            System.out.println("未找到该用户ID(u_ID)");
        }

        preparedStatement.close();
        preparedStatement1.close();
        preparedStatement2.close();
    }
}