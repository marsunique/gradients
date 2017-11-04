# A sql script to insert all of the sample data given for this project.

INSERT INTO User (first_name, last_name, id, password) VALUES
  ('Tom', 'Regan', 'tregan', 'tregan'),
  ('Jenelle', 'Mick', 'jmick', 'jmick'),
  ('Michal', 'Fisher', 'mfisher', 'mfisher'),
  ('Joseph', 'Anderson', 'jander', 'jander'),
  ('Jitendra', 'Harlalka', 'jharla', 'jharla'),
  ('Aishwarya', 'Neelakantan', 'aneela', 'aneela'),
  ('Mary', 'Jones', 'mjones', 'mjones'),
  ('James', 'Moyer', 'jmoyer', 'jmoyer'),
  ('Kemafor', 'Ogan', 'kogan', 'kogan'),
  ('Rada', 'Chirkova', 'rchirkova', 'rchirkova'),
  ('Christopher', 'Healey', 'chealey', 'chealey');

INSERT INTO Student (student_id) VALUES
  ('tregan'),
  ('mfisher'),
  ('jander'),
  ('jmick'),
  ('jharla'),
  ('aneela'),
  ('mjones'),
  ('jmoyer');

INSERT INTO Graduate (grad_id) VALUES
  ('jmick'),
  ('jharla'),
  ('aneela'),
  ('mjones'),
  ('jmoyer');

INSERT INTO Instructor (inst_id) VALUES
  ('kogan'),
  ('rchirkova'),
  ('chealey');

INSERT INTO Topic (topic_id, name) VALUES
  (1, 'ER Model'),
  (2, 'SQL'),
  (3, 'Storing Data: Disks and Files'),
  (4, 'Primary File Organization'),
  (5, 'Hashing Techniques'),
  (6, 'Binary Tree Structures'),
  (7, 'AVL Trees'),
  (8, 'Sequential File Organization'),
  (9, 'BinarySearch'),
  (10, 'Interpolation Search'),
  (11, 'Introduction to Database Design');

INSERT INTO Course (course_id, course_name, start_date, end_date, inst_id, graduate, max_enrolled, num_enrolled) VALUES
  ('CSC440', 'Database Systems', '2017-08-27', '2017-12-12', 'rchirkova', FALSE, 5, 0),
  ('CSC540', 'Database Systems', '2017-08-25', '2017-12-10', 'kogan', TRUE, 5, 0),
  ('CSC541', 'Advanced Data Structures', '2017-08-25', '2017-12-06', 'chealey', TRUE, 5, 0);

INSERT INTO EnrolledIn (student_id, course_id) VALUES
  ('tregan', 'CSC440'),
  ('mfisher', 'CSC440'),
  ('jander', 'CSC440'),
  ('aneela', 'CSC540'),
  ('mjones', 'CSC540'),
  ('jmick', 'CSC540'),
  ('jmoyer', 'CSC540'),
  ('aneela', 'CSC541'),
  ('mjones', 'CSC541'),
  ('jmick', 'CSC541');

INSERT INTO TAFor (ta_id, course_id) VALUES
  ('aneela', 'CSC440'),
  ('jmick', 'CSC440'),
  ('jharla', 'CSC540'),
  ('jmoyer', 'CSC541');

INSERT INTO CourseTopic (course_id, topic_id) VALUES
  ('CSC440', 4),
  ('CSC440', 6),
  ('CSC540', 3),
  ('CSC540', 11),
  ('CSC540', 4),
  ('CSC540', 6),
  ('CSC541', 4),
  ('CSC541', 6);

INSERT INTO Exercise (wrong_points, right_points, name, num_attempts, adaptive, scoring_policy, course_id, topic_id, start_date, end_date, min_dif, max_dif)
VALUES
  (1, 3, 'Homework 1', 2, FALSE, 'last', 'CSC540', 11, '2017-08-12', '2017-09-19', 1, 3),
  (1, 4, 'Homework 2', 2, TRUE, 'average', 'CSC540', 11, '2017-09-21', '2017-10-10', 3, 5),
  (0, 4, 'Homework 3', 9999, FALSE, 'average', 'CSC540', 3, '2017-10-12', '2017-10-30', 3, 5);

