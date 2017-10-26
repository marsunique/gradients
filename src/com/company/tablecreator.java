package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TableCreator {
    public static void createAll(){

        final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";

        try {

            // Load the driver. This creates an instance of the driver
            // and calls the registerDriver method to make Oracle Thin
            // driver available to clients.

            Class.forName("oracle.jdbc.driver.OracleDriver");

            String user = "dbhanda";	// For example, "jsmith"
            String passwd = "Dusrawala@$hu12";	// Your password


            Connection conn = null;
            Statement stmt = null;

            try {

                // Get a connection from the first driver in the
                // DriverManager list that recognizes the URL jdbcURL

                conn = DriverManager.getConnection(jdbcURL, user, passwd);

                // Create a statement object that will be sending your
                // SQL statements to the DBMS

                stmt = conn.createStatement();

                // Create all required tables

                String sqlforTA = "CREATE TABLE IF NOT EXISTS 'TA'" +
                                "( ta_id VARCHAR(255) NOT NULL," +
                                "first_name VARCHAR(255) NOT NULL, " +
                                "last_name VARCHAR(255) NOT NULL, " +
                                "PRIMARY KEY (ta_id) )";

                stmt.executeUpdate(sqlforTA);

                String sqlforInstructor = "CREATE TABLE IF NOT EXISTS 'Instructor'" +
                        "( inst_id VARCHAR(255) NOT NULL," +
                        "first_name VARCHAR(255) NOT NULL, " +
                        "last_name VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (inst_id) )";

                stmt.executeUpdate(sqlforInstructor);

                String sqlforStudent = "CREATE TABLE IF NOT EXISTS 'Student'" +
                        "( student_id VARCHAR(255) NOT NULL," +
                        "first_name VARCHAR(255) NOT NULL, " +
                        "last_name VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (student_id) )";

                stmt.executeUpdate(sqlforStudent);

                String sqlforCourse = "CREATE TABLE IF NOT EXISTS 'Course'" +
                        "( course_id INT NOT NULL AUTO_INCREMENT," +
                        "course_name VARCHAR(255) NOT NULL, " +
                        "start_date DATE NOT NULL, " +
                        "end_date DATE NOT NULL, " +
                        "inst_id INT NOT NULL, " +
                        "PRIMARY KEY (course_id), " +
                        "FOREIGN KEY (inst_id) REFERENCES Instructor(inst_id) )";

                stmt.executeUpdate(sqlforCourse);

                String sqlforExercise = "CREATE TABLE IF NOT EXISTS 'Exercise'" +
                        "( wrong_points FLOAT NOT NULL, " +
                        "right_points FLOAT NOT NULL, " +
                        "start_time TIMESTAMP NOT NULL, " +
                        "end_time TIMESTAMP NOT NULL, " +
                        "ex_id INT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "num_attempts INT NOT NULL, " +
                        "adaptive BOOLEAN NOT NULL, " +
                        "scoring_policy ENUM('last', 'average', 'highest') NOT NULL, " +
                        "course_id INT NOT NULL, " +
                        "PRIMARY KEY (ex_id, course_id), " +
                        "FOREIGN KEY (course_id) REFERENCES Course(course_id) )";

                stmt.executeUpdate(sqlforExercise);

                String sqlforTopic = "CREATE TABLE IF NOT EXISTS 'Topic'" +
                        "( topic_id INT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (topic_id) )";

                stmt.executeUpdate(sqlforTopic);

                String sqlforAssists = "CREATE TABLE IF NOT EXISTS 'Assists'" +
                        "( ta_id VARCHAR(255) NOT NULL, " +
                        "course_id INT NOT NULL, " +
                        "PRIMARY KEY (ta_id, course_id), " +
                        "FOREIGN KEY (ta_id) REFERENCES TA(ta_id), " +
                        "FOREIGN KEY (course_id) REFERENCES Course(course_id) )";

                stmt.executeUpdate(sqlforAssists);

                String sqlforEnrolled_In = "CREATE TABLE IF NOT EXISTS 'Enrolled_In'" +
                            "( student_id VARCHAR(255) NOT NULL, " +
                            "course_id INT NOT NULL, " +
                            "PRIMARY KEY (student_id, course_id), " +
                            "FOREIGN KEY (student_id) REFERENCES Student(student_id), " +
                            "FOREIGN KEY (course_id) REFERENCES Course(course_id) )";

                stmt.executeUpdate(sqlforEnrolled_In);

                String sqlforCourseTopic = "CREATE TABLE IF NOT EXISTS 'CourseTopic'" +
                        "( course_id INT NOT NULL, " +
                        "topic_id INT NOT NULL, " +
                        "PRIMARY KEY (course_id, topic_id), " +
                        "FOREIGN KEY (course_id) REFERENCES Course(course_id), " +
                        "FOREIGN KEY (topic_id) REFERENCES Topic(topic_id) )";

                stmt.executeUpdate(sqlforCourseTopic);

                String sqlforQuestion = "CREATE TABLE IF NOT EXISTS 'Question'" +
                        "( text VARCHAR(255) NOT NULL, " +
                        "hint VARCHAR(255), " +
                        "difficulty INT NOT NULL, " +
                        "ques_id INT NOT NULL AUTO_INCREMENT, " +
                        "solution VARCHAR(255) NOT NULL, " +
                        "topic_id INT NOT NULL, " +
                        "PRIMARY KEY (ques_id), " +
                        "FOREIGN KEY (topic_id) REFERENCES Topic(topic_id) )";

                stmt.executeUpdate(sqlforQuestion);

                String sqlforAnswer = "CREATE TABLE IF NOT EXISTS 'Answer'" +
                        "( text VARCHAR(255) NOT NULL," +
                        "ans_id INT NOT NULL AUTO_INCREMENT," +
                        "ques_id INT NOT NULL," +
                        "PRIMARY KEY (ans_id, ques_id)," +
                        "FOREIGN KEY (ques_id) REFERENCES Question(ques_id) )";

                stmt.executeUpdate(sqlforAnswer);

                String sqlforQuesParams = "CREATE TABLE IF NOT EXISTS 'QuesParams'" +
                        "( ans_id INT NOT NULL," +
                        "param_id INT NOT NULL AUTO_INCREMENT," +
                        "ques_id INT NOT NULL," +
                        "PRIMARY KEY (param_id, ques_id)," +
                        "FOREIGN KEY (ques_id) REFERENCES Question(ques_id) )";

                stmt.executeUpdate(sqlforQuesParams);

                String sqlforAttempt = "CREATE TABLE IF NOT EXISTS 'Attempt'" +
                        "( att_id INT NOT NULL AUTO_INCREMENT," +
                        "score FLOAT NOT NULL," +
                        "student_id VARCHAR(255) NOT NULL," +
                        "ex_id INT NOT NULL," +
                        "course_id INT NOT NULL," +
                        "PRIMARY KEY (att_id)," +
                        "FOREIGN KEY (student_id) REFERENCES Student(student_id)," +
                        "FOREIGN KEY (ex_id, course_id) REFERENCES Exercise(ex_id, course_id) )";

                stmt.executeUpdate(sqlforAttempt);

                String sqlforAttAnswers = "CREATE TABLE IF NOT EXISTS 'AttAnswers'" +
                        "( att_id INT NOT NULL," +
                        "ques_id INT NOT NULL," +
                        "param_id INT NOT NULL," +
                        "ans_id INT NOT NULL," +
                        "FOREIGN KEY (ans_id, ques_id) REFERENCES Answer(ans_id, ques_id)," +
                        "FOREIGN KEY (param_id, ques_id) REFERENCES QuesParams(param_id, ques_id) )";

                stmt.executeUpdate(sqlforAttAnswers);

                String sqlforExQuestions = "CREATE TABLE IF NOT EXISTS 'ExQuestions'" +
                        "( ex_id INT NOT NULL," +
                        "course_id INT NOT NULL," +
                        "ques_id INT NOT NULL," +
                        "PRIMARY KEY (ex_id, course_id, ques_id)," +
                        "FOREIGN KEY (ex_id, course_id) REFERENCES Exercise(ex_id, course_id)," +
                        "FOREIGN KEY (ques_id) REFERENCES Question(ques_id) )";


                stmt.executeUpdate(sqlforExQuestions);

                String sqlforParamVals = "CREATE TABLE IF NOT EXISTS 'ParamVals'" +
                        "( vals VARRAY(20) OF VARCHAR(255) NOT NULL," +
                        "param_id INT NOT NULL," +
                        "ques_id INT NOT NULL," +
                        "PRIMARY KEY (vals, param_id, ques_id)," +
                        "FOREIGN KEY (param_id, ques_id) REFERENCES QuesParams(param_id, ques_id) )";

                stmt.executeUpdate(sqlforParamVals);


            } finally {
                close(stmt);
                close(conn);
            }
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
    }

    static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }
}
