package com.company;
import java.util.Scanner;

public class professorPage {
    public static void professorPage(){
        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome, Professor");
        System.out.println("Options:");
        System.out.println("- Create a new (H)omework");
        System.out.println("- (V)iew homework results");
        System.out.println("- Create a new (C)lass");

        String input = scanner.next();
    }

}
