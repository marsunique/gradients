package com.company;
import com.company.util.DBConnector;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        //TableCreator.createAll();
        try {
            com.company.util.TableCreator.createAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ProfExerciseController controller = new ProfExerciseController();
//        if(controller.addExercise()){
//            System.out.println("Added exercise.");
//        } else {
//            System.out.println("Failed to add exercise.");
//        }

//        Student_Courses_Controller scc = new Student_Courses_Controller();
//        scc.displayMenu();
        //Model.firstInit();
        /*
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
        */
    }

    public static void adminLogin(String username, String password){
        if (Model.checkProfessorPassword(username, password)){

            System.out.println("Login successful!");
            professorPage.professorPage();
        }
    }


}
