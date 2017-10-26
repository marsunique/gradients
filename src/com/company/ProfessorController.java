package com.company;

import java.util.Scanner;

public class ProfessorController {
    private static ProfessorController instance = null;
    Scanner scan = new Scanner(System.in);
    // empty constructor
    private ProfessorController() {}
    public static ProfessorController getInstance() {
        if (instance == null) {
            instance = new ProfessorController();
        }
        return instance;
    }
    public void landingPage() {
        System.out.println("----------------------");
        System.out.println("Welcome, Professor xxx");
        System.out.println("----------------------");
        System.out.println("Please enter corresponding command #");
        System.out.println("1 View Profile");
        System.out.println("2 View/Add Courses");
        System.out.println("3 Enroll/Drop A Student");
        System.out.println("4 View Report");
        System.out.println("5 Setup TA");
        System.out.println("6 View/Add Exercises");
        System.out.println("7 Search/Add Questions To Question Bank");
        System.out.println("8 Add/Remove Questions From Exercises");
        System.out.println("9 Log Out");
        System.out.println("Please enter corresponding command #");
        String commandNo = scan.nextLine();

    }
}
