package com.Narylr.Student_Manage.dao;

import com.Narylr.Student_Manage.connect.Driver;

import java.sql.Connection;

/**
 * Database connection singleton
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        new Driver().getConnection();
        this.connection = Driver.connection;
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
