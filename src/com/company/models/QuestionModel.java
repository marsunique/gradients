package com.company.models;

import com.company.objects.Answer;
import com.company.objects.Question;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionModel extends ModelBase {
    private static QuestionModel questionModel = null;

    private QuestionModel() throws IOException {
        super();
    }

    public static QuestionModel getQuestionModel() throws IOException {
        if (questionModel == null) {
            questionModel = new QuestionModel();
        }
        return questionModel;
    }

    public List<Question> getQuestionsByEx(int eid) {
        List<Question> ret = new ArrayList<>();
        Statement stmt = null;

        String query = "SELECT q.ques_id, q.text, q.difficulty, q.hint, q.solution, q.topic_id, e.ex_id\n" +
                "FROM exercise e, exquestions eq, question q\n" +
                "WHERE e.ex_id = 2\n" +
                "      AND e.ex_id = eq.ex_id\n" +
                "      AND eq.ques_id = q.ques_id\n" +
                "ORDER BY name;";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Question q = new Question();
                q.difficulty = rs.getInt("difficulty");
                q.exID = rs.getInt("ex_id");
                q.hint = rs.getString("hint");
                q.quesID = rs.getInt("ques_id");
                q.solution = rs.getString("solution");
                q.text = rs.getString("text");
                q.topicID = rs.getInt("topic_id");
                ret.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    public List<Answer> getAnswersByQues(int aid) {
        List<Answer> ret = new ArrayList<>();
        Statement stmt = null;


        String query = "SELECT a.text, a.ans_id, a.ques_id, a.explanation, a.correct, a.param_id, p.param_vals\n" +
                "FROM answer a\n" +
                "  LEFT JOIN (parameters p)\n" +
                "    ON (a.ques_id = p.ques_id AND a.param_id = p.param_id)\n" +
                "WHERE a.ques_id = 3;";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Answer a = new Answer();
                a.ansID = rs.getInt("ans_id");
                a.correct = rs.getBoolean("correct");
                a.explanation = rs.getString("explanation");
                a.paramID = rs.getInt("param_id");
                a.text = rs.getString("text");
                a.quesID = rs.getInt("ques_id");
                String[] paramVals = rs.getString("param_vals").split(", ");
                a.paramVals.addAll(Arrays.asList(paramVals));
                ret.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    public void addQuestion(Question question) throws SQLException {
        String query = "INSERT INTO Question(topic_id, text, hint, difficulty, solution)" +
                "VALUES(?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, question.topicID);
        statement.setString(2, question.text);
        statement.setString(3, question.hint);
        statement.setInt(4, question.difficulty);
        statement.setString(5, question.solution);

        statement.execute();
    }

    public void deleteQuestion(int questionID) throws SQLException {
        String query = "DELETE FROM Question WHERE ques_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, questionID);

        statement.execute();
    }

}
