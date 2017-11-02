package com.company.objects;

import java.util.ArrayList;
import java.util.Date;

public class Course {

    public String id;
    public String name;
    public Date start;
    public Date end;
    public String instructor;
    public Boolean graduate;
    public int maxEnrolled;
    public int numEnrolled;
    public ArrayList<Integer> exerciseIds;
    public ArrayList<String> exerciseNames;

    public Course(){
        exerciseNames = new ArrayList<>();
        exerciseIds = new ArrayList<>();
    }

}
