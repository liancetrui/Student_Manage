package com.Narylr.Student_Manage.view;

import com.Narylr.Student_Manage.model.Permission;
import com.Narylr.Student_Manage.model.User;
import com.Narylr.Student_Manage.service.PermissionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户管理面板（仅限管理员使用）
 * 提供查看所有用户及其权限、授予权限、撤销权限以及删除用户的功能。
 */
public class UserManagementPanel extends JPanel {
    private final PermissionService permissionService;

    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton grantReadButton;
    private JButton grantWriteButton;
    private JButton revokeReadButton;
    private JButton revokeWriteButton;
    private JButton deleteUserButton;
    private JButton refreshButton;

    /**
     * 构造方法，初始化用户管理面板
     *
     * @param permissionService 权限服务对象，用于执行与用户及权限相关的数据库操作
     */
    public UserManagementPanel(PermissionService permissionService) {
        this.permissionService = permissionService;
        initializeUI();
        loadUserData();
    }

    /**
     * 初始化界面组件布局和控件设置
     * 包括标题标签、表格显示区域、按钮功能绑定等
     */
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 标题
        JLabel titleLabel = new JLabel("用户权限管理", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // 表格模型定义：不可编辑
        String[] columnNames = {"用户ID", "用户名", "角色", "读权限", "写权限"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setRowHeight(25);
        userTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // 按钮面板配置
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        grantReadButton = new JButton("授予读权限");
        grantReadButton.addActionListener(e -> handleGrantRead());

        grantWriteButton = new JButton("授予写权限");
        grantWriteButton.addActionListener(e -> handleGrantWrite());

        revokeReadButton = new JButton("撤销读权限");
        revokeReadButton.addActionListener(e -> handleRevokeRead());

        revokeWriteButton = new JButton("撤销写权限");
        revokeWriteButton.addActionListener(e -> handleRevokeWrite());

        deleteUserButton = new JButton("删除用户");
        deleteUserButton.addActionListener(e -> handleDeleteUser());

        refreshButton = new JButton("刷新");
        refreshButton.addActionListener(e -> loadUserData());

        buttonPanel.add(grantReadButton);
        buttonPanel.add(grantWriteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(revokeReadButton);
        buttonPanel.add(revokeWriteButton);
        buttonPanel.add(deleteUserButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * 加载并展示所有用户的数据到表格中
     * 同时获取每个用户的权限状态，并在表格中进行展示
     */
    private void loadUserData() {
        try {
            tableModel.setRowCount(0); // 清空现有数据
            List<User> users = permissionService.getAllUsers();
            List<Permission> permissions = permissionService.getAllPermissions();

            for (User user : users) {
                // 查找该用户的权限记录
                Permission permission = permissions.stream()
                    .filter(p -> p.getPermissionId().equals(user.getUserId()))
                    .findFirst()
                    .orElse(null);

                String role = user.isAdmin() ? "管理员" : "普通用户";
                String readPerm = permission != null ? permission.getReadPermission() : "N";
                String writePerm = permission != null ? permission.getWritePermission() : "N";

                Object[] row = {
                    user.getUserId(),
                    user.getUserName(),
                    role,
                    readPerm,
                    writePerm
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "加载用户数据失败: " + e.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 处理“授予读权限”按钮点击事件
     * 对选中的用户授予读取权限
     */
    private void handleGrantRead() {
        String userId = getSelectedUserId();
        if (userId == null) return;

        try {
            boolean success = permissionService.grantReadPermission(userId);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "授予读权限成功",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
                loadUserData(); // 刷新列表
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "操作失败: " + e.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 处理“授予写权限”按钮点击事件
     * 对选中的用户授予写入权限
     */
    private void handleGrantWrite() {
        String userId = getSelectedUserId();
        if (userId == null) return;

        try {
            boolean success = permissionService.grantWritePermission(userId);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "授予写权限成功",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
                loadUserData(); // 刷新列表
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "操作失败: " + e.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 处理“撤销读权限”按钮点击事件
     * 对选中的用户撤销其读取权限
     */
    private void handleRevokeRead() {
        String userId = getSelectedUserId();
        if (userId == null) return;

        try {
            boolean success = permissionService.revokeReadPermission(userId);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "撤销读权限成功",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
                loadUserData(); // 刷新列表
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "操作失败: " + e.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 处理“撤销写权限”按钮点击事件
     * 对选中的用户撤销其写入权限
     */
    private void handleRevokeWrite() {
        String userId = getSelectedUserId();
        if (userId == null) return;

        try {
            boolean success = permissionService.revokeWritePermission(userId);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "撤销写权限成功",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
                loadUserData(); // 刷新列表
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "操作失败: " + e.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 处理“删除用户”按钮点击事件
     * 删除当前选中的用户及其对应的权限信息
     * 不允许删除管理员账户
     */
    private void handleDeleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "请先选择要删除的用户",
                "提示",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        String userName = (String) tableModel.getValueAt(selectedRow, 1);
        String role = (String) tableModel.getValueAt(selectedRow, 2);

        // 防止误删管理员账号
        if ("管理员".equals(role)) {
            JOptionPane.showMessageDialog(this,
                "不能删除管理员账户",
                "错误",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "确定要删除用户 " + userName + " (ID: " + userId + ") 吗？\n这将同时删除用户的权限信息。",
            "确认删除",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = permissionService.deleteUserAndPermissions(userId);
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "删除成功",
                        "成功",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadUserData(); // 刷新列表
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                    "删除失败: " + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 获取当前选中行的用户ID
     * 若未选择任何行则弹出警告对话框并返回null
     *
     * @return 当前选中用户的ID字符串；若无选中项则返回null
     */
    private String getSelectedUserId() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "请先选择一个用户", 
                "提示", 
                JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return (String) tableModel.getValueAt(selectedRow, 0);
    }
}
