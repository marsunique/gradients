package com.company;

import java.util.Scanner;

public class ProfessorController implements Controller {
    private static ProfessorController instance = null;
    Scanner scan = new Scanner(System.in);

    // empty constructor
    private ProfessorController() {
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
            System.out.println("Please enter corresponding command #");
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
