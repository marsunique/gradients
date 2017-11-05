package com.company.objects;

import java.util.ArrayList;

public class Answer {
    private String text, explanation;
    private int quesID, ansID, parameterID;
    private int correct;
    public ArrayList<String> paramVals;


    public Answer (){ paramVals = new ArrayList<>(); }

    public int getAnsID() {
        return ansID;
    }

    public void setAnsID(int ansID) {
        this.ansID = ansID;
    }

    public int getCorrect() {
        return correct;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuesID() {
        return quesID;
    }

    public void setQuesID(int quesID) {
        this.quesID = quesID;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getParameterID() {
        return parameterID;
    }

    public void setParameterID(int parameterID) {
        this.parameterID = parameterID;
    }

    public String getText() {
        return text;
    }
}
