package com.Narylr.Student_Manage.service;

import com.Narylr.Student_Manage.dao.PermissionDAO;
import com.Narylr.Student_Manage.dao.UserDAO;
import com.Narylr.Student_Manage.model.Permission;
import com.Narylr.Student_Manage.model.User;

import java.sql.SQLException;

/**
 * Authentication Service
 */
public class AuthService {
    private final UserDAO userDAO;
    private final PermissionDAO permissionDAO;
    private User currentUser;

    public AuthService() {
        this.userDAO = new UserDAO();
        this.permissionDAO = new PermissionDAO();
    }

    /**
     * Login user
     */
    public User login(String userId, String password) throws SQLException {
        User user = userDAO.validateUser(userId, password);
        if (user != null) {
            this.currentUser = user;
        }
        return user;
    }

    /**
     * Register new user
     */
    public boolean register(String userId, String userName, String password) throws SQLException {
        // Check if admin exists
        boolean adminExists = userDAO.adminExists();
        
        // Create user with appropriate role
        String userRoot = adminExists ? "1" : "0";
        User user = new User(userId, userName, password, userRoot);
        
        // Insert user
        boolean userCreated = userDAO.insert(user);
        
        if (userCreated) {
            // Create default permissions
            String writePermission = adminExists ? "N" : "Y";
            String readPermission = "Y";
            Permission permission = new Permission(userId, userName, writePermission, readPermission);
            permissionDAO.insert(permission);
            return true;
        }
        return false;
    }

    /**
     * Check if admin exists
     */
    public boolean adminExists() throws SQLException {
        return userDAO.adminExists();
    }

    /**
     * Get current logged-in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logout current user
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Check if current user is admin
     */
    public boolean isCurrentUserAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }
}
