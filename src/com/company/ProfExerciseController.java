package com.company;

import com.company.util.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Scanner;

public class ProfExerciseController {
    Scanner in;

    public ProfExerciseController() {
        in = new Scanner(System.in);
    }

    public boolean addExercise() throws IOException{
        boolean added = false;

        // Required Variables
        String name, policy;
        Boolean adaptive;
        Timestamp start, end;
        int numRetries;
        Double pointsCorrect, pointsIncorrect;

        // Get Input
        System.out.println("-------------------");
        System.out.println(" Exercise Creation");
        System.out.println("-------------------");
        System.out.print("Enter Exercise Name: ");
        name = in.nextLine();

        System.out.print("Enter Number of Retries: ");
        try {
            numRetries = Integer.parseInt(in.nextLine());
        } catch (Exception e) {
            System.out.println("Number of retries must be an integer.");
            return added;
        }

        System.out.print("Enter Start Date/Time (yyyy-MM-dd HH:mm:ss): ");
        try {
            start = Timestamp.valueOf(in.nextLine());
        } catch (Exception e) {
            System.out.println("Improperly formatted date.");
            return added;
        }

        System.out.print("Enter End Date/Time(yyyy-MM-dd HH:mm:ss): ");
        try {
            end = Timestamp.valueOf(in.nextLine());
        } catch (Exception e) {
            System.out.println("Improperly formatted date.");
            return added;
        }

        System.out.print("Enter Points Per Correct Answer: ");
        try {
            pointsCorrect = Double.parseDouble(in.nextLine());
        } catch (Exception e) {
            System.out.println("Points must be a number.");
            return added;
        }

        System.out.print("Enter Points Per Incorrect Answer: ");
        try {
            pointsIncorrect = Double.parseDouble(in.nextLine());
        } catch (Exception e) {
            System.out.println("Points must be a number.");
            return added;
        }

        System.out.println("Enter a Scoring Policy");
        System.out.println(" - (L)ast");
        System.out.println(" - (H)ighest");
        System.out.println(" - (A)verage");
        System.out.print("Policy: ");
        String choice = in.nextLine();
        switch (choice.toLowerCase()) {
            case "l":
                policy = "last";
                break;
            case "h":
                policy = "highest";
                break;
            case "a":
                policy = "average";
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
                adaptive = false;
                break;
            case "a":
                adaptive = true;
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

        System.out.println("Name: " + name);
        System.out.println("Policy: " + policy);
        System.out.println("Adaptive: " + adaptive);
        System.out.println("Start: " + start);
        System.out.println("End: " + end);
        System.out.println("Retries: " + numRetries);
        System.out.println("Corrent Points: " + pointsCorrect);
        System.out.println("Incorrect Points: " + pointsIncorrect);

        Connection conn = DBConnector.getConnector().getConn();

        added = true;
        return added;
    }

}
