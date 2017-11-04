package com.company.objects;

import java.util.ArrayList;


public class Student {
    public String studentID;
    public String firstName, lastName;
    public String courseID;
    public ArrayList<Float> exAttempts;

    public Student(){
        exAttempts = new ArrayList<>();
    }

}
