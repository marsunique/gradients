package com.company.util;

import com.company.util.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class TableCreator {

    public static void createAll() throws IOException{

        Connection conn = DBConnector.getConnector().getConn();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        String file = "./sql/CreateTables.sql";

        try {
            runner.runScript(new BufferedReader(new FileReader(file)));
        } catch (IOException e) {
            System.err.print("Error when reading file: " + e);
        } catch (SQLException e) {
            System.err.print("Error when executing SQL: " + e);
        } finally {
            DBConnector.closeConnection(conn);
        }
    }

    public static void resetDB() throws IOException{
        Connection conn = DBConnector.getConnector().getConn();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        String file = null;

        try {
            file = "./sql/DropTables.sql";
            runner.runScript(new BufferedReader(new FileReader(file)));
            file = "./sql/CreateTables.sql";
            runner.runScript(new BufferedReader(new FileReader(file)));
            file = "./sql/SampleData.sql";
            runner.runScript(new BufferedReader(new FileReader(file)));
        } catch (IOException e) {
            System.err.print("Error when reading file: " + e);
        } catch (SQLException e) {
            System.err.print("Error when executing SQL: " + e);
        } finally {
            DBConnector.closeConnection(conn);
        }
    }
}
