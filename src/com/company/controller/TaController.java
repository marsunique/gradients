package com.company.controller;

import com.company.models.CourseModel;
import com.company.models.ProfessorModel;
import com.company.models.StudentModel;
import com.company.objects.Course;
import com.company.objects.Student;
import com.company.objects.User;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;
import com.sun.org.apache.xpath.internal.SourceTree;
import oracle.jrockit.jfr.events.DataStructureDescriptor;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TaController implements Controller {
    private static TaController instance = null;
    private User ta = null;
    private Scanner scanner = new Scanner(System.in);

    // empty constructor
    private TaController() {
    }

    public static TaController getInstance() {
        if (instance == null) {
            instance = new TaController();
        }
        return instance;
    }

    public String hlines(String middleString){
        int count = middleString.length();
        String hlines = "";
        for (int i = 0; i < count + 10; i ++){
            hlines += "-";
        }
        return hlines;
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
            String commandNo = scanner.nextLine();
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
                    viewCourses();
                    break;
                case "5":
                    logOut();
                default:
                    System.out.println("Improper command. Try again.");
            }
        }
    }

    private void viewProfile() {
        while (true) {
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
            String input = scanner.nextLine();
            if (input.equals("")) break;
        }
    }

    private void studentEnrollDrop() {
        while (true) {
            System.out.println();
            System.out.println("Enter one of the following options:");
            System.out.println("1 Add a Student");
            System.out.println("2 Drop a Student");
            System.out.println("3 Back");
            System.out.print("Command #: ");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                addStudent();
            }
            else if (input.equals("2")) {
                dropStudent();
            }
            else if (input.equals("3")) {
                break;
            }
            else {
                System.out.println("Invalid input.  Try again:");
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter a student id: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter a course id: ");
        String courseID = scanner.nextLine();
        while (true) {
            System.out.println("Add " + studentID + " to " + courseID + "? (YES/NO)");
            String input = scanner.nextLine();
            if (input.toUpperCase().equals("YES")) {
                try {
                    ProfessorModel.getProfessorModel().enrollStudent(studentID, courseID);
                    System.out.println("You have successfully enrolled the student with ID " + studentID + " for the course " + courseID);
                }
                catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
                break;
            }
            else if (input.toUpperCase().equals("NO")) {
                System.out.println("Canceled");
                break;
            }
            else {
                System.out.println("Please enter YES or NO");
            }
        }
    }

    private void dropStudent() {
        System.out.print("Enter a student id: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter a course id: ");
        String courseID = scanner.nextLine();
        while (true) {
            System.out.println("Drop " + studentID + " from " + courseID + "? (YES/NO)");
            String input = scanner.nextLine();
            if (input.toUpperCase().equals("YES")) {
                try{
                    ProfessorModel.getProfessorModel().studentEnrollDrop(studentID, courseID);
                    System.out.println("You have successfully drop the student with ID " + studentID + " from the course " + courseID);
                }
                catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
                break;
            }
            else if (input.toUpperCase().equals("NO")) {
                System.out.println("Canceled");
                break;
            }
            else {
                System.out.println("Please enter YES or NO");
            }
        }
    }

    private void viewReport() {
        while (true) {
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
            String input = scanner.nextLine();
            if (input.equals("")) break;
        }

    }

    private void viewCourses() {
        StudentController.getInstance().setUser(ta);
        StudentController.getInstance().viewCourses();
        landingPage();
//        while (true) {
//            String title = "       " + " Courses For " + ta.firstName + " " + ta.lastName;
//            System.out.println(hlines(title));
//            System.out.println(title);
//            System.out.println(hlines(title));
//
//            for (int i = 0; i < ta.enrolled.size(); i++) {
//                System.out.println((i + 1) + " " + ta.enrolled.get(i));
//            }
//            System.out.println(ta.enrolled.size() + 1 + " Back");
//            System.out.println(ta.enrolled.size() + 2 + " Log Out");
//            System.out.print("Please enter corresponding command #: ");
//
//            String input = scanner.nextLine();
//            int result = Integer.valueOf(input.replace(" ", ""));
//            if (result == ta.enrolled.size() + 1) {
//                return;
//            } else if (result == ta.enrolled.size() + 2) {
//                logOut();
//            } else if (1 <= result && result <= ta.enrolled.size()) {
//                viewClassInfo(ta.enrolled.get(result - 1));
//            } else {
//                System.out.println("Invalid input.  Try again:");
//            }
//        }
    }

    public void viewClassInfo(String classId) {
        while (true) {
            try {
                String className = StudentModel.getStudentModel().getCourseName(classId);

                String title = "       " + className + " - " + classId;
                System.out.println(hlines(title));
                System.out.println(title);
                System.out.println(hlines(title));

                System.out.println("1 Current Homeworks");
                System.out.println("2 Past Homeworks");
                System.out.println("3 Back");
                System.out.println("4 Log Out");
                System.out.print("Please enter corresponding command #: ");

                String input = scanner.nextLine();
                switch (input) {
                    case "1":
//                    viewCurrentExercises(classId);
                        break;
                    case "2":
//                    viewPastExercises(classId);
                        break;
                    case "3":
                        return;
                    case "4":
                        logOut();
                        break;
                    default:
                        System.out.println("Invalid input.  Try again:");
                }
            }
            catch (Exception e) {
                System.out.println("Can't get student model stuff - from viewClassInfo() method");
            }
        }
    }

    public void logOut(){
        System.out.println("Good-bye");
        System.exit(0);
    }
}
