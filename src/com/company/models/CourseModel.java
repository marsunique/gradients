
package com.company.models;

import com.company.objects.Course;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map listCourseTopics(String couresID) throws SQLException {
        String query = "SELECT * FROM CourseTopic WHERE course_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setString(1, couresID);

        ResultSet rs = statement.executeQuery();

        List<Integer> topicIDS = new ArrayList<>();

        while (rs.next()) {
            topicIDS.add(rs.getInt("topic_id"));
        }

        Map<Integer, String> topics = new HashMap<>();

        for(Integer topicID : topicIDS) {
            String query1 = "SELECT * FROM Topic WHERE topic_id = ?";
            PreparedStatement  stm = conn.prepareStatement(query1);
            stm.setInt(1, topicID);
            ResultSet rsTopics = stm.executeQuery();
            rsTopics.next();
            topics.put(topicID, rsTopics.getString("name"));
        }
        return topics;
    }
}
