package com.lamp.decoration.core;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Test {


    private static final String DB_URL = "jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 1. 加载驱动并建立连接
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to H2 Database.");

            // 2. 创建表
            createTable(conn);

            // 3. 插入数据
            insertData(conn, 1, "Alice", 25);
            insertData(conn, 2, "Bob", 30);

            // 4. 查询数据
            queryData(conn);

            // 5. 更新数据
            updateData(conn, 25, 26, "Alice");

            // 6. 再次查询验证更新
            System.out.println("\nAfter Update:");
            queryData(conn);

            // 7. 删除数据
            deleteData(conn, 2);

            // 8. 最终查询
            System.out.println("\nAfter Delete:");
            queryData(conn);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INT PRIMARY KEY, " +
                     "name VARCHAR(100), " +
                     "age INT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'users' created or already exists.");
        }
    }

    private static void insertData(Connection conn, int id, String name, int age) throws SQLException {
        String sql = "INSERT INTO users (id, name, age) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();
            System.out.println("Inserted user: " + name);
        }
    }

    private static void queryData(Connection conn) throws SQLException {
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Age: %d%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"));
            }
        }
    }

    private static void updateData(Connection conn, int oldAge, int newAge, String name) throws SQLException {
        String sql = "UPDATE users SET age = ? WHERE name = ? AND age = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newAge);
            pstmt.setString(2, name);
            pstmt.setInt(3, oldAge);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " row(s).");
        }
    }

    private static void deleteData(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " row(s).");
        }
    }
}