INSERT INTO Question (text, hint, difficulty, solution, topic_id) VALUES
  ('Question 1?', 'Hint Text Q1', 2, 'Detailed Explanation Q1', 11),
  ('Question 2?', 'Hint Text Q2', 3, 'Detailed Explanation Q2', 11),
  ('Consider a disk with a <sector size>, <tracks per surface>, <sectors per track>, <double sided platters>,
  <average seek time>. What is the capacity of a track in bytes?', 'Hint Text Q3', 2, 'Detailed Explanation Q3', 11);

INSERT INTO ExQuestions (ex_id, ques_id) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (2, 1),
  (2, 2),
  (2, 3),
  (3, 1),
  (3, 2),
  (3, 3);

INSERT INTO Parameters (param_vals, ques_id) VALUES
  ('512 bytes, 2000, 50, 5, 10msec', 3),
  ('256 bytes, 1000, 100, 10, 20msec', 3);

INSERT INTO Answer (text, ques_id, explanation, correct, param_id) VALUES
  ('Correct Ans 1', 1, '', TRUE, NULL),
  ('Correct Ans 2', 1, '', TRUE, NULL),
  ('Incorrect Ans 3', 1, 'Short explanation 3', FALSE, NULL),
  ('Incorrect Ans 4', 1, 'Short explanation 4', FALSE, NULL),
  ('Incorrect Ans 5', 1, 'Short explanation 5', FALSE, NULL),
  ('Incorrect Ans 6', 1, 'Short explanation 6', FALSE, NULL),
  ('Correct Ans 1', 2, '', TRUE, NULL),
  ('Correct Ans 2', 2, '', TRUE, NULL),
  ('Incorrect Ans 3', 2, 'Short explanation 3', FALSE, NULL),
  ('Incorrect Ans 4', 2, 'Short explanation 4', FALSE, NULL),
  ('Incorrect Ans 5', 2, 'Short explanation 5', FALSE, NULL),
  ('Incorrect Ans 6', 2, 'Short explanation 6', FALSE, NULL),
  ('Correct Ans 1v1', 3, '', TRUE, 1),
  ('Correct Ans 2v1', 3, '', TRUE, 1),
  ('Correct Ans 3v1', 3, '', TRUE, 1),
  ('Incorrect Ans 4v1', 3, 'Short explanation 4', FALSE, 1),
  ('Incorrect Ans 5v1', 3, 'Short explanation 5', FALSE, 1),
  ('Incorrect Ans 6v1', 3, 'Short explanation 6', FALSE, 1),
  ('Incorrect Ans 7v1', 3, 'Short explanation 7', FALSE, 1),
  ('Incorrect Ans 8v1', 3, 'Short explanation 8', FALSE, 1),
  ('Correct Ans 1v2', 3, '', TRUE, 2),
  ('Correct Ans 2v2', 3, '', TRUE, 2),
  ('Correct Ans 3v2', 3, '', TRUE, 2),
  ('Incorrect Ans 4v2', 3, 'Short explanation 4', FALSE, 2),
  ('Incorrect Ans 5v2', 3, 'Short explanation 5', FALSE, 2),
  ('Incorrect Ans 6v2', 3, 'Short explanation 6', FALSE, 2),
  ('Incorrect Ans 7v2', 3, 'Short explanation 7', FALSE, 2),
  ('Incorrect Ans 8v2', 3, 'Short explanation 8', FALSE, 2);

INSERT INTO Attempt (score, student_id, ex_id, course_id) VALUES
  (5.0, 'mjones', 1, 'CSC540'),
  (9.0, 'mjones', 1, 'CSC540'),
  (9.0, 'jmick', 1, 'CSC540'),
  (9.0, 'jmoyer', 1, 'CSC540'),
  (7.0, 'aneela', 2, 'CSC540'),
  (12.0, 'aneela', 2, 'CSC540'), # Note that in the Sample Data it says "thrice" but only gives these entries
  (3.0, 'mjones', 2, 'CSC540'),
  (7.0, 'mjones', 2, 'CSC540'),
  (12.0, 'jmoyer', 2, 'CSC540'),
  (8.0, 'aneela', 3, 'CSC540'),
  (4.0, 'aneela', 3, 'CSC540'),
  (12.0, 'aneela', 3, 'CSC540'),
  (8.0, 'mjones', 3, 'CSC540'),
  (12.0, 'mjones', 3, 'CSC540'),
  (12.0, 'jmoyer', 3, 'CSC540');
