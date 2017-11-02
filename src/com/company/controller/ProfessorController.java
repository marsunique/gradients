package com.company.controller;

import com.company.objects.User;

import java.util.Scanner;

public class ProfessorController implements Controller {
    private static ProfessorController instance = null;
    Scanner scan = new Scanner(System.in);
    User prof = null;

    // empty constructor
    private ProfessorController() {
    }

    public void setUser(User u){
        prof = u;
    }

    public static ProfessorController getInstance() {
        if (instance == null) {
            instance = new ProfessorController();
        }
        return instance;
    }

    public void landingPage() {
        while (true) {
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
            System.out.print("Please enter corresponding command #: ");
            String commandNo = scan.nextLine();
            switch (commandNo) {
                case "1":
                    viewProfile();
                    break;
                case "2":
                    courseViewAdd();
                    break;
                case "3":
                    studentEnrollDrop();
                    break;
                case "4":
                    viewReport();
                    break;
                case "5":
                    setupTA();
                    break;
                case "6":
                    exerciseViewAdd();
                    break;
                case "7":
                    searchAddQuestionToBank();
                    break;
                case "8":
                    addRemoveQuestionFromExercise();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Invalid Command, Try Again");
            }
        }
    }

    public void viewProfile() {

    }

    public void courseViewAdd() {
        while (true) {
            System.out.println("Enter one of the following options:");
            System.out.println(" - The ID of An Existing Course");
            System.out.println(" - 'N' to Add a New Course");
            System.out.println(" - 'Q' to Return");
            String input = scan.nextLine().toUpperCase();
            switch (input) {
                case "Q":
                    return;
                case "N":
                    break;
                default:
                    if (input.matches("^[a-zA-Z]{3}\\d{3}$")) {


                        if(prof.teaches.contains(input)){
                            System.out.println("That course is called " + input);
                        } else {
                            System.out.println("This course does not exist.");
                        }
                    } else {
                        System.out.println("That is not a valid course title.");
                    }
                    break;
            }
        }

    }

    public void studentEnrollDrop() {

    }

    public void viewReport() {

    }

    public void setupTA() {

    }

    public void exerciseViewAdd() {

    }

    public void searchAddQuestionToBank() {

    }

    public void addRemoveQuestionFromExercise() {

    }

    public void logOut() {

    }
}
