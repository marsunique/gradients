package com.company;

public class TaController implements Controller {
    private static TaController instance = null;
    // empty constructor
    private TaController() {}
    public static TaController getInstance() {
        if (instance == null) {
            instance = new TaController();
        }
        return instance;
    }
    public void landingPage() {
        System.out.println("----------------------");
        System.out.println("     Welcome, xxx");
        System.out.println("----------------------");
        System.out.println("Please enter corresponding command #");
        System.out.println("1 View Profile");
        System.out.println("2 Enroll/Drop A Student");
        System.out.println("3 View Report");
        System.out.println("4 Log Out");
    }
}
