package com.company.controller;

public class StudentController implements Controller {
    private static StudentController instance = null;
    // empty constructor
    private StudentController() {}
    public static StudentController getInstance() {
        if (instance == null) {
            instance = new StudentController();
        }
        return instance;
    }
    public void landingPage() {
        System.out.println("----------------------");
        System.out.println("     Welcome, xxx");
        System.out.println("----------------------");
        System.out.println("Please enter corresponding command #");
        System.out.println("1 View Profile");
        System.out.println("2 View Courses");
        System.out.println("3 Log Out");
    }
}
