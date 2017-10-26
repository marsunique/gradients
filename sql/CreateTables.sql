DROP TABLE IF EXISTS ExQuestions;
DROP TABLE IF EXISTS AttAnswers;
DROP TABLE IF EXISTS Attempt;
DROP TABLE IF EXISTS QuesParams;
DROP TABLE IF EXISTS Answer;
DROP TABLE IF EXISTS Question;
DROP TABLE IF EXISTS CourseTopic;
DROP TABLE IF EXISTS EnrolledIn;
DROP TABLE IF EXISTS Assists;
DROP TABLE IF EXISTS Topic;
DROP TABLE IF EXISTS Exercise;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Instructor;
DROP TABLE IF EXISTS TA;

CREATE TABLE TA
(
  ta_id      VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  PRIMARY KEY (ta_id)
);

CREATE TABLE Instructor
(
  inst_id    VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  PRIMARY KEY (inst_id)
);

CREATE TABLE Student
(
  student_id VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  PRIMARY KEY (student_id)
);

CREATE TABLE Course
(
  course_id   INT          NOT NULL AUTO_INCREMENT,
  course_name VARCHAR(255) NOT NULL,
  start_date  DATE         NOT NULL,
  end_date    DATE         NOT NULL,
  inst_id     VARCHAR(255) NOT NULL,
  PRIMARY KEY (course_id),
  FOREIGN KEY (inst_id) REFERENCES Instructor (inst_id)
);

CREATE TABLE Exercise
(
  wrong_points   FLOAT                               NOT NULL,
  right_points   FLOAT                               NOT NULL,
  start_time     TIMESTAMP                           NOT NULL,
  end_time       TIMESTAMP                           NOT NULL,
  ex_id          INT                                 NOT NULL AUTO_INCREMENT,
  name           VARCHAR(255)                        NOT NULL,
  num_attempts   INT                                 NOT NULL,
  adaptive       BOOLEAN                             NOT NULL,
  scoring_policy ENUM ('last', 'average', 'highest') NOT NULL,
  course_id      INT                                 NOT NULL,
  PRIMARY KEY (ex_id, course_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id)
);

CREATE TABLE Topic
(
  topic_id INT          NOT NULL AUTO_INCREMENT,
  name     VARCHAR(255) NOT NULL,
  PRIMARY KEY (topic_id)
);

CREATE TABLE Assists
(
  ta_id     VARCHAR(255) NOT NULL,
  course_id INT          NOT NULL,
  PRIMARY KEY (ta_id, course_id),
  FOREIGN KEY (ta_id) REFERENCES TA (ta_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id)
);

CREATE TABLE EnrolledIn
(
  student_id VARCHAR(255) NOT NULL,
  course_id  INT          NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES Student (student_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id)
);

CREATE TABLE CourseTopic
(
  course_id INT NOT NULL,
  topic_id  INT NOT NULL,
  PRIMARY KEY (course_id, topic_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id),
  FOREIGN KEY (topic_id) REFERENCES Topic (topic_id)
);

CREATE TABLE Question
(
  text       VARCHAR(255) NOT NULL,
  hint       VARCHAR(255),
  difficulty INT          NOT NULL,
  ques_id    INT          NOT NULL AUTO_INCREMENT,
  solution   VARCHAR(255) NOT NULL,
  topic_id   INT          NOT NULL,
  PRIMARY KEY (ques_id),
  FOREIGN KEY (topic_id) REFERENCES Topic (topic_id)
);

CREATE TABLE Answer
(
  text    VARCHAR(255) NOT NULL,
  ans_id  INT          NOT NULL AUTO_INCREMENT,
  ques_id INT          NOT NULL,
  PRIMARY KEY (ans_id, ques_id),
  FOREIGN KEY (ques_id) REFERENCES Question (ques_id)
);

CREATE TABLE QuesParams
(
  ans_id   INT NOT NULL,
  param_id INT NOT NULL AUTO_INCREMENT,
  param_vals TEXT,
  ques_id  INT NOT NULL,
  PRIMARY KEY (param_id, ques_id),
  FOREIGN KEY (ques_id) REFERENCES Question (ques_id)
);

CREATE TABLE Attempt
(
  att_id     INT          NOT NULL AUTO_INCREMENT,
  score      FLOAT        NOT NULL,
  student_id VARCHAR(255) NOT NULL,
  ex_id      INT          NOT NULL,
  course_id  INT          NOT NULL,
  PRIMARY KEY (att_id),
  FOREIGN KEY (student_id) REFERENCES Student (student_id),
  FOREIGN KEY (ex_id, course_id) REFERENCES Exercise (ex_id, course_id)
);

CREATE TABLE AttAnswers (
  att_id   INT NOT NULL,
  ques_id  INT NOT NULL,
  param_id INT NOT NULL,
  ans_id   INT NOT NULL,
  FOREIGN KEY (ans_id, ques_id) REFERENCES Answer (ans_id, ques_id),
  FOREIGN KEY (param_id, ques_id) REFERENCES QuesParams (param_id, ques_id)
);

CREATE TABLE ExQuestions
(
  ex_id     INT NOT NULL,
  course_id INT NOT NULL,
  ques_id   INT NOT NULL,
  PRIMARY KEY (ex_id, course_id, ques_id),
  FOREIGN KEY (ex_id, course_id) REFERENCES Exercise (ex_id, course_id),
  FOREIGN KEY (ques_id) REFERENCES Question (ques_id)
);
