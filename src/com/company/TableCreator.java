package com.company;

import javax.naming.*;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class TableCreator {
    public static void createAll() {


        final String url = "jdbc:mysql://localhost:3306/gradients";
        final String username = "root";
        final String password = "PWD123admin";

        System.out.println("Connecting to database...");

        Connection conn = null;
        Statement stmt = null;


        try {
            // Load the driver. This creates an instance of the driver
            // and calls the registerDriver method to make Oracle Thin
            // driver available to clients.

            Class.forName("com.mysql.jdbc.Driver");


            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, username, password);
                System.out.println("Database connected!");
            } catch (ClassNotFoundException e) {
                System.err.println("Unable to get mysql driver: " + e);
            } catch (SQLException e) {
                System.err.println("Unable to connect to server: " + e);
            }
            ScriptRunner runner = new ScriptRunner(conn, false, false);
            String file = "./sql/CreateTables.sql";
            runner.runScript(new BufferedReader(new FileReader(file)));

//            try {
//
//                // Get a connection from the first driver in the
//                // DriverManager list that recognizes the URL jdbcURL
//
//                conn = DriverManager.getConnection(url, username, password);
//                System.out.println("Database connected!");
//
//                // Create a statement object that will be sending your
//                // SQL statements to the DBMS
//
//                stmt = conn.createStatement();
//
//                // Create all required tables
//            } finally {
//                close(stmt);
//                close(conn);
//            }
        } catch (Throwable oops) {
            throw new IllegalStateException("Cannot connect the database!", oops);
        } finally {
            close(conn);
        }
    }

    static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Throwable whatever) {
            }
        }
    }
}
