package com.company.models;

import com.company.objects.Question;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionModel extends ModelBase {
    private static QuestionModel questionModel = null;
    private QuestionModel() throws SQLException, IOException{
        super();
    }

    public static QuestionModel getQuestionModel() throws IOException, SQLException {
        if (questionModel == null) {
            questionModel = new QuestionModel();
        }
        return questionModel;
    }

    public int addQuestion(Question question) throws SQLException {
        String query = "INSERT INTO Question(topic_id, text, hint, difficulty, solution)" +
                "VALUES(?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, question.getTopicID());
        statement.setString(2, question.getText());
        statement.setString(3, question.getHint());
        statement.setInt(4, question.getDifficulty());
        statement.setString(5, question.getSolution());

        statement.execute();

        ResultSet rs = statement.getGeneratedKeys();

        rs.next();
        return rs.getInt(1); // return the question id after the insertion.
    }

    public void deleteQuestion(int questionID) throws SQLException {
        String query = "DELETE FROM Question WHERE ques_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, questionID);

        statement.execute();
    }

}
