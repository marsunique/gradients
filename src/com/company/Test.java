package com.company;

import com.company.models.UserModel;
import com.company.objects.User;
import com.company.util.ScriptRunner;
import com.company.util.TableCreator;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {

        TableCreator.resetDB();

        Scanner scanner = new Scanner(System.in);
        System.out.println("GRADY-ENTS");
        System.out.print("Login ID: ");
        String username = scanner.next();

        System.out.print("Password: ");
        String password = scanner.next();

        User guy = UserModel.getUser(username, password);

        PrintStream o = System.out;

        assert guy != null;

        o.println("Name: " + guy.firstName + " " + guy.lastName);
        o.println("Role: " + guy.type.toString());
        o.println("Teaches: " + guy.teaches.toString());
        o.println("TAs: " + guy.tas.toString());
        o.println("Enrolled: " + guy.enrolled.toString());

        Controller controller = null;

        switch (guy.type){
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

        controller.landingPage();
    }
}
