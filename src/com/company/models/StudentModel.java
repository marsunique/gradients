package com.company.models;

import com.company.objects.Course;
import com.company.objects.Exercise;
import com.company.objects.Student;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentModel extends ModelBase {
    private static StudentModel studentModel = null;

    private StudentModel() throws IOException {
        super();
    }

    public static StudentModel getStudentModel() {
        if (studentModel == null) {
            try {
                studentModel = new StudentModel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return studentModel;
    }

    public List<Student> getStudentsByCourse(Course course) {
        ArrayList<Student> ret = new ArrayList<>();
        Statement stmt = null;

        String query = "SELECT * " +
                "FROM enrolledin " +
                "WHERE course_id='" + course.id + "'";

        // Get all enrolled in a course
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Student s = new Student();
                s.courseID = rs.getString("course_id");
                s.studentID = rs.getString("student_id");
                ret.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        for (Student s : ret) {

            // Get name
            query = "SELECT * " +
                    "FROM User " +
                    "WHERE id='" + s.studentID + "';";

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    s.firstName = rs.getString("first_name");
                    s.lastName = rs.getString("last_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

            // For each exercise, find score
            for (int eid : course.exerciseIds) {
                Exercise e = ExerciseModel.getExerciseModel().getExerciseById(eid);
                s.exAttempts.add(ExerciseModel.getExerciseModel().getScore(e, s));
            }

        }

        return ret;
    }
}
