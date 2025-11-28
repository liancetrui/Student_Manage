package com.Narylr.Student_Manage.connect;

import java.io.*;
import java.net.NetworkInterface;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import java.sql.*;

public class Driver {
    public static Connection connection;
    private static final String CONFIG_FILE = "db_config.properties";  // 配置文件路径
    private static final String DEVICE_ID_FILE = "device_id.txt";  // 存储设备标识符的文件

    public void getConnection() {
        try {
            // 检查配置文件是否存在
            File configFile = new File(CONFIG_FILE);
            if (!configFile.exists() || !isSameDevice()) {
                // 如果配置文件不存在或设备标识符不匹配，提示用户输入数据库账号和密码
                System.out.println("首次使用系统或设备已更换，请输入数据库连接信息");

                String username = getUserInput("请输入数据库用户名: ");
                String password = getUserInput("请输入数据库密码: ");

                // 验证账号密码是否正确
                while (!isDatabaseCredentialsValid(username, password)) {
                    System.out.println("数据库用户名或密码错误，请重新输入。");
                    username = getUserInput("请输入数据库用户名: ");
                    password = getUserInput("请输入数据库密码: ");
                }

                saveDatabaseConfig(username, password);  // 保存配置到文件
            }

            // 获取数据库连接（连接到 MySQL 服务）
            String username = loadDatabaseConfig()[0];  // 从配置文件读取用户名
            String password = loadDatabaseConfig()[1];  // 从配置文件读取密码
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", username, password);

            // 创建数据库，如果不存在的话
            Create create = new Create(connection);
            create.createDatabase();  // 创建数据库

            // 连接到 'java' 数据库
            connection.setCatalog("java");

            // 创建表
            create.createTables();  // 检查并创建表

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败，请确保 MySQL 服务已启动。");
            System.out.println("请手动启动 MySQL 服务，然后重新运行程序。");
        }
    }

    // 获取用户输入
    private String getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // 检查数据库账号密码是否正确
    private boolean isDatabaseCredentialsValid(String username, String password) {
        try (Connection testConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", username, password)) {
            return testConnection != null;
        } catch (SQLException e) {
            return false;
        }
    }

    // 保存数据库配置（用户名和密码）到配置文件，密码使用Base64编码
    private void saveDatabaseConfig(String username, String password) {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE);
             OutputStreamWriter writer = new OutputStreamWriter(fos)) {
            Properties properties = new Properties();
            properties.setProperty("db_username", username);
            properties.setProperty("db_password", encodeBase64(password));  // Base64编码密码

            // 保存设备标识符
            String deviceId = getDeviceId();
            properties.setProperty("device_id", deviceId);  // 保存设备ID

            properties.store(writer, "Database Configuration");
            System.out.println("数据库连接信息已保存");

            // 保存设备标识符到文件中
            saveDeviceId(deviceId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从配置文件加载数据库配置（用户名和密码）
    private String[] loadDatabaseConfig() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE);
             InputStreamReader reader = new InputStreamReader(fis)) {
            properties.load(reader);

            String username = properties.getProperty("db_username");
            String encodedPassword = properties.getProperty("db_password");
            String decodedPassword = decodeBase64(encodedPassword);  // 解码密码

            return new String[]{username, decodedPassword};
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{"", ""};
        }
    }

    // 使用Base64编码密码
    private String encodeBase64(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    // 使用Base64解码密码
    private String decodeBase64(String encodedData) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
        return new String(decodedBytes);
    }

    // 获取设备的唯一标识符（使用MAC地址）
    private String getDeviceId() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = networkInterfaces.nextElement();
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X", mac[i]));
                    }
                    return sb.toString();  // 返回设备唯一标识符（MAC地址）
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // 如果无法获取MAC地址，返回null
    }

    // 保存设备ID到文件中
    private void saveDeviceId(String deviceId) {
        try (FileWriter writer = new FileWriter(DEVICE_ID_FILE)) {
            writer.write(deviceId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 检查当前设备是否与之前保存的设备相同
    private boolean isSameDevice() {
        try {
            File deviceIdFile = new File(DEVICE_ID_FILE);
            if (!deviceIdFile.exists()) {
                return false;  // 如果没有设备ID文件，说明是第一次使用
            }

            BufferedReader reader = new BufferedReader(new FileReader(deviceIdFile));
            String savedDeviceId = reader.readLine();
            String currentDeviceId = getDeviceId();
            return savedDeviceId != null && savedDeviceId.equals(currentDeviceId);
        } catch (IOException e) {
            return false;
        }
    }
}
