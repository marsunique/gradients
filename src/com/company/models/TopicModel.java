package com.company.models;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void listTopics() throws SQLException {
        String query = "SELECT * FROM Topic";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println("Topic ID: " + rs.getInt("topic_id")
            + "Topic Name: " + rs.getString("name"));
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
