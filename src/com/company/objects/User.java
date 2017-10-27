package com.company.objects;

import com.company.UserType;

import java.util.ArrayList;

public class User {
    public UserType type;
    public String username;
    public String firstName;
    public String lastName;
    public ArrayList<String> tas;
    public ArrayList<String> teaches;
    public ArrayList<String> enrolled;

    public User(){
        type = null;
        username = null;
        firstName = null;
        lastName = null;
        tas = new ArrayList<>();
        teaches = new ArrayList<>();
        enrolled = new ArrayList<>();
    }

}
