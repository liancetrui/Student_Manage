package com.Narylr.Student_Manage.service;

import com.Narylr.Student_Manage.dao.PermissionDAO;
import com.Narylr.Student_Manage.dao.UserDAO;
import com.Narylr.Student_Manage.model.Permission;
import com.Narylr.Student_Manage.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 权限管理服务类，提供用户权限的查询、授予、撤销以及用户信息相关操作。
 */
public class PermissionService {
    private final PermissionDAO permissionDAO;
    private final UserDAO userDAO;

    /**
     * 构造方法，初始化权限数据访问对象和用户数据访问对象。
     */
    public PermissionService() {
        this.permissionDAO = new PermissionDAO();
        this.userDAO = new UserDAO();
    }

    /**
     * 根据用户ID获取用户的权限信息。
     *
     * @param userId 用户唯一标识符
     * @return 返回与指定用户ID关联的权限对象，如果不存在则返回null
     * @throws SQLException 数据库访问异常时抛出
     */
    public Permission getPermission(String userId) throws SQLException {
        return permissionDAO.findById(userId);
    }

    /**
     * 授予指定用户读取权限。
     *
     * @param userId 用户唯一标识符
     * @return 操作成功返回true，否则返回false
     * @throws SQLException 当数据库访问异常或用户不存在时抛出
     */
    public boolean grantReadPermission(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        return permissionDAO.updatePermissionType(userId, "p_READ", "Y");
    }

    /**
     * 授予指定用户写入权限。
     *
     * @param userId 用户唯一标识符
     * @return 操作成功返回true，否则返回false
     * @throws SQLException 当数据库访问异常或用户不存在时抛出
     */
    public boolean grantWritePermission(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        return permissionDAO.updatePermissionType(userId, "p_WRITE", "Y");
    }

    /**
     * 撤销指定用户的读取权限。
     *
     * @param userId 用户唯一标识符
     * @return 操作成功返回true，否则返回false
     * @throws SQLException 当数据库访问异常或用户不存在时抛出
     */
    public boolean revokeReadPermission(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        return permissionDAO.updatePermissionType(userId, "p_READ", "N");
    }

    /**
     * 撤销指定用户的写入权限。
     *
     * @param userId 用户唯一标识符
     * @return 操作成功返回true，否则返回false
     * @throws SQLException 当数据库访问异常或用户不存在时抛出
     */
    public boolean revokeWritePermission(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        return permissionDAO.updatePermissionType(userId, "p_WRITE", "N");
    }

    /**
     * 判断指定用户是否具有读取权限。
     *
     * @param userId 用户唯一标识符
     * @return 若用户存在且拥有读取权限返回true，否则返回false
     * @throws SQLException 数据库访问异常时抛出
     */
    public boolean hasReadPermission(String userId) throws SQLException {
        Permission permission = permissionDAO.findById(userId);
        return permission != null && permission.canRead();
    }

    /**
     * 判断指定用户是否具有写入权限。
     *
     * @param userId 用户唯一标识符
     * @return 若用户存在且拥有写入权限返回true，否则返回false
     * @throws SQLException 数据库访问异常时抛出
     */
    public boolean hasWritePermission(String userId) throws SQLException {
        Permission permission = permissionDAO.findById(userId);
        return permission != null && permission.canWrite();
    }

    /**
     * 获取所有用户的权限列表。
     *
     * @return 包含所有权限信息的列表
     * @throws SQLException 数据库访问异常时抛出
     */
    public List<Permission> getAllPermissions() throws SQLException {
        return permissionDAO.findAll();
    }

    /**
     * 删除指定用户及其对应的权限记录。
     *
     * @param userId 用户唯一标识符
     * @return 删除操作成功返回true，否则返回false
     * @throws SQLException 当数据库访问异常或用户不存在时抛出
     */
    public boolean deleteUserAndPermissions(String userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("User with ID " + userId + " does not exist");
        }
        permissionDAO.delete(userId);
        return userDAO.delete(userId);
    }

    /**
     * 获取系统中所有的用户信息。
     *
     * @return 所有用户的列表
     * @throws SQLException 数据库访问异常时抛出
     */
    public List<User> getAllUsers() throws SQLException {
        return userDAO.findAll();
    }

    /**
     * 验证指定用户是否存在。
     *
     * @param userId 用户唯一标识符
     * @return 存在返回true，否则返回false
     * @throws SQLException 数据库访问异常时抛出
     */
    private boolean userExists(String userId) throws SQLException {
        return userDAO.findById(userId) != null;
    }
}
