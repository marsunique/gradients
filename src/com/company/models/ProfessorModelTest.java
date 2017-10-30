package com.company.models;

import java.io.IOException;
import java.sql.SQLException;

public class ProfessorModelTest {
    public static void main(String[] args) throws IOException, SQLException {
        ProfessorModel professorModel = ProfessorModel.getProfessorModel();
        //professorModel.enrollStudent("gyu9", 540);

        try{
            professorModel.enrollStudent("gyu", 540);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
