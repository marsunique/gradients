package com.company.controller;

import com.company.objects.User;

public class QuestionController extends AbsController{

    private static QuestionController questionController = null;
    private QuestionController() {
        super();
    }

    public static QuestionController getQuestionController() {
        if (questionController == null) {
            questionController = new QuestionController();
        }
        return questionController;
    }

    public void addQuestion() {

    }

    @Override
    public void landingPage() {

    }

    @Override
    public void setUser(User u) {

    }
}
