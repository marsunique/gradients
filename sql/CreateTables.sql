CREATE TABLE IF NOT EXISTS TA
(
  ta_id      VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  PRIMARY KEY (ta_id)
);

CREATE TABLE IF NOT EXISTS Instructor
(
  inst_id    VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  PRIMARY KEY (inst_id)
);

CREATE TABLE IF NOT EXISTS Student
(
  student_id VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  PRIMARY KEY (student_id)
);

CREATE TABLE IF NOT EXISTS Course
(
  course_id   INT          NOT NULL AUTO_INCREMENT,
  course_name VARCHAR(255) NOT NULL,
  start_date  DATE         NOT NULL,
  end_date    DATE         NOT NULL,
  inst_id     VARCHAR(255) NOT NULL,
  PRIMARY KEY (course_id),
  FOREIGN KEY (inst_id) REFERENCES Instructor (inst_id)
);

CREATE TABLE IF NOT EXISTS Exercise
(
  wrong_points   FLOAT                               NOT NULL,
  right_points   FLOAT                               NOT NULL,
  start_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  end_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  ex_id          INT                                 NOT NULL AUTO_INCREMENT,
  name           VARCHAR(255)                        NOT NULL,
  num_attempts   INT                                 NOT NULL,
  adaptive       BOOLEAN                             NOT NULL,
  scoring_policy ENUM ('last', 'average', 'highest') NOT NULL,
  course_id      INT                                 NOT NULL,
  PRIMARY KEY (ex_id, course_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id)
);

CREATE TABLE IF NOT EXISTS Topic
(
  topic_id INT          NOT NULL AUTO_INCREMENT,
  name     VARCHAR(255) NOT NULL,
  PRIMARY KEY (topic_id)
);

CREATE TABLE IF NOT EXISTS Assists
(
  ta_id     VARCHAR(255) NOT NULL,
  course_id INT          NOT NULL,
  PRIMARY KEY (ta_id, course_id),
  FOREIGN KEY (ta_id) REFERENCES TA (ta_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id)
);

CREATE TABLE IF NOT EXISTS EnrolledIn
(
  student_id VARCHAR(255) NOT NULL,
  course_id  INT          NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES Student (student_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id)
);

CREATE TABLE IF NOT EXISTS CourseTopic
(
  course_id INT NOT NULL,
  topic_id  INT NOT NULL,
  PRIMARY KEY (course_id, topic_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id),
  FOREIGN KEY (topic_id) REFERENCES Topic (topic_id)
);

CREATE TABLE IF NOT EXISTS Question
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

CREATE TABLE IF NOT EXISTS Answer
(
  text    VARCHAR(255) NOT NULL,
  ans_id  INT          NOT NULL AUTO_INCREMENT,
  ques_id INT          NOT NULL,
  PRIMARY KEY (ans_id, ques_id),
  FOREIGN KEY (ques_id) REFERENCES Question (ques_id)
);

CREATE TABLE IF NOT EXISTS QuesParams
(
  ans_id     INT NOT NULL,
  param_id   INT NOT NULL AUTO_INCREMENT,
  param_vals TEXT,
  ques_id    INT NOT NULL,
  PRIMARY KEY (param_id, ques_id),
  FOREIGN KEY (ques_id) REFERENCES Question (ques_id)
);

CREATE TABLE IF NOT EXISTS Attempt
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

CREATE TABLE IF NOT EXISTS AttAnswers
(
  att_id   INT NOT NULL,
  ques_id  INT NOT NULL,
  param_id INT NOT NULL,
  ans_id   INT NOT NULL,
  FOREIGN KEY (ans_id, ques_id) REFERENCES Answer (ans_id, ques_id),
  FOREIGN KEY (param_id, ques_id) REFERENCES QuesParams (param_id, ques_id)
);

CREATE TABLE IF NOT EXISTS ExQuestions
(
  ex_id     INT NOT NULL,
  course_id INT NOT NULL,
  ques_id   INT NOT NULL,
  PRIMARY KEY (ex_id, course_id, ques_id),
  FOREIGN KEY (ex_id, course_id) REFERENCES Exercise (ex_id, course_id),
  FOREIGN KEY (ques_id) REFERENCES Question (ques_id)
);
