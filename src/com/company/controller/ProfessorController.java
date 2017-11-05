package com.company.controller;

import com.company.models.*;
import com.company.objects.*;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.text.DateFormat;
import java.util.stream.Collectors;


import static java.lang.System.out;

public class ProfessorController implements Controller {
    private static ProfessorController instance = null;
    private Scanner scan = new Scanner(System.in);
    private User prof = null;

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
                    logOut();
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
            out.println("3 Back");
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
        String startDate, endDate;
        Date utilstartDate = new Date();
        Date utilendDate = new Date();


        out.print("Enter a course id (Ex. CSC440): ");
        c.id = scan.nextLine();


        out.print("Enter a course name: ");
        c.name = scan.nextLine();

        out.print("Enter a start date (yyyy-MM-dd): ");
        startDate = scan.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            utilstartDate = (Date) formatter.parse(startDate);
            c.start = new java.sql.Date(utilstartDate.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        out.print("Enter an end date (yyyy-MM-dd): ");
        endDate = scan.nextLine();
        try {
            utilendDate = (Date) formatter.parse(endDate);
            c.end = new java.sql.Date(utilendDate.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        out.print("Enter the instructor id: ");
        c.instructor = scan.nextLine();

        out.print("Enter 1 if it is a graduate course, else enter 0: ");
        input = scan.nextInt();
        c.graduate = input != 0;

        out.print("Enter the maximum no. of students that can enroll: ");
        c.maxEnrolled = scan.nextInt();

        try {
            ProfessorModel.getProfessorModel().courseAdd(c.id, c.name, c.start, c.end, c.instructor, input, c.maxEnrolled);
            out.println("You have successfully created a new course");
            if (c.instructor.equals(prof.username)) {
                prof.teaches.add(c.id);
            }
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
            } else {
                out.println("This course does not exist.");
            }
        } else {
            out.println("That is not a valid course id.");
        }
    }

    private void studentEnrollDrop() {
        while (true) {
            out.println();
            out.println("Enter one of the following options:");
            out.println("1 Add a Student");
            out.println("2 Drop a Student");
            out.println("3 Back");
            out.print("Command #: ");
            String input = scan.nextLine().toUpperCase();
            if (input.equals("1")) {
                addStudent();
            } else if (input.equals("2")) {
                dropStudent();
            } else if (input.equals("3")) {
                break;
            } else {
                out.println("Invalid input.  Try again:");
            }
        }
    }

    private void addStudent() {
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
            } else if (input.toUpperCase().equals("NO")) {
                out.println("Canceled");
                break;
            } else {
                out.println("Please enter YES or NO");
            }
        }
    }

    private void dropStudent() {
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
            } else if (input.toUpperCase().equals("NO")) {
                System.out.println("Canceled");
                break;
            } else {
                System.out.println("Please enter YES or NO");
            }
        }
    }

    private void viewReport() {

        //Remove Duplicate courses
        prof.teaches = prof.teaches.stream().distinct().collect(Collectors.toList());
        out.println();
        if (prof.teaches.isEmpty()) {
            out.println("You do not teach any courses.");
        } else {
            for (String cid : prof.teaches) {
                Course c = CourseModel.getCourseModel().getCourseByID(cid);
                List<Student> studentList = StudentModel.getStudentModel().getStudentsByCourse(c);

                out.println("COURSE: " + cid);
                out.printf("|%-15s|%-15s|%-15s|", " id", " first name", " last name");
                for (String ename : c.exerciseNames) {
                    out.printf("%15s|", ename + " ");
                }
                out.println();
                out.print("|---------------|---------------|---------------|");
                for (int eid : c.exerciseIds) {
                    out.print("---------------|");
                }

                for (Student s : studentList) {
                    out.println();
                    out.printf("|%-15s|%-15s|%-15s|", s.studentID, s.firstName, s.lastName);
                    for (float attempt : s.exAttempts) {
                        out.printf("%15.2f|", attempt);
                    }

                }
                out.println();
                out.print("|---------------|---------------|---------------|");
                for (int eid : c.exerciseIds) {
                    out.print("---------------|");
                }
                out.println();
            }
        }

        out.print("Press Enter to Return.");
        String input = scan.nextLine();
    }

    private void setupTA() {
        Student s = new Student();
        out.print("Enter a student id: ");
        s.studentID = scan.nextLine();
        out.print("Enter a course id for which the student would be TA for: ");
        s.courseID = scan.nextLine();

        try {
            ProfessorModel.getProfessorModel().setupTA(s.studentID, s.courseID);
            System.out.println("You have successfully added " + s.studentID + " as TA for " + s.courseID);
            return;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void exerciseViewAdd() {
        while (true) {
            out.println();
            out.println("Enter one of the following options:");
            out.println("1 View Exercise");
            out.println("2 Add Exercise");
            out.println("3 Back");
            out.print("Command #: ");
            String input = scan.nextLine();
            switch (input) {
                case "1":
                    viewExercise();
                    break;
                case "2":
                    addExercise();
//                    try {
//                        ProfExerciseController.getProfessorController().addExercise();
//                    }
//                    catch (Exception e) {
//                        out.println("ERROR: " + e.getMessage());
//                    }
                    break;
                case "3":
                    return;
                default:
                    out.println("Invalid command, try again.");
                    break;
            }
        }
    }

    private void viewExercise() {
        out.print("Enter a exercise id: ");
        String input = scan.nextLine();
        if (input.matches("\\d+")) {
            int e_id = Integer.valueOf(input);
            Exercise e = ExerciseModel.getExerciseModel().getExerciseById(e_id);
            if (e != null) {
                try {
                    out.println();
                    out.println("-----------------------");
                    out.println("Detail of Exercise " + e_id);
                    out.println("-----------------------");
                    out.println("Name: " + e.getName());
                    out.println("Start Date: " + e.getStart());
                    out.println("End Date: " + e.getEnd());
                    out.println("Number of Attempts: " + e.getNumRetries());
                    out.println("Course ID: " + e.getCourseID());
                    out.println("Topic: " + TopicModel.getTopicModel().getTopicByID(e.getTopic()));
                    out.println("Adaptive: " + e.getAdaptive());
                    out.println("Right Points: " + e.getPointsCorrect());
                    out.println("Wrong Points: " + e.getPointsIncorrect());
                    out.println("Scoring Policy: " + e.getPolicy());
                    out.println("Min Dif: " + e.getMinDif());
                    out.println("Max Dif: " + e.getMaxDif());
                    out.print("Press Enter to Return");
                    input = scan.nextLine();
                } catch (Exception er) {
                    out.println("ERROR: " + er.getMessage());
                }
            } else {
                out.println("This exercise does not exist.");
            }
        } else {
            out.println("Invalid exercise id, id should only contain numbers");
        }
    }

    private void addExercise() {
        Exercise e = new Exercise();
        out.println("Please provide information of the new exercise");

        out.print("Name (Ex. Homework 1): ");
        e.setName(scan.nextLine());

        out.print("Start Date (yyyy-MM-dd): ");
        String startdate = scan.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date utilstartDate = (Date) formatter.parse(startdate);
            e.setStartDate(new java.sql.Date(utilstartDate.getTime()));
        } catch (ParseException er) {
            er.printStackTrace();
        }

        out.print("End Date (yyyy-MM-dd): ");
        String enddate = scan.nextLine();
        try {
            Date utilendDate = (Date) formatter.parse(enddate);
            e.setEndDate(new java.sql.Date(utilendDate.getTime()));
        } catch (ParseException er) {
            er.printStackTrace();
        }

        out.print("Number of Attempts: ");
        e.setNumRetries(Integer.parseInt(scan.nextLine()));

        out.print("Course ID: ");
        e.setCourseID(scan.nextLine());

        out.print("Topic (topic id): ");
        e.setTopic(Integer.parseInt(scan.nextLine()));

        out.print("Adaptive (YES/NO): ");
        String input = scan.nextLine().toUpperCase();
        if (input.equals("YES")) {
            e.setAdaptive(true);
        } else if (input.equals("NO")) {
            e.setAdaptive(false);
        } else {
            out.println("Invalid input, set to NO by default");
            e.setAdaptive(false);
        }

        out.print("Right Points: ");
        e.setPointsCorrect(Float.parseFloat(scan.nextLine()));

        out.print("Wrong Points: ");
        e.setPointsIncorrect(Float.parseFloat(scan.nextLine()));

        out.print("Scoring Policy (last/average/highest): ");
        input = scan.nextLine().toUpperCase();
        if (input.equals("LAST")) {
            e.setPolicy("last");
        } else if (input.equals("AVERAGE")) {
            e.setPolicy("average");
        } else if (input.equals("HIGHEST")) {
            e.setPolicy("highest");
        } else {
            out.println("Invalid policy, set to highest by default");
            e.setPolicy("highest");
        }

        out.print("Min Dif: ");
        e.setMinDif(Integer.parseInt(scan.nextLine()));

        out.print("Max Dif: ");
        e.setMaxDif(Integer.parseInt(scan.nextLine()));

        try {
            ExerciseModel.getExerciseModel().createExercies(e);
            out.println("Exercise has been successfully added");
        } catch (Exception er) {
            out.println("ERROR: " + er.getMessage());
        }
    }

    private void searchAddQuestionToBank() {
        while (true) {
            out.println();
            out.println("Enter one of the following options:");
            out.println("1 Search Question By Question ID");
            out.println("2 Search Question By Topic");
            out.println("3 Add Question");
            out.println("4 Back");
            out.print("Command #: ");
            String input = scan.nextLine();
            switch (input) {
                case "1":
                    searchQuestionByID();
                    break;
                case "2":
                    searchQuestionByTopic();
                    break;
                case "3":
                    addQuestion();
                    break;
                case "4":
                    return;
                default:
                    out.println("Invalid command, try again.");
                    break;
            }
        }
    }

    private void searchQuestionByID() {
        out.print("\nPlease Enter Question ID: ");
        try {
            String input = scan.nextLine();
            int id = Integer.parseInt(input);
            Question question = QuestionModel.getQuestionModel().getQuestionByID(id);
            out.println("Question ID : " + question.getQuestionID());
            out.println("Text: " + question.getDifficulty());
            out.print("Press Enter to Return");
            input = scan.nextLine();
        } catch (Exception e) {
            out.println("ERROR: " + e.getMessage());
        }
    }

    private void searchQuestionByTopic() {
        out.println("--------------------");
        out.println("   Current topics");
        out.println("--------------------");
        try {
            ArrayList<String> topics = TopicModel.getTopicModel().getAllTopics();
            for (String topic : topics) {
                out.println(topic);
            }
            out.print("Choose Topic id: ");
            String input = scan.nextLine();
            int id = Integer.parseInt(input);
            List<Question> questions = QuestionController.getQuestionController().getQuestionsFromTopic(id);
            out.println("\nQuestion that has topic -- " + TopicModel.getTopicModel().getTopicByID(id));
            out.println("---------------------------------");
            for (Question question : questions) {
                out.println("Question ID : " + question.getQuestionID());
                out.println("Text: " + question.getDifficulty());
                out.println("---------------------------------");
            }
            out.print("Press Enter to Return");
            input = scan.nextLine();
        } catch (Exception e) {
            out.println("ERROR: " + e.getMessage());
        }

    }

    private void addQuestion() {
        try {
            out.print("\nPlease Enter The Course You Want to Add The Question To: ");
            String input = scan.nextLine();
            QuestionController.getQuestionController().createQuestion(input);
        } catch (Exception e) {
            out.println("ERROR: " + e.getMessage());
        }
    }

    private void addRemoveQuestionFromExercise() {

    }

    private void logOut() {
        System.out.println("Good-bye");
    }
}
