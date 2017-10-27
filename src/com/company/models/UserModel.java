package com.company.models;

import com.company.objects.User;
import com.company.UserType;
import com.company.util.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserModel {

    public static User getUser(String username, String password) {
        User ret = new User();

        Connection conn = DBConnector.getConnection();
        Statement stmt = null;

        String query =
                "SELECT * " +
                        "FROM User" +
                        " WHERE id='" + username + "'" +
                        " AND password='" + password + "';";

        // See if the user can log in
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ret.firstName = rs.getString("first_name");
                ret.lastName = rs.getString("last_name");
                ret.username = rs.getString("id");
            }
        } catch (SQLException e) {
            DBConnector.closeConnection(conn);
            e.printStackTrace();
            return null;
        }

        if (ret.username == null) {
            System.out.println("Username and password combination not found.");
            DBConnector.closeConnection(conn);
            return null;
        }

        query =
                "SELECT *" +
                        "FROM Instructor" +
                        " WHERE inst_id='" + ret.username + "';";

        // Check if user is an instructor
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.first()) {
                // User is an instructor!
                ret.type = UserType.INSTRUCTOR;
            }
        } catch (SQLException e) {
            DBConnector.closeConnection(conn);
            e.printStackTrace();
            return null;
        }

        //Type hasn't been found, check for graduate.
        if (ret.type == null) {
            query =
                    "SELECT *" +
                            "FROM Graduate" +
                            " WHERE grad_id='" + ret.username + "';";

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.first()) {
                    // User is an instructor!
                    ret.type = UserType.GRADUATE;
                }
            } catch (SQLException e) {
                DBConnector.closeConnection(conn);
                e.printStackTrace();
                return null;
            }
        }

        // Type still hasn't been found, assume student
        if (ret.type == null) {
            ret.type = UserType.STUDENT;
        }

        // Get courses enrolled in
        if (ret.type == UserType.STUDENT || ret.type == UserType.GRADUATE) {
            query =
                    "SELECT *" +
                            "FROM EnrolledIn" +
                            " WHERE student_id='" + ret.username + "';";

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    ret.enrolled.add(rs.getString("course_id"));
                }
            } catch (SQLException e) {
                DBConnector.closeConnection(conn);
                e.printStackTrace();
                return null;
            }
        }

        // Get courses TAing for
        if (ret.type == UserType.GRADUATE) {
            query =
                    "SELECT *" +
                            "FROM TAFor" +
                            " WHERE ta_id='" + ret.username + "';";

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    ret.tas.add(rs.getString("course_id"));
                }
            } catch (SQLException e) {
                DBConnector.closeConnection(conn);
                e.printStackTrace();
                return null;
            }
        }

        // Get courses teaching
        if (ret.type == UserType.INSTRUCTOR) {
            query =
                    "SELECT *" +
                            "FROM Course" +
                            " WHERE inst_id='" + ret.username + "';";

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    ret.teaches.add(rs.getString("course_id"));
                }
            } catch (SQLException e) {
                DBConnector.closeConnection(conn);
                e.printStackTrace();
                return null;
            }
        }

        DBConnector.closeConnection(conn);
        return ret;
    }
}
