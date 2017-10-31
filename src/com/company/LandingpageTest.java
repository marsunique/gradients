package com.company;

import com.company.controller.ProfessorController;
import com.company.controller.StudentController;
import com.company.controller.TaController;

public class LandingpageTest {
    public static void main(String[] args) {
        ProfessorController proCon = ProfessorController.getInstance();
        proCon.landingPage();

        TaController taCon = TaController.getInstance();
        taCon.landingPage();

        StudentController stuCon = StudentController.getInstance();
        stuCon.landingPage();
    }
}
