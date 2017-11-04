package com.company;
import com.company.controller.Controller;
import com.company.controller.ProfessorController;
import com.company.controller.StudentController;
import com.company.controller.TaController;
import com.company.models.UserModel;
import com.company.objects.User;
import com.company.util.DBConnector;
import com.company.util.TableCreator;

import java.io.PrintStream;
import java.util.Scanner;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{


        TableCreator.resetDB();

        Scanner scanner = new Scanner(System.in);
        System.out.println("GRADY-ENTS");
        System.out.print("Login ID: ");
//        String username = scanner.next();

        System.out.print("Password: ");
//        String password = scanner.next();

        //Dustin got sick of entering his username/password ever time....
//        User guy = UserModel.getUser(username, password);
//        User guy = UserModel.getUser("mfisher", "mfisher");
        User guy = UserModel.getUser("kogan", "kogan");
//        User guy = UserModel.getUser("jharla", "jharla");

        PrintStream o = System.out;

        if (guy != null) {

            o.println("Login Successful!");

            Controller controller = null;

            switch (guy.type) {
                case GRADUATE:
                    controller = TaController.getInstance();
                    break;
                case STUDENT:
                    controller = StudentController.getInstance();
                    break;
                case INSTRUCTOR:
                    controller = ProfessorController.getInstance();
                    break;
            }

            controller.setUser(guy);
            controller.landingPage();

        } else {
            o.println("Login unsuccessful. Rerun program to try again.");
        }
        DBConnector.closeConnection();

    }

}
