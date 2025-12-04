package com.Narylr.Student_Manage.dao;

import com.Narylr.Student_Manage.model.Permission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Permission Data Access Object
 */
public class PermissionDAO {
    private final Connection connection;

    public PermissionDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Find permission by user ID
     */
    public Permission findById(String userId) throws SQLException {
        String sql = "SELECT * FROM permissions WHERE p_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPermission(rs);
                }
            }
        }
        return null;
    }

    /**
     * Insert new permission
     */
    public boolean insert(Permission permission) throws SQLException {
        String sql = "INSERT INTO permissions (p_ID, p_NAME, p_WRITE, p_READ) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, permission.getPermissionId());
            ps.setString(2, permission.getUserName());
            ps.setString(3, permission.getWritePermission());
            ps.setString(4, permission.getReadPermission());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Update permission
     */
    public boolean update(Permission permission) throws SQLException {
        String sql = "UPDATE permissions SET p_NAME = ?, p_WRITE = ?, p_READ = ? WHERE p_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, permission.getUserName());
            ps.setString(2, permission.getWritePermission());
            ps.setString(3, permission.getReadPermission());
            ps.setString(4, permission.getPermissionId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Update specific permission type
     */
    public boolean updatePermissionType(String userId, String permissionType, String value) throws SQLException {
        String sql = "UPDATE permissions SET " + permissionType + " = ? WHERE p_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setString(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Delete permission
     */
    public boolean delete(String userId) throws SQLException {
        String sql = "DELETE FROM permissions WHERE p_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Find all permissions
     */
    public List<Permission> findAll() throws SQLException {
        List<Permission> permissions = new ArrayList<>();
        String sql = "SELECT * FROM permissions";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                permissions.add(mapResultSetToPermission(rs));
            }
        }
        return permissions;
    }

    /**
     * Map ResultSet to Permission object
     */
    private Permission mapResultSetToPermission(ResultSet rs) throws SQLException {
        Permission permission = new Permission();
        permission.setPermissionId(rs.getString("p_ID"));
        permission.setUserName(rs.getString("p_NAME"));
        permission.setWritePermission(rs.getString("p_WRITE"));
        permission.setReadPermission(rs.getString("p_READ"));
        return permission;
    }
}
