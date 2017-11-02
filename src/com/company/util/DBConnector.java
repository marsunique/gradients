package com.company.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

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

        Scanner in = new Scanner(new File("src/com/company/password.txt"));
        ArrayList<String> stringList = new ArrayList<>();

        while(in.hasNext()){
            stringList.add(in.nextLine());
        }

        return stringList.toArray(new String[stringList.size()]);
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
