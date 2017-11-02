package com.company.controller;

import com.company.models.CourseModel;
import com.company.objects.Course;
import com.company.objects.User;

import java.util.Scanner;

public class TaController implements Controller {
    private static TaController instance = null;
    private User ta = null;
    private Scanner in = new Scanner(System.in);


    // empty constructor
    private TaController() {
    }

    public static TaController getInstance() {
        if (instance == null) {
            instance = new TaController();
        }
        return instance;
    }

    public void landingPage() {
        while (true) {
            System.out.println("----------------------");
            System.out.println("Welcome, " + ta.firstName);
            System.out.println("----------------------");
            System.out.println("1 View Profile");
            System.out.println("2 Enroll/Drop A Student");
            System.out.println("3 View Report");
            System.out.println("4 View Courses");
            System.out.println("5 Log Out");
            System.out.print("Please enter corresponding command #: ");
            String commandNo = in.nextLine();
            switch (commandNo) {
                case "1":
                    viewProfile();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Improper command.");
                    break;
            }

        }
    }

    private void viewProfile() {
        System.out.println();
        System.out.println("Name: " + ta.firstName + " " + ta.lastName);
        System.out.println("ID: " + ta.username);
        for(String cid : ta.tas){
            System.out.println("Course: " + cid);
            Course c = CourseModel.getCourseModel().getCourseByID(cid);
            for(String ex : c.exerciseNames){
                System.out.println(" - " + ex);
            }
        }
        System.out.print("Press Enter to Return.");
        in.nextLine();
    }

    public void setUser(User u) {
        ta = u;
    }
}
