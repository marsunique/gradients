package com.company.controller;

import com.company.models.CourseModel;
import com.company.models.StudentModel;
import com.company.models.UserModel;
import com.company.objects.Course;
import com.company.objects.Exercise;
import com.company.objects.Student;
import com.company.objects.User;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class ProfessorController implements Controller {
    private static ProfessorController instance = null;
    Scanner scan = new Scanner(System.in);
    User prof = null;

    // empty constructor
    private ProfessorController() {
    }

    public void setUser(User u) {
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
            out.println();
            out.println("----------------------");
            out.println("Welcome, Professor " + prof.lastName);
            out.println("----------------------");
            out.println("Please enter corresponding command #");
            out.println("1 View Profile");
            out.println("2 View/Add Courses");
            out.println("3 Enroll/Drop A Student");
            out.println("4 View Report");
            out.println("5 Setup TA");
            out.println("6 View/Add Exercises");
            out.println("7 Search/Add Questions To Question Bank");
            out.println("8 Add/Remove Questions From Exercises");
            out.println("9 Log Out");
            out.print("Command #: ");
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
                    out.println("Invalid command, try again.");
            }
        }
    }

    private void viewProfile() {
        out.println();
        out.println("Name: " + prof.firstName + " " + prof.lastName);
        out.println("ID: " + prof.username);
        for (String cid : prof.teaches) {
            out.println("Course: " + cid);
            Course c = CourseModel.getCourseModel().getCourseByID(cid);
            for (String ex : c.exerciseNames) {
                out.println(" - " + ex);
            }
        }
        out.print("Press Enter to Return.");
        scan.nextLine();

    }

    private void courseViewAdd() {
        while (true) {
            out.println();
            out.println("Enter one of the following options:");
            out.println("1 Search Course");
            out.println("2 Add Course");
            out.println("3 Return");
            out.print("Command #: ");
            String input = scan.nextLine().toUpperCase();
            switch (input) {
                case "3":
                    return;
                case "2":
                    addCourse();
                    break;
                case "1":
                    viewCourse();
                    break;
                default:
                    out.println("Invalid command, try again.");
                    break;
            }
        }

    }

    private void addCourse() {
        Course c = new Course();
        String input;

        out.print("Enter a course id (Ex. CSC440): ");
        input = scan.nextLine();
        c.id = scan.nextLine();


        out.print("Enter a course name: ");
        out.print("Enter a start date (yyyy-MM-dd): ");
        out.print("Enter an end date (yyyy-MM-dd): ");
        //TAs
        //Students
        //Topics

    }

    private void viewCourse() {
        out.print("Enter a course id: ");
        String input = scan.nextLine();
        if (input.matches("^[a-zA-Z]{3}\\d{3}$")) {
            Course c = CourseModel.getCourseModel().getCourseByID(input);
            if (c != null) {
                User teacher = UserModel.getUser(c.instructor, null);
                String teachName = teacher != null ? teacher.firstName + " " + teacher.lastName : "None";
                out.println("Name: " + c.name);
                out.println("Instructor: " + teachName);
                out.println("Enrolled: " + c.numEnrolled + "/" + c.maxEnrolled);
                out.println("Dates: " + c.start + " to " + c.end);
                if (!c.exerciseNames.isEmpty() && prof.teaches.contains(input)) {
                    out.println("Exercises: ");
                    for (String e : c.exerciseNames) {
                        System.out.println(" - " + e);
                    }
                }
            } else {
                out.println("This course does not exist.");
            }
        } else {
            out.println("That is not a valid course id.");
        }
    }

    private void studentEnrollDrop() {

    }

    private void viewReport() {
        out.println();
        if (prof.teaches.isEmpty()) {
            out.println("You do not TA for any courses.");
        } else {
            for (String cid : prof.teaches) {
                Course c = CourseModel.getCourseModel().getCourseByID(cid);
                List<Student> studentList = StudentModel.getStudentModel().getStudentsByCourse(c);

                out.println("COURSE: " + cid);
                out.printf("|%-15s|%-15s|%-15s|", " id", " first name", " last name");
                for (int eid : c.exerciseIds) {
                    out.printf("%5s|", "EX" + eid);
                }
                out.println();
                out.print("|---------------|---------------|---------------|");
                for (int eid : c.exerciseIds) {
                    out.print("-----|");
                }

                for (Student s : studentList) {
                    out.println();
                    out.printf("|%-15s|%-15s|%-15s|", s.studentID, s.firstName, s.lastName);
                    for (float attempt : s.exAttempts) {
                        out.printf("%5.2f|", attempt);
                    }

                }
                out.println();
                out.print("|---------------|---------------|---------------|");
                for (int eid : c.exerciseIds) {
                    out.print("-----|");
                }
                out.println();
            }
        }

        out.print("Press Enter to Return.");
        scan.nextLine();
    }

    private void setupTA() {

    }

    private void exerciseViewAdd() {

    }

    private void searchAddQuestionToBank() {

    }

    private void addRemoveQuestionFromExercise() {

    }
}
