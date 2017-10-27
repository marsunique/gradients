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
    WHERE course_id = NEW.course_id;
  END;