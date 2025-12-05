package com.Narylr.Student_Manage.connect;

import java.sql.*;

/**
 * 数据库表结构创建类
 * 用于初始化系统所需的数据库和数据表
 */
public class Create {

    /**
     * 创建数据库SQL语句：如果不存在则创建名为java的数据库
     */
    private static final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS java";

    /**
     * 创建权限表SQL语句：包含权限ID、名称、读写权限字段
     * p_ID: 权限标识符，主键
     * p_NAME: 权限名称，非空
     * p_WRITE: 写权限，只能为'Y'或'N'
     * p_READ: 读权限，只能为'Y'或'N'
     */
    private static final String CREATE_PERMISSIONS_TABLE = "CREATE TABLE IF NOT EXISTS permissions ("
            + "p_ID VARCHAR(255) PRIMARY KEY, "
            + "p_NAME VARCHAR(255) NOT NULL, "
            + "p_WRITE VARCHAR(1) CHECK (p_WRITE IN ('Y', 'N')), "
            + "p_READ VARCHAR(1) CHECK (p_READ IN ('Y', 'N'))"
            + ")";

    /**
     * 创建学生表SQL语句：存储学生基本信息
     * s_NO: 学号，主键
     * s_NAME: 姓名
     * s_AGE: 年龄，可为空，默认NULL
     * s_GENDER: 性别
     * s_GRADE: 年级，可为空，默认NULL
     * s_TEL: 电话
     * s_EMAIL: 邮箱
     * s_ADDRESS: 地址
     */
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

    /**
     * 创建用户表SQL语句：存储用户登录信息和权限等级
     * u_ID: 用户ID，主键
     * u_NAME: 用户名，非空
     * u_PWD: 密码，非空
     * u_ROOT: 权限等级，只能为'1'(管理员)或'0'(普通用户)
     */
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users ("
            + "u_ID VARCHAR(255) PRIMARY KEY, "
            + "u_NAME VARCHAR(255) NOT NULL, "
            + "u_PWD VARCHAR(255) NOT NULL, "
            + "u_ROOT VARCHAR(1) CHECK (u_ROOT IN ('1', '0'))"
            + ")";

    /**
     * 数据库连接对象
     */
    private Connection connection;

    /**
     * 构造方法
     * @param connection 数据库连接对象，用于执行SQL语句
     */
    public Create(Connection connection) {
        this.connection = connection;
    }

    /**
     * 创建数据库
     * 执行创建数据库的SQL语句，如果数据库已存在则忽略
     */
    public void createDatabase() {
        try (Statement statement = connection.createStatement()) {
            // 创建数据库
            statement.executeUpdate(CREATE_DATABASE);

        } catch (SQLException ignore) {
        }
    }

    /**
     * 创建所有数据表
     * 按顺序创建权限表、学生表和用户表，如果表已存在则忽略
     */
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
