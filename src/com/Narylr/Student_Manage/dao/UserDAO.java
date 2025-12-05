package com.Narylr.Student_Manage.dao;

import com.Narylr.Student_Manage.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User Data Access Object
 * 提供对用户数据的增删改查操作，封装了与数据库交互的逻辑。
 */
public class UserDAO {
    private final Connection connection;

    /**
     * 构造方法：初始化数据库连接
     * 使用单例模式获取数据库连接实例。
     */
    public UserDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * 根据用户ID查找用户信息
     *
     * @param userId 用户唯一标识符
     * @return 查询到的User对象；如果未找到则返回null
     * @throws SQLException 数据库访问异常
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
     * 检查是否存在管理员账户（u_ROOT='0'表示管理员）
     *
     * @return 存在管理员返回true，否则返回false
     * @throws SQLException 数据库访问异常
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
     * 插入新用户记录
     *
     * @param user 要插入的用户对象
     * @return 插入成功返回true，失败返回false
     * @throws SQLException 数据库访问异常
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
     * 更新已有用户的详细信息
     *
     * @param user 包含更新后信息的用户对象
     * @return 更新成功返回true，失败返回false
     * @throws SQLException 数据库访问异常
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
     * 删除指定ID的用户记录
     *
     * @param userId 要删除的用户ID
     * @return 删除成功返回true，失败返回false
     * @throws SQLException 数据库访问异常
     */
    public boolean delete(String userId) throws SQLException {
        String sql = "DELETE FROM users WHERE u_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * 获取所有用户列表
     *
     * @return 所有用户组成的List集合
     * @throws SQLException 数据库访问异常
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
     * 验证用户登录凭据是否正确
     *
     * @param userId   用户名/账号ID
     * @param password 密码
     * @return 凭据正确的用户对象；验证失败返回null
     * @throws SQLException 数据库访问异常
     */
    public User validateUser(String userId, String password) throws SQLException {
        User user = findById(userId);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * 将查询结果集映射为User实体对象
     *
     * @param rs 查询结果集
     * @return 映射后的User对象
     * @throws SQLException 结果集读取异常
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
