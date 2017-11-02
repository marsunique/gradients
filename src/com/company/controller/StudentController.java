package com.company.controller;

import com.company.objects.User;

import java.util.Scanner;

public class StudentController implements Controller {
    private static StudentController instance = null;
    private User student = null;

    // empty constructor
    private StudentController() {
    }

    public static StudentController getInstance() {
        if (instance == null) {
            instance = new StudentController();
        }
        return instance;
    }

    public void landingPage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------");
        System.out.println("     Welcome, xxx");
        System.out.println("----------------------");
        System.out.println("Please enter corresponding command #");
        System.out.println("1 View Profile");
        System.out.println("2 View Courses");
        System.out.println("3 Log Out");

        String input = "";
        while (input == ""){
            input = scanner.next();

            switch(input){
                case "1":
                    System.out.println("todo");
                    break;
                case "2":
                    System.out.println("todo");
                    break;
                case "3":
                    System.out.println("todo");
                    break;
                default:
                    System.out.println("Invalid input.  Try again:");
                    input = "";
            }
        }



    }

    public void setUser(User u) {
        student = u;
    }
}
