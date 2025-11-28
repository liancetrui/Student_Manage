package com.Narylr.Student_Manage;

import com.Narylr.Student_Manage.Tools.Login;
import com.Narylr.Student_Manage.Tools.firstLogin;
import com.Narylr.Student_Manage.Tools.CheckRoot;


public class App {
    public static void main(String[] args) throws Exception {
        // 启动应用时首先调用 Start 类进行初始化
        new Start();

        // 判断是否存在管理员账户
        if (new CheckRoot().checkUserRoot()) {
            // 如果管理员存在，直接进入登录界面
            new Login();
        } else {
            // 如果没有管理员，进入首次登录并注册管理员
            new firstLogin();
        }
    }
}
