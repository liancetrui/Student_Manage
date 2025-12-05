package com.Narylr.Student_Manage.service;

import com.Narylr.Student_Manage.dao.PermissionDAO;
import com.Narylr.Student_Manage.dao.UserDAO;
import com.Narylr.Student_Manage.model.Permission;
import com.Narylr.Student_Manage.model.User;

import java.sql.SQLException;

/**
 * Authentication Service
 * 提供用户认证相关功能，包括登录、注册、权限检查等操作
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
     * 用户登录验证
     * @param userId 用户ID
     * @param password 密码
     * @return 验证成功的用户对象，如果验证失败则返回null
     * @throws SQLException 数据库操作异常
     */
    public User login(String userId, String password) throws SQLException {
        User user = userDAO.validateUser(userId, password);
        if (user != null) {
            this.currentUser = user;
        }
        return user;
    }

    /**
     * 注册新用户
     * @param userId 用户ID
     * @param userName 用户名
     * @param password 密码
     * @return 注册成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean register(String userId, String userName, String password) throws SQLException {
        // 检查是否已存在管理员账户
        boolean adminExists = userDAO.adminExists();

        // 根据是否存在管理员决定用户角色：若无管理员则创建管理员(角色为"0")，否则创建普通用户(角色为"1")
        String userRoot = adminExists ? "1" : "0";
        User user = new User(userId, userName, password, userRoot);

        // 插入用户信息到数据库
        boolean userCreated = userDAO.insert(user);

        if (userCreated) {
            // 创建默认权限配置：若已有管理员则写权限为"N"，否则为"Y"
            String writePermission = adminExists ? "N" : "Y";
            String readPermission = "Y";
            Permission permission = new Permission(userId, userName, writePermission, readPermission);
            permissionDAO.insert(permission);
            return true;
        }
        return false;
    }

    /**
     * 检查系统中是否存在管理员账户
     * @return 存在管理员返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean adminExists() throws SQLException {
        return userDAO.adminExists();
    }

    /**
     * 获取当前已登录的用户
     * @return 当前用户对象，未登录时返回null
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * 用户登出，清除当前用户状态
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * 判断当前登录用户是否为管理员
     * @return 当前用户是管理员返回true，否则返回false
     */
    public boolean isCurrentUserAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }
}
