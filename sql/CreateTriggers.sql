# A sql script containing some triggers to enforce constraints.

CREATE TRIGGER EnrollStudent
AFTER INSERT ON EnrolledIn
FOR EACH ROW
  BEGIN
    UPDATE Course
    SET num_enrolled = num_enrolled + 1
    WHERE course_id = NEW.course_id;
  END;

CREATE TRIGGER CheckEnrolled
BEFORE INSERT ON EnrolledIn
FOR EACH ROW
  BEGIN
    IF (
      EXISTS(
          SELECT *
          FROM Course C
          WHERE C.course_id IN (NEW.course_id)
                AND C.num_enrolled >= C.max_enrolled))
    THEN
      SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Error: Surpassed Maximum Enrolled Students';
    END IF;
  END;

CREATE TRIGGER DropStudent
AFTER DELETE ON EnrolledIn
FOR EACH ROW
  BEGIN
    UPDATE Course
    SET num_enrolled = num_enrolled - 1
    WHERE course_id = OLD.course_id;
  END;

CREATE TRIGGER CourseID
BEFORE INSERT ON course
  FOR EACH ROW
  BEGIN
    IF(NEW.course_id NOT LIKE '[a-zA-Z][a-zA-Z][a-zA-Z][1-9][1-9][1-9]')
      THEN
      SIGNAL SQL
    END IF;
  END;