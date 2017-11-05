DROP TRIGGER IF EXISTS CourseCheck;
DROP TRIGGER IF EXISTS ExerciseCheck;
DROP TRIGGER IF EXISTS QuestionCheck;
DROP TRIGGER IF EXISTS CheckEnrolled;
DROP TRIGGER IF EXISTS EnrollStudent;
DROP TRIGGER IF EXISTS DropStudent;
DROP TRIGGER IF EXISTS AttExercise;
DROP TRIGGER IF EXISTS GradEnroll;
DROP TRIGGER IF EXISTS NewAttempt;

# A sql script containing some triggers to enforce constraints.

CREATE TRIGGER EnrollStudent AFTER INSERT ON EnrolledIn FOR EACH ROW BEGIN UPDATE Course SET num_enrolled = num_enrolled + 1 WHERE course_id = NEW.course_id; END;

CREATE TRIGGER CheckEnrolled BEFORE INSERT ON EnrolledIn FOR EACH ROW BEGIN IF ( EXISTS( SELECT * FROM Course C WHERE C.course_id IN (NEW.course_id) AND C.num_enrolled >= C.max_enrolled)) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Surpassed Maximum Enrolled Students'; END IF; END;

CREATE TRIGGER DropStudent AFTER DELETE ON EnrolledIn FOR EACH ROW BEGIN UPDATE Course SET num_enrolled = num_enrolled - 1 WHERE course_id = OLD.course_id; END;

CREATE TRIGGER CourseCheck BEFORE INSERT ON course FOR EACH ROW BEGIN IF (SELECT NEW.course_id NOT RLIKE '[A-Z]{3}[0-9]{3}') THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: CourseID Invalid'; END IF; IF (NEW.start_date >= NEW.end_date) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Start date must be before end date'; END IF; IF (NEW.num_enrolled > NEW.max_enrolled) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Number of enrolled can not exceed max enrolled'; END IF; END;

CREATE TRIGGER ExerciseCheck BEFORE INSERT ON exercise FOR EACH ROW BEGIN IF (NEW.start_date >= NEW.end_date) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Start date must be before end date'; END IF; IF (NEW.min_dif > NEW.max_dif) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Minimum difficulty must be lower than maximum difficulty'; END IF; IF (NEW.min_dif > 5 OR NEW.min_dif < 1 OR NEW.max_dif > 5 OR NEW.max_dif < 1) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Difficulty must be between 1 and 5'; END IF; END;

CREATE TRIGGER QuestionCheck BEFORE INSERT ON question FOR EACH ROW BEGIN IF (NEW.difficulty > 5 OR NEW.difficulty < 1) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Difficulty must be between 1 and 5'; END IF; END;

CREATE TRIGGER AttExercise BEFORE INSERT ON attempt FOR EACH ROW BEGIN IF (NOT EXISTS(SELECT * FROM enrolledin WHERE enrolledin.course_id = NEW.course_id AND enrolledin.student_id = NEW.student_id)) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Student must be enrolled in course to make attempt'; END IF; END;

CREATE TRIGGER GradEnroll BEFORE INSERT ON enrolledin FOR EACH ROW BEGIN IF (EXISTS(SELECT * FROM course WHERE course.course_id = NEW.course_id AND graduate = TRUE) AND NOT EXISTS(SELECT * FROM graduate WHERE graduate.grad_id = NEW.student_id)) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Student must be a graduate to enroll in grad classes'; END IF; IF (EXISTS(SELECT * FROM tafor WHERE NEW.student_id = tafor.ta_id AND NEW.course_id = tafor.course_id)) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Student may not enroll in a class they TA for'; END IF; END;

CREATE TRIGGER NewAttempt BEFORE INSERT ON attempt FOR EACH ROW BEGIN SET NEW.att_num = (SELECT COUNT(*) FROM attempt a WHERE a.course_id = NEW.course_id AND a.student_id = NEW.student_id AND a.ex_id = NEW.ex_id) + 1; IF(NEW.att_num > (SELECT e.num_attempts FROM exercise e WHERE e.ex_id=NEW.ex_id)) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: This student has attempted the maximum number of times'; END IF; END;