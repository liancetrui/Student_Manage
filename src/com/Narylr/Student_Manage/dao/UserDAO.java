package com.Narylr.Student_Manage.dao;

import com.Narylr.Student_Manage.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User Data Access Object
 */
public class UserDAO {
    private final Connection connection;

    public UserDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Find user by ID
     */
    public User findById(String userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE u_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    /**
     * Check if admin exists
     */
    public boolean adminExists() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE u_ROOT = '0'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Insert new user
     */
    public boolean insert(User user) throws SQLException {
        String sql = "INSERT INTO users (u_ID, u_NAME, u_PWD, u_ROOT) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserRoot());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Update user
     */
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET u_NAME = ?, u_PWD = ?, u_ROOT = ? WHERE u_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUserRoot());
            ps.setString(4, user.getUserId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Delete user
     */
    public boolean delete(String userId) throws SQLException {
        String sql = "DELETE FROM users WHERE u_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Find all users
     */
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    /**
     * Validate user credentials
     */
    public User validateUser(String userId, String password) throws SQLException {
        User user = findById(userId);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Map ResultSet to User object
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getString("u_ID"));
        user.setUserName(rs.getString("u_NAME"));
        user.setPassword(rs.getString("u_PWD"));
        user.setUserRoot(rs.getString("u_ROOT"));
        return user;
    }
}
