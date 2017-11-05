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
import java.util.HashMap;


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

    public String getCourseName(String courseID) throws SQLException{

        Statement statement = conn.createStatement();
        String query = "SELECT course_name " +
                "FROM Course " +
                "WHERE course_id = '" + courseID+ "';";

        ResultSet rs = statement.executeQuery(query);

        while (rs.next()){
            return rs.getString("course_name");
        }

        return "";
    }

    public ArrayList<Exercise> getAvailableExercises(String studentId, String classId)throws SQLException{
        Statement statement = conn.createStatement();
        String query = "SELECT e.name, e.ex_id " +
                "FROM Gradients.Exercise AS e " +
                "WHERE e.ex_id NOT IN (SELECT ex_id " +
                "FROM Gradients.Attempt AS a " +
                "WHERE a.student_id = '" + studentId + "' " +
                "GROUP BY a.ex_id " +
                "HAVING COUNT(*) >= e.num_attempts) " +
                    "AND e.course_id = '" + classId + "' " +
                    "AND e.end_date > CURDATE() " +
                    "AND e.start_date < CURDATE() " +
                    "ORDER BY e.name ASC;";

        ResultSet rs = statement.executeQuery(query);

        ArrayList<Integer> id_numbers = new ArrayList<>();
        ArrayList<Exercise> exercises = new ArrayList<>();
        while (rs.next()){
            id_numbers.add(Integer.valueOf(rs.getString("ex_id")));
        }
        for (Integer i : id_numbers){
            exercises.add(ExerciseModel.getExerciseModel().getExerciseById(i));
        }

        return exercises;
    }

    public ArrayList<HashMap<String, String>> getExerciseAttempt(String studentId, String classId){

        try{
            Statement statement = conn.createStatement();
            String query = "SELECT a.att_id, a.score, a.ex_id, e.name " +
                    "FROM Attempt AS a " +
                    "INNER JOIN Exercise AS e ON a.ex_id = e.ex_id " +
                    "WHERE a.student_id = '" + studentId + "' " +
                    "AND a.course_id = '" + classId + "' ";

            ResultSet rs = statement.executeQuery(query);

            ArrayList<HashMap<String, String>> toReturn = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> previousExercisesHashMap;
            while (rs.next()){
                previousExercisesHashMap = new HashMap<>();
                previousExercisesHashMap.put("score", rs.getString("score"));
                previousExercisesHashMap.put("ex_id", rs.getString("ex_id"));
                previousExercisesHashMap.put("name", rs.getString("name"));
                previousExercisesHashMap.put("att_id", rs.getString("att_id"));

                toReturn.add(previousExercisesHashMap);
            }
            return toReturn;
        }catch(Exception e){
            System.out.println("Error in getExerciseAttempt() method in StudentModel.java");
        }



        return null;






    }
}
