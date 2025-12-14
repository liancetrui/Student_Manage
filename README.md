# 学生管理系统 - 重构

## 重构概述

本次重构将原有的控制台应用程序完全转换为基于 Java Swing 的图形界面应用程序，并采用标准的分层架构模式，实现了代码的清晰分离和模块化。

## 新架构设计

### 1. Model 层 (模型层)
**位置**: `com.Narylr.Student_Manage.model`

包含三个实体类：
- **User.java** - 用户实体
- **Student.java** - 学生实体  
- **Permission.java** - 权限实体

**特点**:
- 标准的 JavaBean 设计
- 提供完整的 getter/setter 方法
- 实现了 toString() 方法便于调试

### 2. DAO 层 (数据访问层)
**位置**: `com.Narylr.Student_Manage.dao`

包含四个类：
- **DatabaseConnection.java** - 数据库连接单例
- **UserDAO.java** - 用户数据访问对象
- **StudentDAO.java** - 学生数据访问对象
- **PermissionDAO.java** - 权限数据访问对象

**特点**:
- 使用单例模式管理数据库连接
- 提供完整的 CRUD 操作
- 使用 try-with-resources 自动管理资源
- 统一的异常处理

### 3. Service 层 (业务逻辑层)
**位置**: `com.Narylr.Student_Manage.service`

包含三个服务类：
- **AuthService.java** - 认证服务（登录、注册、权限检查）
- **StudentService.java** - 学生管理服务
- **PermissionService.java** - 权限管理服务

**特点**:
- 封装业务逻辑
- 处理业务验证
- 协调多个 DAO 层操作

### 4. View 层 (视图层)
**位置**: `com.Narylr.Student_Manage.view`

包含六个 GUI 组件：
- **LoginFrame.java** - 登录窗口
- **RegisterDialog.java** - 注册对话框
- **MainFrame.java** - 主窗口框架
- **StudentManagementPanel.java** - 学生管理面板
- **StudentDialog.java** - 学生添加/编辑对话框
- **UserManagementPanel.java** - 用户管理面板

**特点**:
- 使用 Java Swing 组件
- 响应式布局设计
- 完整的用户交互反馈
- 权限控制集成

## 核心功能

### 1. 用户认证系统
- ✅ 用户登录
- ✅ 用户注册
- ✅ 首次使用自动创建管理员
- ✅ 注销功能

### 2. 学生信息管理
- ✅ 添加学生信息
- ✅ 修改学生信息
- ✅ 删除学生信息
- ✅ 查询学生信息
- ✅ 查看所有学生列表
- ✅ 按学号搜索

### 3. 权限管理 (管理员功能)
- ✅ 授予/撤销读权限
- ✅ 授予/撤销写权限
- ✅ 删除普通用户
- ✅ 查看所有用户及其权限

### 4. 权限控制
- ✅ 管理员拥有所有权限
- ✅ 普通用户根据授权进行操作
- ✅ 界面按钮根据权限动态启用/禁用

## 技术栈

- **Java SE** - 核心编程语言
- **Java Swing** - GUI 框架
- **JDBC** - 数据库连接
- **MySQL** - 数据库系统

## 设计模式应用

1. **单例模式** - DatabaseConnection
2. **MVC 模式** - 整体架构分层
3. **DAO 模式** - 数据访问层设计

## 与原系统对比

### 原系统
- ❌ 控制台界面
- ❌ 代码混乱，所有逻辑集中在 Tools 包
- ❌ 难以维护和扩展
- ❌ 用户体验差

### 新系统
- ✅ 现代化图形界面
- ✅ 清晰的分层架构
- ✅ 易于维护和扩展
- ✅ 良好的用户体验
- ✅ 完善的错误处理
- ✅ 权限控制更加直观

## 如何运行

1. 确保 MySQL 数据库服务已启动
2. 运行 `App.java` 主类
3. 首次运行会提示创建管理员账户
4. 登录后即可使用所有功能

## 数据库配置

系统首次运行时会自动：
1. 提示输入数据库用户名和密码
2. 创建 `java` 数据库（如果不存在）
3. 创建必要的表结构：
   - `users` - 用户表
   - `student` - 学生表
   - `permissions` - 权限表

配置信息将加密保存在 `db_config.properties` 文件中。

## 未来扩展建议

1. 添加数据导入/导出功能
2. 实现更复杂的查询和统计功能
3. 添加日志记录系统
4. 实现数据备份和恢复
5. 添加学生成绩管理模块
6. 实现密码加密存储

## 文件清单

### 保留的原有文件
- `connect/Driver.java` - 数据库驱动（被 DAO 层使用）
- `connect/Create.java` - 数据库和表创建（被 Driver 使用）
- `Start.java` - 可以删除，已不再使用
- `Tools/*` - 可以删除，功能已迁移到新架构

### 新增文件
```
src/com/Narylr/Student_Manage/
├── model/
│   ├── User.java
│   ├── Student.java
│   └── Permission.java
├── dao/
│   ├── DatabaseConnection.java
│   ├── UserDAO.java
│   ├── StudentDAO.java
│   └── PermissionDAO.java
├── service/
│   ├── AuthService.java
│   ├── StudentService.java
│   └── PermissionService.java
└── view/
    ├── LoginFrame.java
    ├── RegisterDialog.java
    ├── MainFrame.java
    ├── StudentManagementPanel.java
    ├── StudentDialog.java
    └── UserManagementPanel.java
```

## 总结

本次重构成功地将一个混乱的控制台应用程序转换为结构清晰、易于维护的 GUI 应用程序。新系统采用了业界标准的分层架构，使得代码职责明确，各层之间低耦合高内聚，为未来的功能扩展打下了良好的基础。
