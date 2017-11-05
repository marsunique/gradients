package com.company.models;

import com.company.objects.Answer;
import com.company.objects.Exercise;
import com.company.objects.Question;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class StudentExerciseModel extends ModelBase {
    private static StudentExerciseModel studentExerciseModel = null;

    private StudentExerciseModel() throws IOException {
        super();
    }

    public static StudentExerciseModel getStudentExerciseModel() {
        if (studentExerciseModel == null) {
            try {
                studentExerciseModel = new StudentExerciseModel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return studentExerciseModel;
    }

    public void saveAttemptToDB(List<Question> questions, String studentId, Exercise exercise){

        try {

//            String query = "INSERT INTO Attempt " +
//                    "(att_id, ex_id, student_id, course_id)" +
//                    "VALUES " +
//                    "(?, ?, ?, ?)";
            String query = "INSERT INTO Attempt " +
                    "( score, student_id, ex_id, course_id)" +
                    "VALUES " +
                    "(?, ?, ?, ?)";


            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, exercise.getFinalScore());
            statement.setString(2, studentId);
            statement.setInt(3, questions.get(0).getExerciseID());
            statement.setString(4, exercise.getCourseID());

            statement.execute();
        }catch(Exception e){
            System.out.println("issue saving attempt into Attempt table...");
        }

    }




}
