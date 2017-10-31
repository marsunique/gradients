package com.company.models;

import com.company.objects.Exercise;
import com.company.util.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ExerciseModel {

    private static ExerciseModel exerciseModel;
    private static Connection conn = null;

    private ExerciseModel() throws IOException {
        conn = DBConnector.getConnector().getConn();
    }

    public static ExerciseModel getExerciseModel() throws IOException{

        if (exerciseModel == null) {
            exerciseModel = new ExerciseModel();
        }

        return exerciseModel;
    }

    public void createExercies(Exercise exercise) throws SQLException {

        String query = "INSERT INTO Exercise(name, scoring_policy, " +
                "num_attempts, start_time, end_time, adaptive, " +
                "right_points, wrong_points, course_id) VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, exercise.getName());
        statement.setString(2, exercise.getPolicy());
        statement.setInt(3, exercise.getNumRetries());
        statement.setTimestamp(4, exercise.getStart());
        statement.setTimestamp(5, exercise.getEnd());
        statement.setBoolean(6, exercise.getAdaptive());
        statement.setDouble(7, exercise.getPointsCorrect());
        statement.setDouble(8, exercise.getPointsIncorrect());
        statement.setInt(9, exercise.getCourseID());

        statement.execute();
    }
}
