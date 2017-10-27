package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlClient {
    private static final String url = "jdbc:mysql://localhost:3306/gradients";
    private static final String username = "root";
    private static final String password = "ygx123456";

    private static Connection conn = null;

    public static Connection getConn() {
        if( conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, username, password);
                System.out.println("Database connected!");
            } catch (ClassNotFoundException e) {
                System.err.println("Unable to get mysql driver: " + e);
            } catch (SQLException e) {
                System.err.println("Unable to connect to server: " + e);
            }
        }
        return conn;
    }
}
