package com.company.models;

import java.io.IOException;
import java.sql.SQLException;

public class ProfessorModelTest {
    public static void main(String[] args) throws IOException, SQLException {
        ProfessorModel professorModel = ProfessorModel.getProfessorModel();
        professorModel.studentEnrollDrop("gyu9", 540);
    }
}
