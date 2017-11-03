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
        question.topicID = Integer.parseInt(scanner.nextLine());

        System.out.print("Please Enter Difficulty(1-5): ");
        int difficulty = Integer.parseInt(scanner.nextLine());

        if (difficulty < 1 || difficulty > 5) {
            System.out.println("Difficulty is out of range(1-5).");
            return;
        }

        question.difficulty = difficulty;

        System.out.print("Please Enter Question text: ");
        question.text = scanner.nextLine();

        System.out.print("Please Enter Question hint: ");
        question.hint = scanner.nextLine();

        System.out.print("Please Enter Question solution: ");
        question.solution = scanner.nextLine();


        System.out.println("Question topic id: " + question.topicID);
        System.out.println("Question difficulty: " + question.difficulty);
        System.out.println("Question text: " + question.text);
        System.out.println("Question hint: " + question.hint);
        System.out.println("Question solution: " + question.solution);

        try {
            QuestionModel.getQuestionModel().addQuestion(question);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @Override
    public void landingPage() {

    }

    @Override
    public void setUser(User u) {

    }
}
