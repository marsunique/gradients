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
        Scanner scanner = new Scanner(System.in);

        System.out.println(hlines("     Welcome, " + student.firstName + " " + student.lastName));
        System.out.println("      Welcome, " + student.firstName + " " + student.lastName);
        System.out.println(hlines("     Welcome, " + student.firstName + " " + student.lastName));
        System.out.println("Please enter corresponding command #");
        System.out.println("1 View Profile");
        System.out.println("2 View Courses");
        System.out.println("3 Log Out");

        String input = "";
        while (input == ""){
            input = scanner.next();

            switch(input){
                case "1":
                    viewProfile();
                    break;
                case "2":
                    viewCourses();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input.  Try again:");
                    input = "";
            }
        }
    }

    public void viewProfile(){
        Scanner scanner = new Scanner(System.in);

        System.out.println(hlines("       " + student.firstName + " " + student.lastName + " Profile"));
        System.out.println("       " + student.firstName + " " + student.lastName + " Profile");
        System.out.println("       First Name: " + student.firstName);
        System.out.println("       Last  Name: " + student.lastName);
        System.out.println(hlines("       " + student.firstName + " " + student.lastName + " Profile"));
        System.out.println("1 Back");
        System.out.println("2 Log Out");

        String input = "";
        while (input == ""){
            input = scanner.next();

            switch(input){
                case "1":
                    landingPage();
                    break;
                case "2":
                    logOut();
                    break;
                default:
                    System.out.println("Invalid input.  Try again:");
                    input = "";
            }
        }
    }

    public void viewCourses(){
        Scanner scanner = new Scanner(System.in);

        String title = "       " + " Courses For " + student.firstName + " " + student.lastName;
        System.out.println(hlines(title));
        System.out.println(title);
        System.out.println(hlines(title));

        for (int i = 0; i < student.enrolled.size(); i ++){
            System.out.println((i+1) + " " + student.enrolled.get(i));
        }
        System.out.println(student.enrolled.size() + 1 + " Back");
        System.out.println((student.enrolled.size() + 2) + " Log Out");

        String input = "";
        while (input == ""){
            input = scanner.next();

            int result = Integer.valueOf(input.replace(" ", ""));


            if (result == student.enrolled.size() + 1){
                landingPage();
            }
            else if (result == student.enrolled.size() + 2){
                logOut();
            }
            else if (result <= student.enrolled.size()){
                viewClassInfo(student.enrolled.get(result-1));
            }
            else{
                System.out.println("Invalid input.  Try again:");
                input = "";
            }
        }
    }

    public void viewClassInfo(String classId){
        try {

            String className = StudentModel.getStudentModel().getCourseName(classId);

            Scanner scanner = new Scanner(System.in);

            String title = "       " + className + " - " + classId;
            System.out.println(hlines(title));
            System.out.println(title);
            System.out.println(hlines(title));

            System.out.println("1 Current Homeworks");
            System.out.println("2 Past Homeworks");
            System.out.println("3 Back");
            System.out.println("4 Log Out");

            String input = "";
            while (input == ""){
                input = scanner.next();

                switch(input){
                    case "1":
                        viewCurrentExercises(classId);
                        break;
                    case "2":
                        viewPastExercises(classId);
                        landingPage();
                        break;
                    case "3":
                        viewCourses();
                        break;
                    case "4":
                        logOut();
                        break;
                    default:
                        System.out.println("Invalid input.  Try again:");
                        input = "";
                }
            }

        }catch(Exception e){
            System.out.println("Can't get student model stuff - from viewClassInfo() method");
        }

    }

    public void viewCurrentExercises(String classId) {
        try {
            ArrayList<Exercise> exercises = StudentModel.getStudentModel().getAvailableExercises(student.username, classId);


            Scanner scanner = new Scanner(System.in);

            String title = "       Available Exercises for " + classId;
            System.out.println(hlines(title));
            System.out.println(title);
            System.out.println(hlines(title));

            for (int i = 0; i < exercises.size(); i ++){

                System.out.println((i + 1) + " " + exercises.get(i).getName());

            }
            System.out.println((exercises.size() + 1) + " Back");
            System.out.println((exercises.size() + 2) + " Log Out");


            String input = "";
            while (input == "") {
                input = scanner.next();

                int result = Integer.valueOf(input.replace(" ", ""));

                if (result == exercises.size() + 1){
                    viewClassInfo(classId);
                }
                else if (result == exercises.size() + 2){
                    logOut();
                }
                else if (result < exercises.size() + 1){
                    StudentExerciseController.getInstance().setUser(student);
                    StudentExerciseController.getInstance().landingPage(exercises.get(result-1));
                }
                else{
                    System.out.println("Invalid input.  Try again:");
                    input = "";
                }
            }

        }catch(Exception e){
            System.out.println("issue getting current classes for this student, called from getCurrentExercises()");
        }

    }

    public void viewPastExercises(String classId){

        Scanner scanner = new Scanner(System.in);

        String title = "       Previous Exercises for " + classId;
        System.out.println(hlines(title));
        System.out.println(title);
        System.out.println(hlines(title));


        try{
            ArrayList<HashMap<String, String>> attemptsArrayList = StudentModel.getStudentModel().getExerciseAttempt(student.username, classId);

            int count = 0;
            for (HashMap<String, String> hm : attemptsArrayList){
                count ++;
                System.out.println(count + " " + hm.get("name"));
            }
            System.out.println((count + 1) + " Back");
            System.out.println((count + 2) + " Log Out");
            System.out.println();

            String input = "";
            while (input == ""){
                input = scanner.nextLine();

                int result = Integer.valueOf(input.replace(" ", ""));

                if (result == count + 1){
                    viewClassInfo(classId);
                }
                else if (result == count + 2){
                    logOut();
                }
                else if (result <= count){
                    previousExerciseReport(Integer.parseInt(attemptsArrayList.get(Integer.parseInt(input)-1).get("att_id")),
                            Integer.parseInt(attemptsArrayList.get(Integer.parseInt(input)-1).get("ex_id")));
                    System.out.println("todo");
                }
                else{
                    System.out.println("Invalid input.  Try again:");
                    input = "";
                }

            }

        }catch(Exception e){
            System.out.println("No record for this class.");
            viewPastExercises(classId);
        }

    }

    public void previousExerciseReport(int att_id, int ex_id){
        Exercise e = StudentModel.getStudentModel().getExerciseForReport(att_id, ex_id);

        String title = "      Review of " + e.getName() + " " + e.getCourseID();
        System.out.println(hlines(title));
        System.out.println(title);
        System.out.println(hlines(title));
        System.out.println("");

        for (Question q: e.questions){
            System.out.println("  " + q.getText());
            System.out.println("");
            System.out.println("    Student Response: " + q.getStudentAnswer().getText());
            System.out.println("    Correct Response: " + q.getActualAnswer().getText());
            System.out.println("    " + q.getSolution());
            System.out.println("");

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
