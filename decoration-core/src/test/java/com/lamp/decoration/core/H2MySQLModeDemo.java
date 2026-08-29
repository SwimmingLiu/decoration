package com.lamp.decoration.core;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class H2MySQLModeDemo {

    // 关键配置：在JDBC URL中添加 ;MODE=MySQL 以启用兼容模式
    private static final String DB_URL = "jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            // 加载H2驱动
            Class.forName("org.h2.Driver");

            // 建立连接
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("成功连接到H2数据库 (MySQL模式)");

            Statement stmt = conn.createStatement();

            // 1. 创建表：使用MySQL特有的 AUTO_INCREMENT 语法
            // 在非MySQL模式下，此语句可能会报错或行为不同
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                                    "username VARCHAR(50) NOT NULL, " +
                                    "email VARCHAR(100), " +
                                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                                    ")";
            stmt.execute(createTableSQL);
            System.out.println("表 'users' 创建成功");

            // 2. 插入数据：省略自增主键 id
            String insertSQL = "INSERT INTO users (username, email) VALUES ('alice', 'alice@example.com'), ('bob', 'bob@example.com')";
            stmt.executeUpdate(insertSQL);
            System.out.println("插入2条数据成功");

            // 3. 查询数据
            String selectSQL = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(selectSQL);
            System.out.println("\n查询结果：");
            System.out.println("ID\tUsername\tEmail\t\t\tCreated At");
            while (rs.next()) {
                System.out.printf("%d\t%s\t\t%s\t%s%n",
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at"));
            }

            // 4. 验证MySQL特有函数：使用 GROUP_CONCAT (H2在MySQL模式下支持)
            String groupConcatSQL = "SELECT GROUP_CONCAT(username SEPARATOR ', ') as user_list FROM users";
            ResultSet rsGroup = stmt.executeQuery(groupConcatSQL);
            if (rsGroup.next()) {
                System.out.println("\nGROUP_CONCAT 结果: " + rsGroup.getString("user_list"));
            }

            // 关闭资源
            rs.close();
            rsGroup.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

