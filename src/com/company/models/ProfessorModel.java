package com.company.models;

import com.company.util.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfessorModel extends ModelBase{
    private static ProfessorModel professorModel = null;

    private ProfessorModel() throws IOException {
        super();
    }

    public static ProfessorModel getProfessorModel() throws IOException{
        if(professorModel == null) {
            professorModel = new ProfessorModel();
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
            System.out.println("First Name: " + rs.getString("first_name"));
            System.out.println("Last Name: " + rs.getString("last_name"));
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

    public void studentEnrollDrop(String studentID, int courseID) throws SQLException {
        Statement statement = conn.createStatement();
        String query = "DELETE FROM EnrolledIn WHERE student_id = '" + studentID + "' AND " +
                "course_id = " + courseID + ";";
        statement.executeUpdate(query);
    }

    public void viewReport() {

    }

    public void setupTA(String TAId, int courseID) throws SQLException{
        Statement statement = conn.createStatement();

        String query = "INSERT INTO TAFor(ta_id, course_id) VALUES('"+
                TAId + "'," + courseID +")";
        statement.executeUpdate(query);
    }

    public void exerciseViewAdd() {

    }

    public void searchAddQuestionToBank() {

    }

    public void addRemoveQuestionFromExercise() {

    }
}
