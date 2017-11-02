# A sql script to create all tables for this assignment. Note that it will not overwrite existing tables.

CREATE TABLE IF NOT EXISTS User
(
  id         VARCHAR(255) NOT NULL,
  password   VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

# These will only be added one time, if they don't already
REPLACE INTO User (id, password, first_name, last_name) VALUES
  ('dlambright', 'dustin', 'Dustin', 'Lambright'),
  ('dbhandari', 'darshan', 'Darshan', 'Bhandari'),
  ('ysun', 'yuchen', 'Yuchen', 'Sun'),
  ('lkerr', 'leonard', 'Leonard', 'Kerr'),
  ('gyu', 'guanxu', 'Guanxu', 'Yu');


CREATE TABLE IF NOT EXISTS Graduate
(
  grad_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (grad_id) REFERENCES User (id)
);

CREATE TABLE IF NOT EXISTS Instructor
(
  inst_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (inst_id) REFERENCES User (id)
);

CREATE TABLE IF NOT EXISTS Student
(
  student_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (student_id) REFERENCES User (id)
);

CREATE TABLE IF NOT EXISTS Course
(
  course_id    VARCHAR(6)   NOT NULL,
  course_name  VARCHAR(255) NOT NULL,
  start_date   DATE         NOT NULL,
  end_date     DATE         NOT NULL,
  inst_id      VARCHAR(255) NOT NULL,
  graduate     BOOL DEFAULT FALSE,
  max_enrolled INT          NOT NULL,
  num_enrolled INT  DEFAULT 0,
  PRIMARY KEY (course_id),
  FOREIGN KEY (inst_id) REFERENCES Instructor (inst_id)
);

CREATE TABLE IF NOT EXISTS Topic
(
  topic_id INT          NOT NULL AUTO_INCREMENT,
  name     VARCHAR(255) NOT NULL,
  PRIMARY KEY (topic_id)
);

CREATE TABLE IF NOT EXISTS Exercise
(
  wrong_points   FLOAT                               NOT NULL,
  right_points   FLOAT                               NOT NULL,
  start_date     DATE                                NOT NULL,
  end_date       DATE                                NOT NULL,
  ex_id          INT                                 NOT NULL AUTO_INCREMENT,
  name           VARCHAR(255)                        NOT NULL,
  num_attempts   INT                                 NOT NULL,
  adaptive       BOOLEAN                             NOT NULL,
  scoring_policy ENUM ('last', 'average', 'highest') NOT NULL,
  course_id      VARCHAR(6)                          NOT NULL,
  topic_id       INT                                 NOT NULL,
  min_dif        INT                                 NOT NULL,
  max_dif        INT                                 NOT NULL,
  PRIMARY KEY (ex_id, course_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id),
  FOREIGN KEY (topic_id) REFERENCES Topic (topic_id)
);

CREATE TABLE IF NOT EXISTS TAFor
(
  ta_id     VARCHAR(255) NOT NULL,
  course_id VARCHAR(6)   NOT NULL,
  PRIMARY KEY (ta_id, course_id),
  FOREIGN KEY (ta_id) REFERENCES Graduate (grad_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id)
);

CREATE TABLE IF NOT EXISTS EnrolledIn
(
  student_id VARCHAR(255) NOT NULL,
  course_id  VARCHAR(6)   NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES Student (student_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id)
);

CREATE TABLE IF NOT EXISTS CourseTopic
(
  course_id VARCHAR(6) NOT NULL,
  topic_id  INT        NOT NULL,
  PRIMARY KEY (course_id, topic_id),
  FOREIGN KEY (course_id) REFERENCES Course (course_id),
  FOREIGN KEY (topic_id) REFERENCES Topic (topic_id)
);

CREATE TABLE IF NOT EXISTS Question
(
  text       TEXT NOT NULL,
  hint       TEXT,
  difficulty INT  NOT NULL,
  ques_id    INT  NOT NULL AUTO_INCREMENT,
  solution   TEXT NOT NULL,
  topic_id   INT  NOT NULL,
  PRIMARY KEY (ques_id),
  FOREIGN KEY (topic_id) REFERENCES Topic (topic_id)
);

CREATE TABLE IF NOT EXISTS Parameters
(
  param_id   INT NOT NULL AUTO_INCREMENT,
  param_vals TEXT,
  ques_id    INT NOT NULL,
  PRIMARY KEY (param_id, ques_id),
  FOREIGN KEY (ques_id) REFERENCES Question (ques_id)
);

CREATE TABLE IF NOT EXISTS Answer
(
  text        TEXT NOT NULL,
  ans_id      INT  NOT NULL AUTO_INCREMENT,
  ques_id     INT  NOT NULL,
  explanation TEXT NOT NULL,
  correct     BOOL NOT NULL,
  param_id    INT,
  PRIMARY KEY (ans_id, ques_id),
  FOREIGN KEY (ques_id) REFERENCES Question (ques_id),
  FOREIGN KEY (param_id) REFERENCES Parameters (param_id)
);

CREATE TABLE IF NOT EXISTS Attempt
(
  att_id     INT          NOT NULL AUTO_INCREMENT,
  score      FLOAT        NOT NULL,
  student_id VARCHAR(255) NOT NULL,
  ex_id      INT          NOT NULL,
  course_id  VARCHAR(6)   NOT NULL,
  PRIMARY KEY (att_id),
  FOREIGN KEY (student_id) REFERENCES Student (student_id),
  FOREIGN KEY (ex_id, course_id) REFERENCES Exercise (ex_id, course_id)
);

# This table may not be necessary, if we only need to store the attempt score.
CREATE TABLE IF NOT EXISTS AttAnswers
(
  att_id   INT NOT NULL,
  ques_id  INT NOT NULL,
  param_id INT NOT NULL,
  ans_id   INT NOT NULL,
  FOREIGN KEY (ans_id, ques_id) REFERENCES Answer (ans_id, ques_id),
  FOREIGN KEY (param_id, ques_id) REFERENCES Parameters (param_id, ques_id)
);

CREATE TABLE IF NOT EXISTS ExQuestions
(
  ex_id   INT NOT NULL,
  ques_id INT NOT NULL,
  PRIMARY KEY (ex_id, ques_id),
  FOREIGN KEY (ex_id) REFERENCES Exercise (ex_id),
  FOREIGN KEY (ques_id) REFERENCES Question (ques_id)
);
