package com.company;

import com.company.models.ExerciseModel;
import com.company.objects.Exercise;
import com.company.util.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class ProfExerciseController {
    Scanner in;
    ExerciseModel exerciseModel = null;

    public ProfExerciseController() throws IOException {
        in = new Scanner(System.in);
        exerciseModel = ExerciseModel.getExerciseModel();
    }

    public boolean addExercise() throws IOException, SQLException {
        boolean added = false;

        // Required Variables
        /*String name, policy;
        Boolean adaptive;
        Timestamp start, end;
        int numRetries;
        Double pointsCorrect, pointsIncorrect;
        */

        Exercise exercise = new Exercise();

        // Get Input
        System.out.println("-------------------");
        System.out.println(" Exercise Creation");
        System.out.println("-------------------");
        System.out.print("Enter Exercise Name: ");
        exercise.setName(in.nextLine());

        System.out.print("Enter Course ID: ");
        try {
            exercise.setCourseID(Integer.parseInt(in.nextLine()));
        } catch (Exception e) {
            System.out.println("Course ID must be a number.");
            return added;
        }


        System.out.print("Enter Number of Retries: ");
        try {
            exercise.setNumRetries(Integer.parseInt(in.nextLine()));
        } catch (Exception e) {
            System.out.println("Number of retries must be an integer.");
            return added;
        }

        System.out.print("Enter Start Date/Time (yyyy-MM-dd HH:mm:ss): ");
        try {
            exercise.setStartDate(Timestamp.valueOf(in.nextLine()));
        } catch (Exception e) {
            System.out.println("Improperly formatted date.");
            return added;
        }

        System.out.print("Enter End Date/Time(yyyy-MM-dd HH:mm:ss): ");
        try {
            exercise.setEndDate(Timestamp.valueOf(in.nextLine()));
        } catch (Exception e) {
            System.out.println("Improperly formatted date.");
            return added;
        }

        System.out.print("Enter Points Per Correct Answer: ");
        try {
            exercise.setPointsCorrect(Double.parseDouble(in.nextLine()));
        } catch (Exception e) {
            System.out.println("Points must be a number.");
            return added;
        }

        System.out.print("Enter Points Per Incorrect Answer: ");
        try {
            exercise.setPointsIncorrect(Double.parseDouble(in.nextLine()));
        } catch (Exception e) {
            System.out.println("Points must be a number.");
            return added;
        }

        System.out.println("Enter a Scoring Policy");
        System.out.println(" - (L)ast");
        System.out.println(" - (H)ighest");
        System.out.println(" - (A)verage");
        System.out.printf("Policy: ");
        String choice = in.nextLine();
        switch (choice.toLowerCase()) {
            case "l":
                exercise.setPolicy("last");
                break;
            case "h":
                exercise.setPolicy("highest");
                break;
            case "a":
                exercise.setPolicy("average");
                break;
            default:
                System.out.println("Policy must be one of L, H, or A.");
                return added;
        }

        System.out.println("Enter a Exercise Type");
        System.out.println(" - (S)tandard");
        System.out.println(" - (A)daptive");
        System.out.print("Type: ");
        choice = in.nextLine();
        switch (choice.toLowerCase()) {
            case "s":
                exercise.setAdaptive(false);
                break;
            case "a":
                exercise.setAdaptive(true);
                break;
            default:
                System.out.println("Type must be one of S or A.");
                return added;
        }

//        String name, policy;
//        Boolean adaptive;
//        Timestamp start, end;
//        int numRetries;
//        Double pointsCorrect, pointsIncorrect;

        System.out.println("Name: " + exercise.getName());
        System.out.println("Policy: " + exercise.getPolicy());
        System.out.println("Adaptive: " + exercise.getAdaptive());
        System.out.println("Start: " + exercise.getStart());
        System.out.println("End: " + exercise.getEnd());
        System.out.println("Retries: " + exercise.getNumRetries());
        System.out.println("Correct Points: " + exercise.getPointsCorrect());
        System.out.println("Incorrect Points: " + exercise.getPointsIncorrect());

        exerciseModel.createExercies(exercise);

        added = true;
        return added;
    }

    public void deleteExercise() throws SQLException, IOException{
        int exID, courseID;

        System.out.println("-------------------");
        System.out.println(" Exercise Deprecation");
        System.out.println("-------------------");
        System.out.print("Enter Exercise ID: ");
        exID = Integer.parseInt(in.nextLine());
        System.out.print("Enter Course ID: ");
        courseID = Integer.parseInt(in.nextLine());

        exerciseModel.deleteExercise(exID, courseID);
    }

}
