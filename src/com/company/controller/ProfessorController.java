package com.company.controller;

import com.company.models.CourseModel;
import com.company.models.StudentModel;
import com.company.models.UserModel;
import com.company.models.ProfessorModel;
import com.company.objects.Course;
import com.company.objects.Exercise;
import com.company.objects.Student;
import com.company.objects.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.text.DateFormat;


import static java.lang.System.in;
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
        while (true) {
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
            String input = scan.nextLine();
            if (input.equals("")) break;
        }
    }

    private void courseViewAdd() {
        while (true) {
            out.println();
            out.println("Enter one of the following options:");
            out.println("1 View Course");
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
        int input;
        String startDate,endDate;
        Date utilstartDate = new Date();
        Date utilendDate = new Date();


        out.print("Enter a course id (Ex. CSC440): ");
        c.id = scan.nextLine();


        out.print("Enter a course name: ");
        c.name=scan.nextLine();

        out.print("Enter a start date (yyyy-MM-dd): ");
        startDate=scan.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            utilstartDate = (Date)formatter.parse(startDate);
            c.start = new java.sql.Date(utilstartDate.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        out.print("Enter an end date (yyyy-MM-dd): ");
        endDate=scan.nextLine();
        try {
            utilendDate = (Date)formatter.parse(endDate);
            c.end = new java.sql.Date(utilendDate.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        out.println("Enter the instructor id:");
        c.instructor=scan.nextLine();

        out.println("Enter whether this is a graduate course or not :");
        out.println("Enter 1 if it is a graduate course, else enter if its 0");
        input = scan.nextInt();
        if(input==0)
            c.graduate=false;
        else
            c.graduate=true;

        out.println("Enter the maximum no. of students that can enroll:");
        c.maxEnrolled=scan.nextInt();

        try {
            ProfessorModel.getProfessorModel().courseAdd(c.id,c.name,c.start,c.end,c.instructor,input,c.maxEnrolled);
            out.println("You have successfully created a new course");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

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
            }
            else {
                out.println("This course does not exist.");
            }
        }
        else {
            out.println("That is not a valid course id.");
        }
    }

    private void studentEnrollDrop() {
        while (true) {
            out.println();
            out.println("Enter one of the following options:");
            out.println("1 Add a Student");
            out.println("2 Drop a Student");
            out.println("3 Return");
            out.print("Command #: ");
            String input = scan.nextLine().toUpperCase();
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
                out.println("Invalid input.  Try again:");
            }
        }
    }

    private void addStudent()  {
        Student s = new Student();
        out.print("Enter a student id: ");
        s.studentID = scan.nextLine();
        out.print("Enter a course id: ");
        s.courseID = scan.nextLine();
        while (true) {
            out.println("Add " + s.studentID + " to " + s.courseID + "? (YES/NO)");
            String input = scan.nextLine();
            if (input.toUpperCase().equals("YES")) {
                try {
                    ProfessorModel.getProfessorModel().enrollStudent(s.studentID, s.courseID);
                    out.println("You have successfully enrolled the student with ID " + s.studentID + " for the course " + s.courseID);
                } catch (Exception e) {
                    out.println("ERROR: " + e.getMessage());
                }
                break;
            }
            else if (input.toUpperCase().equals("NO")) {
                out.println("Canceled");
                break;
            }
            else {
                out.println("Please enter YES or NO");
            }
        }
    }

    private void dropStudent(){
        Student s = new Student();
        out.print("Enter a student id: ");
        s.studentID = scan.nextLine();
        out.print("Enter a course id: ");
        s.courseID = scan.nextLine();
        while (true) {
            out.println("Drop " + s.studentID + " from " + s.courseID + "? (YES/NO)");
            String input = scan.nextLine();
            if (input.toUpperCase().equals("YES")) {
                try {
                    ProfessorModel.getProfessorModel().studentEnrollDrop(s.studentID, s.courseID);
                    out.println("You have successfully dropped the course " + s.courseID + " for student with ID " + s.studentID);
                } catch (Exception e) {
                    out.println("ERROR: " + e.getMessage());
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
            String input = scan.nextLine();
            if (input.equals("")) break;
        }
    }

    private void setupTA() {
        Student s = new Student();
        out.print("Enter a student id: ");
        s.studentID = scan.nextLine();
        out.print("Enter a course id for which the student would be TA for: ");
        s.courseID = scan.nextLine();

        try {
            ProfessorModel.getProfessorModel().setupTA(s.studentID,s.courseID);
            System.out.println("You have successfully added "+ s.studentID+ " as TA for "+s.courseID );
            return;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void exerciseViewAdd() {

    }

    private void searchAddQuestionToBank() {

    }

    private void addRemoveQuestionFromExercise() {

    }
}
