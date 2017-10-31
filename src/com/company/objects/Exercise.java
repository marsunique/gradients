package com.company.objects;

import java.sql.Timestamp;

public class Exercise {
    public Exercise(){

    }

    // Required Variables
    private String name, policy;
    private Boolean adaptive;
    private Timestamp start, end;
    private int numRetries, courseID;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    private Double pointsCorrect, pointsIncorrect;

    public void setName(String name){
        this.name = name;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public void setAdaptive(Boolean adaptive) {
        this.adaptive = adaptive;
    }

    public void setStartDate(Timestamp start) {
        this.start = start;
    }

    public void setEndDate(Timestamp end) {
        this.end = end;
    }

    public void setNumRetries(int numRetries) {
        this.numRetries = numRetries;
    }

    public void setPointsCorrect(Double pointsCorrect) {
        this.pointsCorrect = pointsCorrect;
    }

    public void setPointsIncorrect(Double pointsIncorrect) {
        this.pointsIncorrect = pointsIncorrect;
    }

    public String getName() {
        return name;
    }

    public int getNumRetries() {
        return numRetries;
    }

    public Double getPointsIncorrect() {
        return pointsIncorrect;
    }

    public Boolean getAdaptive() {
        return adaptive;
    }

    public Double getPointsCorrect() {
        return pointsCorrect;
    }

    public String getPolicy() {
        return policy;
    }

    public Timestamp getEnd() {
        return end;
    }

    public Timestamp getStart() {
        return start;
    }
}
