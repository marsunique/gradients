package com.company.objects;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String text;
    private String hint;
    private int difficulty;
    private String solution;
    private int topicID;
    private int exerciseID;
    private int questionID;
    public List<Answer> answers;
    public ArrayList<String> paramVals;
    private Answer studentAnswer;
    private Answer actualAnswer;

    public Question(){
        paramVals = new ArrayList<>();
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setStudentAnswer(Answer a) {this.studentAnswer = a;}

    public Answer getStudentAnswer() {return this.studentAnswer;}

    public void setActualAnswer(Answer a) { this.actualAnswer = a;}

    public Answer getActualAnswer() {return this.actualAnswer;}
}
