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

    public Question getQuestionByQuesID(int quesID) throws SQLException {
        Question question = new Question();
        String query = "SELECT * FROM Question WHERE ques_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, quesID);

        ResultSet rs = statement.executeQuery();
        rs.next();
        question.setQuestionID(rs.getInt("ques_id"));
        question.setText(rs.getString("text"));
        question.setSolution(rs.getString("solution"));
        question.setDifficulty(rs.getInt("difficulty"));
        question.setTopicID(rs.getInt("topic_id"));
        question.setHint(rs.getString("hint"));

        return question;
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
                q.setDifficulty(rs.getInt("difficulty"));
                q.setExerciseID(rs.getInt("ex_id"));
                q.setHint(rs.getString("hint"));
                q.setQuestionID(rs.getInt("ques_id"));
                q.setSolution(rs.getString("solution"));
                q.setText(rs.getString("text"));
                q.setTopicID(rs.getInt("topic_id"));
                ret.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    public List<Answer> getAnswersByQues(int qid) {
        List<Answer> ret = new ArrayList<>();
        Statement stmt = null;


        String query = "SELECT a.text, a.ans_id, a.ques_id, a.explanation, a.correct, a.param_id, p.param_vals\n" +
                "FROM answer a\n" +
                "  LEFT JOIN (parameters p)\n" +
                "    ON (a.ques_id = p.ques_id AND a.param_id = p.param_id)\n" +
                "WHERE a.ques_id = " + qid + ";";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Answer a = new Answer();
                a.setAnsID(rs.getInt("ans_id"));
                a.setCorrect(rs.getInt("correct"));
                a.setExplanation(rs.getString("explanation"));
                a.setParameterID(rs.getInt("param_id"));
                a.setText(rs.getString("text"));
                a.setQuesID(rs.getInt("ques_id"));
                String paramValsString = rs.getString("param_vals");
                if (paramValsString != null){
                    String[] paramVals = rs.getString("param_vals").split(", ");
                    a.paramVals.addAll(Arrays.asList(paramVals));
                }

                ret.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    public List<Question> getQuestionsByTopic(int topicID) throws SQLException {
        String query = "SELECT * FROM Question WHERE topic_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, topicID);

        ResultSet rs = preparedStatement.executeQuery();

        List<Question> questionsList = new ArrayList<>();
        while (rs.next()) {
            Question question = new Question();

            question.setQuestionID(rs.getInt("ques_id"));
            question.setText(rs.getString("text"));
            question.setHint(rs.getString("hint"));
            question.setDifficulty(rs.getInt("difficulty"));
            question.setSolution(rs.getString("solution"));
            question.setTopicID(rs.getInt("topic_id"));
            questionsList.add(question);
        }

        return questionsList;
    }

    public int addQuestion(Question question) throws SQLException {
        String query = "INSERT INTO Question(topic_id, text, hint, difficulty, solution)" +
                "VALUES(?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);
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
