package com.company.objects;

import java.util.ArrayList;

public class Answer {
    public String text, explanation;
    public int quesID, ansID, paramID;
    public boolean correct;
    public ArrayList<String> paramVals;

    public Answer (){
        paramVals = new ArrayList<>();
    }
}
