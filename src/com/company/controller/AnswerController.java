package com.company.controller;

import com.company.models.AnswerModel;
import com.company.objects.Answer;
import com.company.objects.User;

import java.io.IOException;
import java.sql.SQLException;

public class AnswerController extends AbsController {

    private static AnswerController answerController = null;

    private AnswerController() {
        super();
    }

    public static AnswerController getAnswerController() {

        if (answerController == null) {
            answerController = new AnswerController();
        }
        return answerController;
    }
    public void addAnswer(int questionID, int parameterID) throws IOException, SQLException {
        Answer answer = buildAnswer();
        answer.setQuesID(questionID);
        answer.setParameterID(parameterID);
        AnswerModel.getAnswerModel().addAnswer(answer);
    }

    public void addAnswer(int questionID) throws IOException, SQLException {
        Answer answer = buildAnswer();
        answer.setQuesID(questionID);
        AnswerModel.getAnswerModel().addAnswer(answer);
    }

    private Answer buildAnswer() {
        Answer answer = new Answer();

        System.out.println("-------------------");
        System.out.println(" Answer Creation");
        System.out.println("-------------------");

        System.out.printf("Please Enter Answer Text: ");
        answer.setText(scanner.nextLine());

        System.out.printf("Please Enter Answer Explanation: ");
        answer.setExplanation(scanner.nextLine());

        System.out.printf("Please Enter Answer Correctness: ");
        answer.setCorrect(Integer.parseInt(scanner.nextLine()));

        return answer;
    }

    @Override
    public void landingPage() {

    }

    @Override
    public void setUser(User u) {

    }
}
