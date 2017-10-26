package com.company;

import java.util.Scanner;

public class Student_Selected_Course_Controller {
    public void displayMenuWithName(String courseName){
        Scanner scanner = new Scanner(System.in);
        System.out.println(courseName);

        System.out.println("1. Current open homeworks");
        System.out.println("2. Past homeworks");

        String response = scanner.next();

    }
}
