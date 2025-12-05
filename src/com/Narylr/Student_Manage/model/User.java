package com.Narylr.Student_Manage.model;

/**
 * User entity class
 * 用户实体类，用于表示系统中的用户信息
 */
public class User {
    private String userId;
    private String userName;
    private String password;
    private String userRoot; // "0" for admin, "1" for normal user

    /**
     * 无参构造函数
     */
    public User() {
    }

    /**
     * 有参构造函数，用于创建用户对象
     * @param userId 用户ID
     * @param userName 用户名
     * @param password 用户密码
     * @param userRoot 用户权限标识，"0"表示管理员，"1"表示普通用户
     */
    public User(String userId, String userName, String password, String userRoot) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userRoot = userRoot;
    }

    /**
     * 获取用户ID
     * @return 用户ID字符串
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     * @param userId 要设置的用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     * @return 用户名字符串
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     * @param userName 要设置的用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户密码
     * @return 用户密码字符串
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     * @param password 要设置的用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户权限标识
     * @return 用户权限标识字符串
     */
    public String getUserRoot() {
        return userRoot;
    }

    /**
     * 设置用户权限标识
     * @param userRoot 用户权限标识，"0"表示管理员，"1"表示普通用户
     */
    public void setUserRoot(String userRoot) {
        this.userRoot = userRoot;
    }

    /**
     * 判断用户是否为管理员
     * @return 如果是管理员返回true，否则返回false
     */
    public boolean isAdmin() {
        return "0".equals(userRoot);
    }

    /**
     * 重写toString方法，返回用户对象的字符串表示
     * @return 包含用户信息的字符串
     */
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userRoot='" + userRoot + '\'' +
                '}';
    }
}

