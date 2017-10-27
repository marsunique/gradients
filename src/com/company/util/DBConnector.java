package com.company.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private final String url = "jdbc:mysql://localhost:3306/gradients";
    private String username = "";
    private String password = "";

    private static DBConnector instance;

    private static Connection conn = null;

    private DBConnector() throws IOException {
        String[] stringArray = getUnameandPassword();
        username = stringArray[0];
        password = stringArray[1];
    }

    public static DBConnector getConnector() throws IOException {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    public Connection getConn() {
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

    private String[] getUnameandPassword() throws IOException {
        String filePath = new File("").getAbsolutePath();

        try(BufferedReader br = new BufferedReader(new FileReader("src/com/company/password.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            String[] stringArray = everything.split("\n");

            return stringArray;
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
            throw ex;
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
