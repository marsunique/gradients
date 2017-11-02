package com.company.controller;

public class QuestionController {

    private static QuestionController questionController = null;
    private QuestionController() {

    }

    public static QuestionController getQuestionController() {
        if (questionController == null) {
            questionController = new QuestionController();
        }
        return questionController;
    }

    public void addQuestion() {

    }
}
