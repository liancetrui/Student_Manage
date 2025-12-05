package com.Narylr.Student_Manage.model;

/**
 * Permission entity class
 * 权限实体类，用于表示用户的读写权限信息
 */
public class Permission {
    private String permissionId;
    private String userName;
    private String writePermission; // "Y" or "N"
    private String readPermission;  // "Y" or "N"

    /**
     * 无参构造函数
     */
    public Permission() {
    }

    /**
     * 有参构造函数，用于初始化权限对象的所有属性
     *
     * @param permissionId 权限ID
     * @param userName 用户名
     * @param writePermission 写权限标识，"Y"表示有写权限，"N"表示无写权限
     * @param readPermission 读权限标识，"Y"表示有读权限，"N"表示无读权限
     */
    public Permission(String permissionId, String userName, String writePermission, String readPermission) {
        this.permissionId = permissionId;
        this.userName = userName;
        this.writePermission = writePermission;
        this.readPermission = readPermission;
    }

    /**
     * 获取权限ID
     *
     * @return 权限ID
     */
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * 设置权限ID
     *
     * @param permissionId 权限ID
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取写权限标识
     *
     * @return 写权限标识，"Y"表示有写权限，"N"表示无写权限
     */
    public String getWritePermission() {
        return writePermission;
    }

    /**
     * 设置写权限标识
     *
     * @param writePermission 写权限标识，应为"Y"或"N"
     */
    public void setWritePermission(String writePermission) {
        this.writePermission = writePermission;
    }

    /**
     * 获取读权限标识
     *
     * @return 读权限标识，"Y"表示有读权限，"N"表示无读权限
     */
    public String getReadPermission() {
        return readPermission;
    }

    /**
     * 设置读权限标识
     *
     * @param readPermission 读权限标识，应为"Y"或"N"
     */
    public void setReadPermission(String readPermission) {
        this.readPermission = readPermission;
    }

    /**
     * 判断用户是否具有写权限
     *
     * @return true表示有写权限，false表示无写权限
     */
    public boolean canWrite() {
        return "Y".equals(writePermission);
    }

    /**
     * 判断用户是否具有读权限
     *
     * @return true表示有读权限，false表示无读权限
     */
    public boolean canRead() {
        return "Y".equals(readPermission);
    }

    /**
     * 重写toString方法，返回权限对象的字符串表示
     *
     * @return 包含所有权限信息的字符串
     */
    @Override
    public String toString() {
        return "Permission{" +
                "permissionId='" + permissionId + '\'' +
                ", userName='" + userName + '\'' +
                ", writePermission='" + writePermission + '\'' +
                ", readPermission='" + readPermission + '\'' +
                '}';
    }
}

