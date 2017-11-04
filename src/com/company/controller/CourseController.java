package com.company.controller;

import com.company.models.CourseModel;
import com.company.objects.User;

import java.io.IOException;
import java.util.Map;

public class CourseController extends AbsController{

    private static CourseController courseController = null;

    private CourseController() {
        super();
    }

    public static CourseController getCourseController() {
        if (courseController == null) {
            courseController = new CourseController();
        }
        return courseController;
    }

    public Map<Integer, String> listCourseTopics (String courserID) throws IOException {
        CourseModel courseModel = CourseModel.getCourseModel();
        Map<Integer, String> topics = null;
        try {
            topics = courseModel.listCourseTopics(courserID);
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return topics;
    }

    @Override
    public void landingPage() {

    }

    @Override
    public void setUser(User u) {

    }
}
