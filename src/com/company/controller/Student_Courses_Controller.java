package com.company.controller;

import java.util.Scanner;

public class Student_Courses_Controller {
    public void displayMenu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome, <STUDENT_NAME_HERE>");
        System.out.println("Your Courses:");
        String[] tempCourses = {"Reading", "Writing", "Arithmetic"};

        for (int i = 0; i < tempCourses.length; i ++){
            System.out.println( (i+1) + ":  " + tempCourses[i]);
        }

        String response = scanner.next();

        Student_Selected_Course_Controller SSCC = new Student_Selected_Course_Controller();

        SSCC.displayMenuWithName(tempCourses[Integer.valueOf(response)-1]);


    }
}
