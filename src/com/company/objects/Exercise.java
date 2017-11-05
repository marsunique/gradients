package com.company.objects;

import java.sql.Date;

public class Exercise {
    public Exercise() {

    }

    // Required Variables
    private String name, policy, courseID;
    private Boolean adaptive;
    private Date start, end;
    private int numRetries;
    private int maxDif;
    private int minDif;
    private int topic;
    private int id;
    private int finalScore;
    private float pointsCorrect, pointsIncorrect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public int getMaxDif() {
        return maxDif;
    }

    public void setMaxDif(int maxDif) {
        this.maxDif = maxDif;
    }

    public int getMinDif() {
        return minDif;
    }

    public void setMinDif(int minDif) {
        this.minDif = minDif;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public void setAdaptive(Boolean adaptive) {
        this.adaptive = adaptive;
    }

    public void setStartDate(Date start) {
        this.start = start;
    }

    public void setEndDate(Date end) {
        this.end = end;
    }

    public void setNumRetries(int numRetries) {
        this.numRetries = numRetries;
    }

    public void setPointsCorrect(float pointsCorrect) {
        this.pointsCorrect = pointsCorrect;
    }

    public void setPointsIncorrect(float pointsIncorrect) {
        this.pointsIncorrect = pointsIncorrect;
    }

    public String getName() {
        return name;
    }

    public int getNumRetries() {
        return numRetries;
    }

    public float getPointsIncorrect() {
        return pointsIncorrect;
    }

    public Boolean getAdaptive() {
        return adaptive;
    }

    public float getPointsCorrect() {
        return pointsCorrect;
    }

    public String getPolicy() {
        return policy;
    }

    public Date getEnd() {
        return end;
    }

    public Date getStart() {
        return start;
    }

    public void setFinalScore(int score) {finalScore = score;}

    public int getFinalScore() { return finalScore; }
}
