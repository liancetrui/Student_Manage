package com.Narylr.Student_Manage.connect;

import java.sql.*;

public class Create {

    private static final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS java";
    private static final String CREATE_PERMISSIONS_TABLE = "CREATE TABLE IF NOT EXISTS permissions ("
            + "p_ID VARCHAR(255) PRIMARY KEY, "
            + "p_NAME VARCHAR(255) NOT NULL, "
            + "p_WRITE VARCHAR(1) CHECK (p_WRITE IN ('Y', 'N')), "
            + "p_READ VARCHAR(1) CHECK (p_READ IN ('Y', 'N'))"
            + ")";

    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS student ("
            + "s_NO VARCHAR(255) PRIMARY KEY, "
            + "s_NAME VARCHAR(255), "
            + "s_AGE INT NULL DEFAULT NULL, "
            + "s_GENDER VARCHAR(255), "
            + "s_GRADE INT NULL DEFAULT NULL, "
            + "s_TEL VARCHAR(255), "
            + "s_EMAIL VARCHAR(255), "
            + "s_ADDRESS VARCHAR(255)"
            + ")";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users ("
            + "u_ID VARCHAR(255) PRIMARY KEY, "
            + "u_NAME VARCHAR(255) NOT NULL, "
            + "u_PWD VARCHAR(255) NOT NULL, "
            + "u_ROOT VARCHAR(1) CHECK (u_ROOT IN ('1', '0'))"
            + ")";

    private Connection connection;

    public Create(Connection connection) {
        this.connection = connection;
    }

    // 创建数据库
    public void createDatabase() {
        try (Statement statement = connection.createStatement()) {
            // 创建数据库
            statement.executeUpdate(CREATE_DATABASE);

        } catch (SQLException ignore) {
        }
    }

    // 创建表
    public void createTables() {
        try (Statement statement = connection.createStatement()) {
            // 创建 permissions 表
            statement.executeUpdate(CREATE_PERMISSIONS_TABLE);

            // 创建 student 表
            statement.executeUpdate(CREATE_STUDENT_TABLE);

            // 创建 users 表
            statement.executeUpdate(CREATE_USERS_TABLE);

        } catch (SQLException ignore) {
        }
    }
}
