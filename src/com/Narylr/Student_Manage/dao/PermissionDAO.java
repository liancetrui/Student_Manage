package com.Narylr.Student_Manage.dao;

import com.Narylr.Student_Manage.model.Permission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Permission Data Access Object
 * 提供对权限数据的增删改查操作接口
 */
public class PermissionDAO {
    private final Connection connection;

    /**
     * 构造方法，初始化数据库连接
     */
    public PermissionDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * 根据用户ID查找权限信息
     *
     * @param userId 用户ID
     * @return 权限对象，如果未找到则返回null
     * @throws SQLException 数据库访问异常
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
     * 插入新的权限记录
     *
     * @param permission 权限对象
     * @return 插入成功返回true，否则返回false
     * @throws SQLException 数据库访问异常
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
     * 更新权限信息
     *
     * @param permission 权限对象
     * @return 更新成功返回true，否则返回false
     * @throws SQLException 数据库访问异常
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
     * 更新指定类型的权限值
     *
     * @param userId          用户ID
     * @param permissionType  权限类型（如p_WRITE或p_READ）
     * @param value           新的权限值
     * @return 更新成功返回true，否则返回false
     * @throws SQLException 数据库访问异常
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
     * 删除指定用户的权限记录
     *
     * @param userId 用户ID
     * @return 删除成功返回true，否则返回false
     * @throws SQLException 数据库访问异常
     */
    public boolean delete(String userId) throws SQLException {
        String sql = "DELETE FROM permissions WHERE p_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * 查询所有权限记录
     *
     * @return 包含所有权限对象的列表
     * @throws SQLException 数据库访问异常
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
     * 将查询结果集映射为Permission对象
     *
     * @param rs 查询结果集
     * @return 映射后的Permission对象
     * @throws SQLException 数据库访问异常
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
