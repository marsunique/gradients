package com.company;

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
