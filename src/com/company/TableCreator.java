package com.company;

import javax.naming.*;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

public class TableCreator {
    public static void createAll() {

        String username = "";
        String password = "";
        final String url = "jdbc:mysql://localhost:3306/gradients";
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


            username = stringArray[0];
            password = stringArray[1];
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }




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