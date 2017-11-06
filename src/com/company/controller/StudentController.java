package com.company.controller;

import com.company.models.StudentModel;
import com.company.objects.Exercise;
import com.company.objects.Question;
import com.company.objects.User;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Scanner;

public class StudentController implements Controller {
    private static StudentController instance = null;

    private User student = null;
    private Scanner scanner = new Scanner(System.in);

    // empty constructor
    private StudentController() {
    }

    public static StudentController getInstance() {
        if (instance == null) {
            instance = new StudentController();
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

    public void landingPage() {
        while (true) {

            System.out.println(hlines("     Welcome, " + student.firstName + " " + student.lastName));
            System.out.println("      Welcome, " + student.firstName + " " + student.lastName);
            System.out.println(hlines("     Welcome, " + student.firstName + " " + student.lastName));
            System.out.println("1 View Profile");
            System.out.println("2 View Courses");
            System.out.println("3 Log Out");
            System.out.print("Please enter corresponding command #");

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    viewProfile();
                    break;
                case "2":
                    viewCourses();
                    break;
                case "3":
                    logOut();
                default:
                    System.out.println("Invalid input.  Try again:");
            }
        }
    }

    public void viewProfile(){
        System.out.println(hlines("       " + student.firstName + " " + student.lastName + " Profile"));
        System.out.println("       " + student.firstName + " " + student.lastName + " Profile");
        System.out.println("       First Name: " + student.firstName);
        System.out.println("       Last  Name: " + student.lastName);
        System.out.println(hlines("       " + student.firstName + " " + student.lastName + " Profile"));
        System.out.print("Press Enter to Return.");
        String input = scanner.nextLine();
    }

    public void viewCourses(){
        while (true) {
            String title = "       " + " Courses For " + student.firstName + " " + student.lastName;
            System.out.println(hlines(title));
            System.out.println(title);
            System.out.println(hlines(title));

            for (int i = 0; i < student.enrolled.size(); i++) {
                System.out.println((i + 1) + " " + student.enrolled.get(i));
            }
            System.out.println(student.enrolled.size() + 1 + " Back");
            System.out.println((student.enrolled.size() + 2) + " Log Out");
            System.out.print("Command #: ");

            String input = scanner.nextLine();
            int result = Integer.valueOf(input.replace(" ", ""));

            if (result == student.enrolled.size() + 1) {
                return;
            } else if (result == student.enrolled.size() + 2) {
                logOut();
            } else if (1 <= result && result<= student.enrolled.size()) {
                viewClassInfo(student.enrolled.get(result - 1));
            } else {
                System.out.println("Invalid input.  Try again:");
            }
        }
    }

    public void viewClassInfo(String classId){
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
                System.out.print("Command #: ");
                String input = scanner.nextLine();

                if (input.equals("1")) {
                    viewCurrentExercises(classId);
                } else if (input.equals("2")) {
                    viewPastExercises(classId);
                } else if (input.equals("3")) {
                    break;
                } else if (input.equals("4")) {
                    logOut();
                } else {
                    System.out.println("Invalid input.  Try again:");
                }
            } catch (Exception e) {
                System.out.println("Can't get student model stuff - from viewClassInfo() method");
            }
        }
    }

    public void viewCurrentExercises(String classId) {
        while (true) {
            try {
                ArrayList<Exercise> exercises = StudentModel.getStudentModel().getAvailableExercises(student.username, classId);

                String title = "       Available Exercises for " + classId;
                System.out.println(hlines(title));
                System.out.println(title);
                System.out.println(hlines(title));

                for (int i = 0; i < exercises.size(); i++) {
                    System.out.println((i + 1) + " " + exercises.get(i).getName());
                }
                System.out.println((exercises.size() + 1) + " Back");
                System.out.println((exercises.size() + 2) + " Log Out");
                System.out.print("Command #: ");

                String input = scanner.nextLine();
                int result = Integer.valueOf(input.replace(" ", ""));

                if (result == exercises.size() + 1) {
                    break;
                } else if (result == exercises.size() + 2) {
                    logOut();
                } else if (1 <= result && result <= exercises.size()) {
                    StudentExerciseController.getInstance().setUser(student);
                    StudentExerciseController.getInstance().landingPage(exercises.get(result - 1));
                } else {
                    System.out.println("Invalid input.  Try again:");
                }
            } catch (Exception e) {
                System.out.println("issue getting current classes for this student, called from getCurrentExercises()");
            }
        }
    }

    public void viewPastExercises(String classId){
        while (true) {
            String title = "       Previous Exercises for " + classId;
            System.out.println(hlines(title));
            System.out.println(title);
            System.out.println(hlines(title));

            try {
                ArrayList<HashMap<String, String>> attemptsArrayList = StudentModel.getStudentModel().getExerciseAttempt(student.username, classId);

                int count = 0;
                for (HashMap<String, String> hm : attemptsArrayList) {
                    count++;
                    System.out.println(count + " " + hm.get("name"));
                }
                System.out.println((count + 1) + " Back");
                System.out.println((count + 2) + " Log Out");
                System.out.println();
                System.out.print("Command #: ");

                String input = scanner.nextLine();
                int result = Integer.valueOf(input.replace(" ", ""));

                if (result == count + 1) {
                    break;
                } else if (result == count + 2) {
                    logOut();
                } else if (1 <= result && result <= count) {
                    previousExerciseReport(Integer.parseInt(attemptsArrayList.get(result - 1).get("att_id")),
                            Integer.parseInt(attemptsArrayList.get(result - 1).get("ex_id")));
                } else {
                    System.out.println("Invalid input.  Try again:");
                }
            } catch (Exception e) {
                System.out.println("No record for this exercise.");
                System.out.print("Press Enter to Continue");
                String input = scanner.nextLine();
            }
        }
    }

    private void previousExerciseReport(int att_id, int ex_id) {
        Exercise e = StudentModel.getStudentModel().getExerciseForReport(att_id, ex_id);

        String title = "      Review of " + e.getName() + " " + e.getCourseID();
        System.out.println(hlines(title));
        System.out.println(title);
        System.out.println(hlines(title));
        System.out.println(" Start Date:          " + e.getStart());
        System.out.println(" End Date:            " + e.getEnd());
        System.out.println(" Adaptive:            " + e.getAdaptive());
        System.out.println(" Points Available:    " + (int)(e.questions.size()*e.getPointsCorrect()));
        System.out.println(" Points For Correct:  " + (int)e.getPointsCorrect());
        System.out.println(" Points For Wrong:    " + (int)e.getPointsIncorrect());
        System.out.println(" Student Score:       " + e.getFinalScore());
        System.out.println(" Allowed Attempts:    " + e.getNumRetries());
        System.out.println(" Student Attempts:    " + StudentModel.getStudentModel().getMaxNumberOfTimesAStudentHasTakenAHomework(e.getId(), ta.username));

        System.out.println(hlines(title));

        for (Question q : e.questions) {
            System.out.println("  " + questionTextMaybeParameterized(q));
            System.out.println("");
            System.out.println("    Student Response: " + q.getStudentAnswer().getText());
            System.out.println("    Correct Response: " + q.getActualAnswer().getText());


            if (q.getActualAnswer() == q.getStudentAnswer()){
                System.out.println("    CORRECT!          + " + (int)e.getPointsCorrect() + " points");
            }
            else{
                System.out.println("    INCORRECT.        - " + (int)e.getPointsIncorrect() + " points");
            }
            System.out.println("    Explanation:      " + q.getSolution());
            System.out.println("");
        }
        System.out.print("Press Enter to Continue");
        String input = scanner.nextLine();
    }

    private String questionTextMaybeParameterized(Question q){
        if (q.getParamIndex() == 0){
            return q.getText();
        }
        else{
            String[] temp = q.getText().split("<");

            String prompt = temp[0];
            for (int i = 1; i < temp.length; i ++){
                temp[i] = temp[i].replace(">", "");
                temp[i] = q.paramVals.get(i-1) + " " +  temp[i];
                prompt += temp[i];
            }
            return prompt;
        }
    }


    public void logOut(){
        System.out.println("Good-bye");
        System.exit(0);
    }

    public void setUser(User u) {
        student = u;
    }
}
