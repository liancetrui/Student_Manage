package com.Narylr.Student_Manage.service;

import com.Narylr.Student_Manage.dao.PermissionDAO;
import com.Narylr.Student_Manage.dao.UserDAO;
import com.Narylr.Student_Manage.model.Permission;
import com.Narylr.Student_Manage.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Permission Service
 */
public class PermissionService {
    private final PermissionDAO permissionDAO;
    private final UserDAO userDAO;

    public PermissionService() {
        this.permissionDAO = new PermissionDAO();
        this.userDAO = new UserDAO();
    }

    /**
     * Get permission for user
     */
    public Permission getPermission(String userId) throws SQLException {
        return permissionDAO.findById(userId);
    }

    /**
     * Grant read permission
     */
    public boolean grantReadPermission(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        return permissionDAO.updatePermissionType(userId, "p_READ", "Y");
    }

    /**
     * Grant write permission
     */
    public boolean grantWritePermission(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        return permissionDAO.updatePermissionType(userId, "p_WRITE", "Y");
    }

    /**
     * Revoke read permission
     */
    public boolean revokeReadPermission(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        return permissionDAO.updatePermissionType(userId, "p_READ", "N");
    }

    /**
     * Revoke write permission
     */
    public boolean revokeWritePermission(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        return permissionDAO.updatePermissionType(userId, "p_WRITE", "N");
    }

    /**
     * Check if user has read permission
     */
    public boolean hasReadPermission(String userId) throws SQLException {
        Permission permission = permissionDAO.findById(userId);
        return permission != null && permission.canRead();
    }

    /**
     * Check if user has write permission
     */
    public boolean hasWritePermission(String userId) throws SQLException {
        Permission permission = permissionDAO.findById(userId);
        return permission != null && permission.canWrite();
    }

    /**
     * Get all permissions
     */
    public List<Permission> getAllPermissions() throws SQLException {
        return permissionDAO.findAll();
    }

    /**
     * Delete user and permissions
     */
    public boolean deleteUserAndPermissions(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        permissionDAO.delete(userId);
        return userDAO.delete(userId);
    }

    /**
     * Get all users
     */
    public List<User> getAllUsers() throws SQLException {
        return userDAO.findAll();
    }

    /**
     * Check if user exists
     */
    private boolean userExists(String userId) throws SQLException {
        return userDAO.findById(userId) != null;
    }
}
