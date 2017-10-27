package com.company.models;

public class ProfessorModel {
    private static ProfessorModel professorModel = null;

    private ProfessorModel(){
    }

    public static ProfessorModel getProfessorModel(){
        if(professorModel == null) {
            professorModel = new ProfessorModel();
        }

        return professorModel;
    }

    public void viewProfile() {
        String query = "SELECT * " +
                        "FROM Instructor " +
                        "WHERE inst_id = " + "instructor1;";


    }

    public void courseViewAdd() {

    }

    public void studentEnrollDrop() {

    }

    public void viewReport() {

    }

    public void setupTA() {

    }

    public void exerciseViewAdd() {

    }

    public void searchAddQuestionToBank() {

    }

    public void addRemoveQuestionFromExercise() {

    }
}
