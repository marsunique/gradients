package com.company.models;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TopicModel extends ModelBase {
    private static TopicModel topicModel = null;
    private TopicModel() throws IOException {
        super();
    }
    public static TopicModel getTopicModel() throws IOException {
        if (topicModel ==  null) {
            topicModel = new TopicModel();
        }
        return topicModel;
    }

    public ResultSet listTopics() throws SQLException {
        String query = "SELECT * FROM Topic";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        return rs;
    }

    public ArrayList<String> getAllTopics() {
        Statement stmt = null;
        String query = "SELECT * FROM Topic";
        ArrayList<String> ret = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String res = rs.getString("topic_id") + " -- " +
                        rs.getString("name");
                ret.add(res);
            }
            return ret;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTopicByID(int id) throws SQLException {
        Statement stmt = null;
        String query = "SELECT * " +
                "FROM Topic " +
                "WHERE topic_id=" + String.valueOf(id);
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getString("name");
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addTopic(String topicName) throws SQLException {
        String query = "INSERT INTO Topic(name) VALUES (?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, topicName);

        statement.execute();
    }

    public void deleteTopic(int topicID) throws SQLException {
        String query = "DELETE FROM Topic WHERE topic_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setInt(1, topicID);

        statement.execute();
    }
}
