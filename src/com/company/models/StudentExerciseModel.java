package com.company.models;

import com.company.objects.Answer;
import com.company.objects.Exercise;
import com.company.objects.Question;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

        int att_id;
        try {
            String query = "INSERT INTO Attempt " +
                    "( score, student_id, ex_id, course_id)" +
                    "VALUES " +
                    "(?, ?, ?, ?)";


            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, exercise.getFinalScore());
            statement.setString(2, studentId);
            statement.setInt(3, exercise.getId());
            statement.setString(4, exercise.getCourseID());

            statement.execute();
        }catch(Exception e){
            System.out.println("issue saving attempt into Attempt table...");
        }

        try {
            Statement statement = conn.createStatement();
            String query = "SELECT MAX(att_id) FROM Attempt";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                att_id = rs.getInt("MAX(att_id)");
                for (Question q : questions){
                    query = "INSERT INTO AttAnswers " +
                            "( att_id, ques_id, correct_ans_id, ans_id, param_id)" +
                            "VALUES " +
                            "(?, ?, ?, ? ,?)";

                    PreparedStatement pstatement = conn.prepareStatement(query);
                    pstatement.setInt(1, att_id);
                    pstatement.setInt(2, q.getQuestionID());
                    pstatement.setInt(3, q.getActualAnswer().getAnsID());
                    pstatement.setInt(4, q.getStudentAnswer().getAnsID());
                    pstatement.setInt(5, q.getParamIndex());

                    pstatement.execute();
                }
            }
        }catch(Exception e){
            System.out.println("issue saving attempt into Attempt table...");
        }




    }




}
