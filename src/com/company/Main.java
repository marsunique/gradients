package com.company;
import com.company.util.DBConnector;

import javax.jws.WebParam;
import java.util.Scanner;


import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            com.company.util.TableCreator.createAll();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Model.firstInit();

        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        System.out.println("GRADY-ENTS");
        System.out.println("Login ID: ");
        String username = scanner.next();

        System.out.println("Password: ");
        String password = scanner.next();

        System.out.println("Checking Password....");

        Boolean loginSuccess = Model.login(username, password);

        if (loginSuccess){
            System.out.println("Login successful!");
        }
        else{
            System.out.println("Login unsuccessful!");
        }

    }

    public static void adminLogin(String username, String password){
        if (Model.checkProfessorPassword(username, password)){

            System.out.println("Login successful!");
            professorPage.professorPage();
        }
    }


}
