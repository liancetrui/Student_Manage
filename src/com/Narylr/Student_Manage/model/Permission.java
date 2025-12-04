package com.Narylr.Student_Manage.model;

/**
 * Permission entity class
 */
public class Permission {
    private String permissionId;
    private String userName;
    private String writePermission; // "Y" or "N"
    private String readPermission;  // "Y" or "N"

    public Permission() {
    }

    public Permission(String permissionId, String userName, String writePermission, String readPermission) {
        this.permissionId = permissionId;
        this.userName = userName;
        this.writePermission = writePermission;
        this.readPermission = readPermission;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWritePermission() {
        return writePermission;
    }

    public void setWritePermission(String writePermission) {
        this.writePermission = writePermission;
    }

    public String getReadPermission() {
        return readPermission;
    }

    public void setReadPermission(String readPermission) {
        this.readPermission = readPermission;
    }

    public boolean canWrite() {
        return "Y".equals(writePermission);
    }

    public boolean canRead() {
        return "Y".equals(readPermission);
    }

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
