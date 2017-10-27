package com.company;

import com.company.util.DBConnector;
import com.company.util.ScriptRunner;

import java.io.*;
import java.sql.*;

public class TableCreator {
    public static void createAll() {

        System.out.println("Connecting to database...");

        Connection conn = null;
        Statement stmt = null;


        try {
            // Load the driver. This creates an instance of the driver
            // and calls the registerDriver method to make Oracle Thin
            // driver available to clients.

            Class.forName("com.mysql.jdbc.Driver");
            conn = DBConnector.getConnector().getConn();
            ScriptRunner runner = new ScriptRunner(conn, false, false);
            String file = "./sql/CreateTables.sql";
            runner.runScript(new BufferedReader(new FileReader(file)));
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