package com.company.models;

import com.company.util.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfessorModel {
    private static ProfessorModel professorModel = null;
    private static Connection conn =  null;

    private ProfessorModel(){
    }

    public static ProfessorModel getProfessorModel() throws IOException{
        if(professorModel == null) {
            professorModel = new ProfessorModel();
        }

        if (conn == null) {
            conn = DBConnector.getConnector().getConn();
        }

        return professorModel;
    }

    public void viewProfile() throws SQLException {
        Statement statement = conn.createStatement();
        String query = "SELECT * " +
                        "FROM Instructor " +
                        "WHERE inst_id = 'instructor1';";

        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            System.out.println(rs.getString("first_name"));
            System.out.println(rs.getString("last_name"));
        }

    }

    public void courseViewAdd() {

    }

    public void enrollStudent(String studentID, int courseID) throws SQLException {
        Statement statement = conn.createStatement();
        String query = "INSERT INTO EnrolledIn(student_id, course_id) VALUES('"+
                studentID + "'," + courseID +")";
        statement.executeUpdate(query);
    }

    public void studentEnrollDrop() {

    }

    public void viewReport() {

    }

    public void setupTA() {

    }

    public void exerciseViewAdd() {

    }

    public void searchAddQuestionToBank() {

    }

    public void addRemoveQuestionFromExercise() {

    }
}
