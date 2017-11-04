package com.company.models;

import com.company.objects.Answer;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AnswerModel extends ModelBase {
    private static AnswerModel answerModel = null;
    private AnswerModel() throws IOException {
        super();
    }

    public static AnswerModel getAnswerModel() throws IOException {
        if (answerModel == null) {
            answerModel = new AnswerModel();
        }

        return answerModel;
    }

    public void addAnswer(Answer answer) throws SQLException {
        String query = "INSERT INTO Answer(text,explanation,correct, ques_id, param_id) VALUES(?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, answer.getText());
        statement.setString(2, answer.getExplanation());
        statement.setInt(3, answer.getCorrect());
        statement.setInt(4, answer.getQuesID());
        if (answer.getParameterID() == 0) {
            statement.setNull(5, Types.INTEGER);
        } else {
            statement.setInt(5, answer.getParameterID());
        }
        statement.execute();
    }

    public void deleteAnswer(int ansID, int quesID) throws SQLException {
        String query = "DELETE FROM Answer WHERE ans_id = ? AND ques_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, ansID);
        statement.setInt(2, quesID);

        statement.execute();
    }

}
