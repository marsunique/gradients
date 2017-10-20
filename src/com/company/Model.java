package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Model {
    public static boolean checkProfessorPassword(String username, String password){
        // TODO: add sql check of password and username

        for(int clear = 0; clear < 100; clear++)
        {
            System.out.println("\b") ;
        }
        return true;
    }

    public static void firstInit(){

        final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";

        try {

            // Load the driver. This creates an instance of the driver
            // and calls the registerDriver method to make Oracle Thin
            // driver available to clients.

            Class.forName("oracle.jdbc.driver.OracleDriver");

            String user = "dalambri";	// For example, "jsmith"
            String passwd = "200111823";	// Your 9 digit student ID number


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {

                // Get a connection from the first driver in the
                // DriverManager list that recognizes the URL jdbcURL

                conn = DriverManager.getConnection(jdbcURL, user, passwd);

                // Create a statement object that will be sending your
                // SQL statements to the DBMS

                stmt = conn.createStatement();

                // Create the COFFEES table

                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS 'Users'" +
                        "('user_id' INT NOT NULL AUTO_INCREMENT, " +
                        "'name' VARCHAR(45) NOT NULL" +
                        "'password' VARCHAR(45) NOT NULL, " +
                        "PRIMARY KEY ('user_id'), " +
                        "UNIQUE INDEX 'user_id_UNIQUE' ('user_id', ASC))");

                // Populate the COFFEES table

                stmt.executeUpdate("INSERT INTO Users " +
                        "VALUES (Dustin Lambright, dlambright)" +
                        "WHERE NOT EXISTS (SELECT * FROM TABLE Users WHERE name = Dustin Lambright)");

                // Get data from the COFFEES table

                rs = stmt.executeQuery("SELECT * FROM Users");

                // Now rs contains the rows of coffees and prices from
                // the COFFEES table. To access the data, use the method
                // NEXT to access all rows in rs, one row at a time

                while (rs.next()) {
                    String s = rs.getString("user_id");
                    float n = rs.getFloat("password");
                    System.out.println(s + "   " + n);
                }

            } finally {
                close(rs);
                close(stmt);
                close(conn);
            }
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
    }

    static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable whatever) {}
        }
    }
}
