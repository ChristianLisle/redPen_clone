package myProject;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {
	@Autowired
	CourseRepository courses;

	@Autowired
	AssignmentRepository assignments;

	@Autowired
	AssignedAssignmentRepository assignedAssignments;

	// Basic Course mappings

	// Get course {id}
	@GetMapping("/course/{id}")
	Course getCourse(@PathVariable Integer id) {
		return courses.findOne(id);
	}

	// Get all courses
	@RequestMapping("/courses")
	List<Course> hello() {
		return courses.findAll();
	}

	// Create a new course
	@PostMapping("course")
	Course createCourse(@RequestBody Course s) {
		courses.save(s);
		return s;
	}

	// Update a course
	@PutMapping("/course/{id}/update")
	Course updateCourse(@RequestBody Course c, @PathVariable Integer id) {
		Course old_c = courses.findOne(id);
		old_c.setName(c.getName());
		old_c.setCourseDescription(c.getCourseDescription());
		courses.save(old_c);
		return old_c;
	}

	// Delete course {id}
	@DeleteMapping("/course/{id}")
	String deleteCourse(@PathVariable Integer id) {
		courses.delete(id);
		return "deleted " + id;
	}
<<<<<<< Backend/src/main/java/myProject/CourseController.java

=======
	
	/*
>>>>>>> Backend/src/main/java/myProject/CourseController.java
	@Autowired
	CourseRegistrationRepository registrar;

	@Autowired
	TeacherCourseRepository teacherCourses;

	// Course student and teacher mappings

	// Get students taking course {id} (teacher is irrelevant)
	@RequestMapping("/course/{id}/students")
	List<Student> getStudents(@PathVariable Integer id) {
		List<Student> s = new ArrayList<Student>();
		List<CourseRegistration> list = registrar.findAll();
		for (CourseRegistration cr : list) {
			if (cr.getCourse().getId() == id) {
				s.add(cr.getStudent());
			}
		}
		return s;
	}
<<<<<<< Backend/src/main/java/myProject/CourseController.java

	// Get teachers of course {id}
	@RequestMapping("/course/{id}/teachers")
	List<Teacher> getTeachers(@PathVariable Integer id) {
		List<Teacher> t = new ArrayList<Teacher>();
		List<TeacherCourse> list = teacherCourses.findAll();
		for (TeacherCourse tc : list) {
=======
	*/
	
	@Autowired
	TeacherClassesRepository teacherClasses;
	
	//Gets teachers of a class
	@RequestMapping("/course/{id}/teachers")
	List<Teacher> getTeachers(@PathVariable Integer id)	{
		List<Teacher> t = new ArrayList<Teacher>();
		List<TeacherClasses> list = teacherClasses.findAll();
		for (TeacherClasses tc : list) {
>>>>>>> Backend/src/main/java/myProject/CourseController.java
			if (tc.getCourse().getId() == id) {
				t.add(tc.getTeacher());
			}
		}
		return t;
<<<<<<< Backend/src/main/java/myProject/CourseController.java
=======
	}
	
	@Autowired
	AssignmentRepository assignments;
	
	@Autowired
	AssignedAssignmentRepository assignedAssignments;
	
	@RequestMapping("/course/{id}/assignments")
	List<Assignment> getCourseAssignments(@PathVariable Integer id)	{
		java.util.Set<Assignment> setAssignments = courses.findOne(id).getAssignments();
		List<Assignment> listAssignments = new ArrayList<Assignment>(setAssignments);
		return listAssignments;
>>>>>>> Backend/src/main/java/myProject/CourseController.java
	}

	// Course assignment mappings

	// Create assignment for course {id}
	@PostMapping("/course/{id}/assignment")
	Assignment createCourseAssignment(@PathVariable Integer id, @RequestBody Assignment assignment) {
		Assignment a = new Assignment(assignment);
		a.setCourse(courses.findOne(id));
		assignments.save(a);
		return a;
	}
<<<<<<< Backend/src/main/java/myProject/CourseController.java

	// Get assignments for course {id}
	@RequestMapping("/course/{id}/assignments")
	java.util.Set<Assignment> getCourseAssignments(@PathVariable Integer id) {
		return courses.getOne(id).getAssignments();
	}

	// Assign Assignment {assignment_id} to students in course {id}
	@PutMapping("/course/{id}/assign-assignment/{assignment_id}")
	List<AssignedAssignment> assignAllStudents(@PathVariable Integer id, @PathVariable Integer assignment_id) {
=======
	
	/*
	@PutMapping("/course/{id}/assignment/{assignment_id}/assign")
	List<AssignedAssignment> assignAllStudents(@PathVariable Integer id, @PathVariable Integer assignment_id)	{
>>>>>>> Backend/src/main/java/myProject/CourseController.java
		List<AssignedAssignment> aa = new ArrayList<AssignedAssignment>();
		List<Student> students = getStudents(id);// getStudent() method from above
		Assignment a = assignments.findOne(assignment_id);
		for (Student s : students) {
			assignedAssignments.save(new AssignedAssignment(s, a));
			aa.add(assignedAssignments.findOne((int) assignedAssignments.count())); // count() returns the number of entities (last pos)
		}
		return aa;
	}
<<<<<<< Backend/src/main/java/myProject/CourseController.java
=======
	*/
<<<<<<< Backend/src/main/java/myProject/CourseController.java

=======
	
>>>>>>> Backend/src/main/java/myProject/CourseController.java
>>>>>>> Backend/src/main/java/myProject/CourseController.java
}
