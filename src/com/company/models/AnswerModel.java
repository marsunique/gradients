package com.company.models;

import com.company.objects.Answer;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        String query = "INSERT INTO Answer(text, ques_id) VALUES(?,?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, answer.text);
        statement.setInt(2, answer.quesID);

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
