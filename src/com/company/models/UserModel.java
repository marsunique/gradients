package com.company.models;

import com.company.objects.User;
import com.company.util.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserModel {

    private static String loginQuery(String username, String password) {
        return "SELECT * " +
                "FROM User" +
                " WHERE id='" + username + "'" +
                " AND password='" + password + "';";

    }

    private static String userByIdQuery(String username) {
        return "SELECT * " +
                "FROM User" +
                " WHERE id='" + username + "'";
    }

    public static User getUser(String username, String password) {
        User ret = User.getUser();

        Connection conn = null;
        try {
            conn = DBConnector.getConnector().getConn();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Statement stmt = null;

        String query = null;
        if (password == null) {
            query = userByIdQuery(username);
        } else {
            query = loginQuery(username, password);
        }

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
            e.printStackTrace();
            return null;
        }

        if (ret.username == null) {
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
                ret.type = User.Type.INSTRUCTOR;
            }
        } catch (SQLException e) {
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
                    ret.type = User.Type.GRADUATE;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        // Type still hasn't been found, assume student
        if (ret.type == null) {
            ret.type = User.Type.STUDENT;
        }

        // Get courses enrolled in
        if (ret.type == User.Type.STUDENT || ret.type == User.Type.GRADUATE) {
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
                e.printStackTrace();
                return null;
            }
        }

        // Get courses TAing for
        if (ret.type == User.Type.GRADUATE) {
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
                e.printStackTrace();
                return null;
            }
        }

        // Get courses teaching
        if (ret.type == User.Type.INSTRUCTOR) {
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
                e.printStackTrace();
                return null;
            }
        }

        return ret;
    }
}
