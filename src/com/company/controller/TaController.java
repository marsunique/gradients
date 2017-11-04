package com.company.controller;

import com.company.models.CourseModel;
import com.company.models.StudentModel;
import com.company.objects.Course;
import com.company.objects.Student;
import com.company.objects.User;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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

    public void setUser(User u) {
        ta = u;
    }

    public void landingPage() {
        while (true) {
            System.out.println();
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
                    studentEnrollDrop();
                    break;
                case "3":
                    viewReport();
                    break;
                case "4":
                    viewCourse();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Improper command. Try again.");
                    break;
            }

        }
    }

    private void viewCourse() {

    }

    private void studentEnrollDrop() {

    }

    private void viewReport() {
        System.out.println();
        if (ta.tas.isEmpty()) {
            System.out.println("You are not TA for any courses.");
        } else {
            for (String cid : ta.tas) {
                Course c = CourseModel.getCourseModel().getCourseByID(cid);
                List<Student> studentList = StudentModel.getStudentModel().getStudentsByCourse(c);

                System.out.println("COURSE: " + cid);
                System.out.printf("|%-15s|%-15s|%-15s|", " id", " first name", " last name");
                for (int eid : c.exerciseIds) {
                    System.out.printf("%5s|", "EX" + eid);
                }
                System.out.println();
                System.out.print("|---------------|---------------|---------------|");
                for (int eid : c.exerciseIds) {
                    System.out.print("-----|");
                }

                for (Student s : studentList) {
                    System.out.println();
                    System.out.printf("|%-15s|%-15s|%-15s|", s.studentID, s.firstName, s.lastName);
                    for (float attempt : s.exAttempts) {
                        System.out.printf("%5.2f|", attempt);
                    }

                }
                System.out.println();
                System.out.print("|---------------|---------------|---------------|");
                for (int eid : c.exerciseIds) {
                    System.out.print("-----|");
                }
                System.out.println();
            }
        }

        System.out.print("Press Enter to Return.");
        in.nextLine();
    }

    private void viewProfile() {
        System.out.println();
        System.out.println("Name: " + ta.firstName + " " + ta.lastName);
        System.out.println("ID: " + ta.username);
        for (String cid : ta.tas) {
            System.out.println("Course: " + cid);
            Course c = CourseModel.getCourseModel().getCourseByID(cid);
            for (String ex : c.exerciseNames) {
                System.out.println(" - " + ex);
            }
        }
        System.out.print("Press Enter to Return.");
        in.nextLine();
    }
}
