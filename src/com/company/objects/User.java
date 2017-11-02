package com.company.objects;

import java.util.ArrayList;

public class User {

    public enum Type {
        STUDENT, GRADUATE, INSTRUCTOR;
    }


    public Type type;
    public String username;
    public String firstName;
    public String lastName;
    public ArrayList<String> tas;
    public ArrayList<String> teaches;
    public ArrayList<String> enrolled;

    private static User user = null;

    private User(){
        type = null;
        username = null;
        firstName = null;
        lastName = null;
        tas = new ArrayList<>();
        teaches = new ArrayList<>();
        enrolled = new ArrayList<>();
    }

    public static User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

}
