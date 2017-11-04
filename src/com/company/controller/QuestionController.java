package com.company.controller;

import com.company.models.QuestionModel;
import com.company.models.TopicModel;
import com.company.objects.Question;
import com.company.objects.User;

import java.sql.ResultSet;

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
        Question question = new Question();
        System.out.println("-------------------");
        System.out.println(" Question Creation");
        System.out.println("-------------------");

        System.out.println("Below is the topics list");

        try {
            ResultSet topics = TopicModel.getTopicModel().listTopics();

            while (topics.next()) {
                System.out.println("Topic ID: " + topics.getInt("topic_id")
                        + " Topic Name: " + topics.getString("name"));
            }

        }catch (Exception e) {
            System.out.println("ERROR:" + e.getMessage());
        }
        System.out.print("Please Enter Question Topic ID: ");
        question.setTopicID(Integer.parseInt(scanner.nextLine()));

        System.out.print("Please Enter Difficulty(1-5): ");
        int difficulty = Integer.parseInt(scanner.nextLine());

        if (difficulty < 1 || difficulty > 5) {
            System.out.println("Difficulty is out of range(1-5).");
            return;
        }

        question.setDifficulty(difficulty);

        System.out.print("Please Enter Question text: ");
        question.setText(scanner.nextLine());

        System.out.print("Please Enter Question hint: ");
        question.setText(scanner.nextLine());

        System.out.print("Please Enter Question solution: ");
        question.setSolution(scanner.nextLine());


        System.out.println("Question topic id: " + question.getTopicID());
        System.out.println("Question difficulty: " + question.getDifficulty());
        System.out.println("Question text: " + question.getText());
        System.out.println("Question hint: " + question.getHint());
        System.out.println("Question solution: " + question.getSolution());

        int questionID = 0;
        try {
            questionID = QuestionModel.getQuestionModel().addQuestion(question);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.print("Does this question has parameters? (Y/N): ");
        String hasParams = scanner.nextLine().toLowerCase();

        AnswerController answerController = AnswerController.getAnswerController();

        if(hasParams.equals("y")) {
            //TODO: add parameters
            ParametersController parametersController = ParametersController.getParametersController();
            while(true) {
                int parameterID = parametersController.addParameters(questionID);
                while (true) {
                    try {
                        answerController.addAnswer(questionID, parameterID);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    System.out.printf("Do you want to add more answers for the parameters?(Y/N) : ");
                    String moreAns = scanner.nextLine().toLowerCase();
                    if (moreAns.equals("n")) {
                        break;
                    }
                }
                System.out.printf("Do you want to add more parameters? (Y/N) : ");
                String moreParams = scanner.nextLine().toLowerCase();
                if (moreParams.equals("n")) {
                    break;
                }
            }
        } else {
            //TODO: need to check whether the input is correct, this case add answer with no parameters
            while (true) {
                try {
                    answerController.addAnswer(questionID);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.out.printf("Do you want to add more Answer? (Y/N) : ");
                String moreAnswer = scanner.nextLine().toLowerCase();
                if (moreAnswer.equals("n")) {
                    break;
                }
            }
        }
    }

    @Override
    public void landingPage() {

    }

    @Override
    public void setUser(User u) {

    }
}
