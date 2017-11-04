package com.company.controller;

import com.company.objects.User;

public class StudentExerciseController extends Controller {

    private User student = null;
    public void setUser(User u) {
        student = u;
    }

    public String hlines(String middleString){
        int count = middleString.length();
        String hlines = "";
        for (int i = 0; i < count + 10; i ++){
            hlines += "-";
        }
        return hlines;
    }

    public void landingPage(){

    }


}
