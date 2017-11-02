package com.company.controller;

import com.company.objects.User;

public class TopicController extends AbsController{
    TopicController topicController = null;
    private TopicController() {
        super();
    }

    public TopicController getTopicController() {
        if (topicController == null) {
            topicController = new TopicController();
        }
        return topicController;
    }

    public void listTopics() {
        System.out.println();
    }

    @Override
    public void landingPage() {

    }

    @Override
    public void setUser(User u) {

    }
}
