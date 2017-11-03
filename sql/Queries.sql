#1. Find names of all students of CSC 540 that attempted HW1 but did not attempt HW2.
SELECT u.first_name, u.last_name
FROM user u, attempt a, enrolledin s
WHERE s.course_id='CSC540'
AND u.id=s.student_id
AND a.student_id=s.student_id
AND a.ex_id=1
AND NOT EXISTS(
  SELECT *
  FROM attempt a2
  WHERE a2.student_id = s.student_id
  AND a2.ex_id=2
);

#2. Give list of students whose score increased on second attempt. ????


#3. List all courses and number of students enrolled.
SELECT course_id, course_name, num_enrolled
FROM course;

#4. Show a report of all homework and attempts for all students enrolled in CSC540.
SELECT e.name, s.student_id, a.score
FROM enrolledin s, attempt a, exercise e
WHERE s.course_id = 'CSC540'
AND a.student_id = s.student_id
AND a.ex_id = e.ex_id;