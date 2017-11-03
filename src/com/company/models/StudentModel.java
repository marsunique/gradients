package com.company.models;

import com.company.objects.Student;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ArrayList;

public class StudentModel extends ModelBase {
    private static StudentModel studentModel = null;
    private StudentModel() throws IOException {
        super();
    }

    public static StudentModel getStudentModel() throws IOException {
        if(studentModel == null) {
            studentModel = new StudentModel();
        }

        return studentModel;
    }

    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO Student(student_id, first_name, last_name) VALUES (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setString(1, student.getStudentID());
        statement.setString(2, student.getFirstName());
        statement.setString(3, student.getLastName());

        statement.execute();
    }

    public void deleteStudent(String studentID) throws SQLException {
        String query = "DELETE FROM Student WHERE student_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setString(1, studentID);

        statement.execute();
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

    public HashMap<String, Integer> getAvailableExercises(String studentId, String classId)throws SQLException{
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

        HashMap<String, Integer> availableExercisesDictionary = new HashMap<String, Integer>();
        while (rs.next()){
            availableExercisesDictionary.put(rs.getString("name"), Integer.valueOf(rs.getString("ex_id")));
        }
        return availableExercisesDictionary;
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
