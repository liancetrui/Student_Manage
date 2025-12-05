package com.Narylr.Student_Manage.dao;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.Connection;

/**
 * Database connection singleton
 * 数据库连接单例类，用于管理数据库连接的唯一实例
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    /**
     * 私有构造函数，初始化数据库连接
     * 通过Driver类获取数据库连接并存储在实例变量中
     */
    private DatabaseConnection() {
        new Driver().getConnection();
        this.connection = Driver.connection;
    }

    /**
     * 获取DatabaseConnection单例实例
     * 使用同步机制确保多线程环境下的线程安全性
     * @return DatabaseConnection实例
     */
    public static synchronized DatabaseConnection getInstance() {
        // 懒加载方式创建单例实例
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * 获取数据库连接对象
     * @return Connection 数据库连接对象
     */
    public Connection getConnection() {
        return connection;
    }
}
