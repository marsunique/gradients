package com.company.models;

import com.company.objects.Student;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentModel extends ModelBase {
    StudentModel studentModel = null;
    private StudentModel() throws IOException {
        super();
    }

    public StudentModel getStudentModel() throws IOException {
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
}
