package com.Narylr.Student_Manage.Tools;

import com.Narylr.Student_Manage.connect.Driver;
import java.sql.*;
import java.util.Scanner;

import static com.Narylr.Student_Manage.Tools.Login.loggedIn;

public class Modification {
    private String userId;

    public Modification(String userId) {
        this.userId = userId;
        try {
            new Driver().getConnection();
            chooseAction();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chooseAction() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        if (new CheckRoot().checkRootUser(userId)) {
            while (true) {
                System.out.println("************** 学生信息管理(管理员登录) **************");
                System.out.println("1. 新增学生数据");
                System.out.println("2. 修改学生数据");
                System.out.println("3. 删除学生数据");
                System.out.println("4. 查看学生数据");
                System.out.println("5. 删除用户数据");
                System.out.println("6. 查看用户权限");
                System.out.println("7. 授权用户权限");
                System.out.println("8. 退出登录");
                System.out.print("请输入选择:");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        addStudent();
                        break;
                    case "2":
                        modifyStudent();
                        break;
                    case "3":
                        new Delete().deleteStudentRoot();
                        break;
                    case "4":
                        new Check().checkStudentRoot();
                        break;
                    case "5":
                        new Delete().deleteUsers();
                        break;
                    case "6":
                        new Check().checkPermissions();
                        break;
                    case "7":
                        new Authorization().authorizeUser();
                        break;
                    case "8":
                        System.out.println("退出登录...");
                        pause(500);
                        System.out.println("退出登录成功！");
                        loggedIn = false;
                        new Login();
                    default:
                        System.out.println("输入无效，请重新选择！");
                }
            }
        } else {
            while (true) {
                System.out.println("************** 学生信息管理 **************");
                System.out.println("1. 新增学生数据");
                System.out.println("2. 修改学生数据");
                System.out.println("3. 删除学生数据");
                System.out.println("4. 查看学生数据");
                System.out.println("5. 退出登录");
                System.out.print("请输入选择:");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        if (checkUsersPermissionsWrite(userId).equals("Y")) {
                            addStudent();
                        } else {
                            System.out.println("权限不足");
                            pause(500);
                        }
                        break;
                    case "2":
                        if (checkUsersPermissionsWrite(userId).equals("Y")) {
                            modifyStudent();
                        } else {
                            System.out.println("权限不足");
                           pause(500);
                        }
                        break;
                    case "3":
                        if (checkUsersPermissionsWrite(userId).equals("Y")) {
                            new Delete().deleteStudentUser(userId);
                        } else {
                            System.out.println("权限不足");
                            pause(500);
                        }
                        break;
                    case "4":
                        if (checkUsersPermissionsRead(userId).equals("Y")) {
                            new Check().checkStudentUser(userId);
                        } else {
                            System.out.println("权限不足");
                            pause(500);
                        }
                        break;
                    case "5":
                        System.out.println("退出登录...");
                        pause(500);
                        System.out.println("退出登录成功！");
                        loggedIn = false;
                        new Login();
                    default:
                        System.out.println("输入无效，请重新选择！");
                        pause(500);
                }
            }
        }
    }

    private void addStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("************** 新增学生信息 **************");
        int s_no, s_age, s_grade;

        while (true) {
            System.out.print("请输入学生学号 (s_NO):");

            if (scanner.hasNextInt()) {
                s_no = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("请输入数字!!!");
                scanner.nextLine();
            }
        }

        System.out.print("请输入学生姓名 (s_NAME):");
        String s_name = scanner.nextLine();

        while (true) {
            System.out.print("请输入学生年龄 (s_AGE):");
            if (scanner.hasNextInt()) {
                s_age = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("请输入数字!!!");
                scanner.nextLine();
            }
        }

        System.out.print("请输入学生性别 (s_GENDER):");
        String s_gender = scanner.nextLine();

        while (true) {
            System.out.print("请输入学生年级 (s_GRADE):");
            if (scanner.hasNextInt()) {
                s_grade = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("请输入数字!!!");
                scanner.nextLine();
            }
        }
        System.out.print("请输入学生电话 (s_TEL):");
        String s_tel = scanner.nextLine();

        System.out.print("请输入学生邮箱 (s_EMAIL):");
        String s_email = scanner.nextLine();

        System.out.print("请输入学生地址 (s_ADDRESS):");
        String s_address = scanner.nextLine();

        String sql = "INSERT INTO student (s_NO, s_NAME, s_AGE, s_GENDER, s_GRADE, s_TEL, s_EMAIL, s_ADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, s_no);
            preparedStatement.setString(2, s_name);
            preparedStatement.setInt(3, s_age);
            preparedStatement.setString(4, s_gender);
            preparedStatement.setInt(5, s_grade);
            preparedStatement.setString(6, s_tel);
            preparedStatement.setString(7, s_email);
            preparedStatement.setString(8, s_address);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("新增学生信息成功！");
                pause(500);
            } else {
                System.out.println("新增学生信息失败！");
                pause(500);
            }
        } catch (SQLException e) {
            System.out.println("请输入数字!!!");
        }
    }

    private void modifyStudent() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("************** 修改学生信息 **************");
        System.out.print("请输入要修改的学生学号 (s_NO):");
        String s_no = scanner.nextLine();
        scanner.nextLine();

        if (!checkStudentExists(s_no)) {
            System.out.println("学生学号不存在，无法修改！");
            pause(500);
            return;
        }

        System.out.println("选择要修改的内容:");
        System.out.println("1. 姓名 (s_NAME)");
        System.out.println("2. 年龄 (s_AGE)");
        System.out.println("3. 性别 (s_GENDER)");
        System.out.println("4. 年级 (s_GRADE)");
        System.out.println("5. 电话 (s_TEL)");
        System.out.println("6. 邮箱 (s_EMAIL)");
        System.out.println("7. 地址 (s_ADDRESS)");
        System.out.print("请输入序号:");
        String choice = scanner.nextLine();
        scanner.nextLine();

        String field;
        switch (choice) {
            case "1" -> field = "s_NAME";
            case "2" -> field = "s_AGE";
            case "3" -> field = "s_GENDER";
            case "4" -> field = "s_GRADE";
            case "5" -> field = "s_TEL";
            case "6" -> field = "s_EMAIL";
            case "7" -> field = "s_ADDRESS";
            default -> {
                System.out.println("输入无效！");
                pause(500);
                return;
            }
        }

        System.out.print("请输入新的值:");
        String newValue = scanner.nextLine();

        String sql = "UPDATE student SET " + field + " = ? WHERE s_NO = ?";
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, s_no);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("修改成功！");
                pause(500);
            } else {
                System.out.println("修改失败！");
                pause(500);
            }
        } catch (SQLException e) {
            System.out.println("数据库操作出错:" + e.getMessage());
            pause(500);
        }
    }

    private boolean checkStudentExists(String s_no) throws SQLException {
        String sql = "SELECT * FROM student WHERE s_NO = ?";
        try (PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, s_no);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private String checkUsersPermissionsRead(String p_ID) throws SQLException {
        String sql = "SELECT * FROM permissions WHERE p_ID = ?";
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        preparedStatement.setString(1, p_ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString("p_READ");
    }

    private String checkUsersPermissionsWrite(String p_ID) throws SQLException {
        String sql = "SELECT * FROM permissions WHERE p_ID = ?";
        PreparedStatement preparedStatement = Driver.connection.prepareStatement(sql);
        preparedStatement.setString(1, p_ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString("p_WRITE");
    }
    private void pause(int time){
        try {
            Thread.sleep(time);//单位是ms
        } catch (InterruptedException ignored){}
    }
}