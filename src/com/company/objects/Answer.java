package com.company.objects;

public class Answer {
    private String text;
    private int quesID;
    private String explanation;
    private int parameterID;
    private int correct;

    public int getCorrect() {
        return correct;
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

    public void setText(String text) {
        this.text = text;
    }

    public int getQuesID() {
        return quesID;
    }

    public void setQuesID(int quesID) {
        this.quesID = quesID;
    }
}
