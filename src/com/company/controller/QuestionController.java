package com.company.controller;

import com.company.objects.Question;
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
        System.out.println("-------------------");
        System.out.println(" Question Creation");
        System.out.println("-------------------");
        System.out.print("Enter Question : ");
        Question question = new Question();


    }

    @Override
    public void landingPage() {

    }

    @Override
    public void setUser(User u) {

    }
}
