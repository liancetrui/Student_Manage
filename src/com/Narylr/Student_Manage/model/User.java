package com.Narylr.Student_Manage.model;

/**
 * User entity class
 */
public class User {
    private String userId;
    private String userName;
    private String password;
    private String userRoot; // "0" for admin, "1" for normal user

    public User() {
    }

    public User(String userId, String userName, String password, String userRoot) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userRoot = userRoot;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRoot() {
        return userRoot;
    }

    public void setUserRoot(String userRoot) {
        this.userRoot = userRoot;
    }

    public boolean isAdmin() {
        return "0".equals(userRoot);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userRoot='" + userRoot + '\'' +
                '}';
    }
}
