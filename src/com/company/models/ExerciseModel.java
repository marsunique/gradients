package com.company.models;

import com.company.objects.Course;
import com.company.objects.Exercise;
import com.company.objects.Student;
import com.company.util.DBConnector;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExerciseModel extends ModelBase {

    private static ExerciseModel exerciseModel;

    private ExerciseModel() throws IOException {
        super();
    }

    public Float getScore(Exercise ex, Student s) {
        float score = 0; // If nothing is found, their score is a 0.
        String query = null;
        Statement stmt = null;

        switch (ex.getPolicy()) {
            case "average":
                query = "SELECT AVG(score) as score " +
                        "FROM ATTEMPT " +
                        "WHERE student_id = '" + s.studentID + "' " +
                        "AND ex_id = " + ex.getId() + " " +
                        "GROUP BY student_id, ex_id;";
                break;
            case "last":
                // Will automatically get the one with the highest ex_id
                query = "SELECT score " +
                        "FROM attempt " +
                        "WHERE student_id = '" + s.studentID + "' " +
                        "AND ex_id =" + ex.getId() + ";";
                break;
            case "highest":
                query = "SELECT MAX(score) as score " +
                        "FROM ATTEMPT " +
                        "WHERE student_id = '" + s.studentID + "' " +
                        "AND ex_id = " + ex.getId() + " " +
                        "GROUP BY student_id, ex_id;";
                break;
        }

        // See if the user can log in
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                score = rs.getFloat("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return score;
    }

    public Exercise getExerciseById(int id) {
        Exercise ret = new Exercise();
        Statement stmt = null;

        String query = "SELECT * " +
                "FROM exercise " +
                "WHERE ex_id=" + id + "";

        // See if the user can log in
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ret.setPolicy(rs.getString("scoring_policy"));
                ret.setAdaptive(rs.getBoolean("adaptive"));
                ret.setCourseID(rs.getString("course_id"));
                ret.setEndDate(rs.getDate("end_date"));
                ret.setName(rs.getString("name"));
                ret.setNumRetries(rs.getInt("num_attempts"));
                ret.setPointsCorrect(rs.getFloat("right_points"));
                ret.setPointsIncorrect(rs.getFloat("wrong_points"));
                ret.setStartDate(rs.getDate("start_date"));
                ret.setMinDif(rs.getInt("min_dif"));
                ret.setMaxDif(rs.getInt("max_dif"));
                ret.setTopic(rs.getInt("topic_id"));
                ret.setId(rs.getInt("ex_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        if (ret.getCourseID() == null) {
            return null;
        }

        return ret;
    }

    public static ExerciseModel getExerciseModel() {

        if (exerciseModel == null) {
            try {
                exerciseModel = new ExerciseModel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return exerciseModel;
    }

    public void createExercies(Exercise exercise) throws SQLException {

        String query = "INSERT INTO Exercise " +
                "(wrong_points, right_points, name, num_attempts, adaptive, scoring_policy, course_id, topic_id, start_date, end_date, min_dif, max_dif) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setFloat(1, exercise.getPointsIncorrect());
        statement.setFloat(2, exercise.getPointsCorrect());
        statement.setString(3, exercise.getName());
        statement.setInt(4, exercise.getNumRetries());
        statement.setBoolean(5, exercise.getAdaptive());
        statement.setString(6, exercise.getPolicy());
        statement.setString(7, exercise.getCourseID());
        statement.setInt(8, exercise.getTopic());
        statement.setDate(9, exercise.getStart());
        statement.setDate(10, exercise.getEnd());
        statement.setInt(11, exercise.getMinDif());
        statement.setInt(12, exercise.getMaxDif());


        statement.execute();
    }

    public void deleteExercise(int exID, int courseID) throws SQLException {
        String query = "DELETE FROM Exercise WHERE course_id = ? AND ex_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, courseID);
        statement.setInt(2, exID);

        statement.execute();
    }
}
