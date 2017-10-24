package com.company;
import javax.sound.midi.Soundbank;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) {

        Model.firstInit();
        TableCreator.creatAll();

        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        System.out.println("GRADY-ENTS");
        System.out.println("Login ID: ");
        String username = scanner.next();

        System.out.println("Password: ");
        String password = scanner.next();

        System.out.println("Checking Password....");

        switch (username){
            case "admin":
            case "Admin":
                adminLogin(username, password);
        }
    }

    public static void adminLogin(String username, String password){
        if (Model.checkProfessorPassword(username, password)){

            System.out.println("Login successful!");
            professorPage.professorPage();
        }
    }


}
