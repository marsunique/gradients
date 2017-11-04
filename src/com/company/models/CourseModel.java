
package com.company.models;

import com.company.objects.Course;

import java.io.IOException;
import java.sql.*;

public class CourseModel extends ModelBase {

    private static CourseModel courseModel;

    public CourseModel() throws IOException {
        super();
    }

    public static CourseModel getCourseModel() {

        if (courseModel == null) {
            try {
                courseModel = new CourseModel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return courseModel;
    }

    public Course getCourseByID(String id) {
        Course ret = new Course();
        Statement stmt = null;

        String query = "SELECT * " +
                "FROM Course " +
                "WHERE course_id='" + id + "'";

        // See if the user can log in
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ret.id = rs.getString("course_id");
                ret.name = rs.getString("course_name");
                ret.start = rs.getDate("start_date");
                ret.end = rs.getDate("end_date");
                ret.instructor = rs.getString("inst_id");
                ret.graduate = rs.getBoolean("graduate");
                ret.maxEnrolled = rs.getInt("max_enrolled");
                ret.numEnrolled = rs.getInt("num_enrolled");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        if (ret.id == null) {
            return null;
        }

        query = "SELECT * " +
                "FROM Exercise " +
                "WHERE course_id='" + id + "';";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ret.exerciseIds.add(rs.getInt("ex_id"));
                ret.exerciseNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    public Boolean addStudent(String student, String course) throws SQLException {

        String query = "REPLACE " +
                "INTO EnrolledIn (student_id, course_id) " +
                "VALUES (?,?)";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, student);
        statement.setString(2, course);

        return statement.execute();
    }

    public Boolean dropStudent(String student, String course) throws SQLException {

        String query = "DELETE FROM EnrolledIn " +
                "WHERE student_id='" + student + "' " +
                "AND course_id='" + course + "';";

        PreparedStatement statement = conn.prepareStatement(query);

        return statement.execute();
    }

}
