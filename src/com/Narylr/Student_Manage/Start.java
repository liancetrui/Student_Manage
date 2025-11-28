package com.Narylr.Student_Manage;

public class Start {
    public Start(){
        System.out.println("欢迎使用学生数据管理系统");
        showLoadingMessage();
    }

    private void showLoadingMessage() {
        System.out.println("加载中，请稍后");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(400);
                System.out.print(".");
            } catch (InterruptedException e) {
                System.err.println("加载过程中出现错误: " + e.getMessage());
            }
        }
        System.out.println("\n加载完成！");
    }
}
