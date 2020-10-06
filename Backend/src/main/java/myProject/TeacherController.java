package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {
	@Autowired
	TeacherRepository teacher;

	@GetMapping("/teacher/{id}")
	Teacher getTeacher(@PathVariable Integer id) {
		return teacher.findOne(id);
	}
	
	@PostMapping("teacher")
	Teacher createTeacher(@RequestBody Teacher t) {
		teacher.save(t);
		return t;
	}

	@RequestMapping("/teacher") //maybe "/teachers"
	List<Teacher> all() {
		return teacher.findAll();
	}

	@DeleteMapping("/teacher/{id}")
	String deleteTeacher(@PathVariable Integer id) {
		teacher.delete(id);
		return "deleted teacher" + teacher.findOne(id).name;
	}	
	
	
	
	
	
	
	

	/*
	@Autowired
	CourseRepository courses;

	@Autowired
	CourseRegistrationRepository registrar;
	
	// Method for class registration
	@PutMapping("/student/{id}/register/{course_id}")
	CourseRegistration registerCourse(@PathVariable Integer id, @PathVariable Integer course_id) {
		Student student = students.findOne(id);
		Course course = courses.findOne(course_id);
		registrar.save(new CourseRegistration(student, course));
		return registrar.findOne((int) registrar.count()); // count() returns the number of entities (last pos)
	}

	// Method for getting all registrations performed by student
	@RequestMapping("/student/{id}/registrations")
	List<CourseRegistration> listRegistrations(@PathVariable Integer id) {
		List<CourseRegistration> s = new ArrayList<CourseRegistration>();
		List<CourseRegistration> list = registrar.findAll();
		for (CourseRegistration cr : list) {
			if (cr.getStudent().getId() == id) {
				s.add(cr);
			}
		}
		return s;
	}

	// Method for getting all courses for a specific student
	@RequestMapping("/student/{id}/courses")
	List<Course> listCourses(@PathVariable Integer id) {
		List<Course> s = new ArrayList<Course>();
		List<CourseRegistration> list = registrar.findAll();
		for (CourseRegistration cr : list) {
			if (cr.getStudent().getId() == id) {
				s.add(cr.getCourse());
			}
		}
		return s;
	}
	
	@Autowired
	AssignmentRepository assignments;
	
	@Autowired
	AssignedAssignmentRepository assignedAssignments;
	
	// Method assigns assignment to student
	@PutMapping("/student/{id}/assign/{assignment_id}")
	AssignedAssignment assignStudentAssignment(@PathVariable Integer id, @PathVariable Integer assignment_id) {
		Student student = students.findOne(id);
		Assignment assignment = assignments.findOne(assignment_id);
		assignedAssignments.save(new AssignedAssignment(student, assignment));
		return assignedAssignments.findOne((int) assignedAssignments.count()); // count() returns the number of entities (last pos)
	}
	
	// Method returns a list of assignments
	@RequestMapping("/student/{id}/assignments/overview")
	List<Assignment> listAssignments(@PathVariable Integer id)	{
		List<Assignment> a = new ArrayList<Assignment>();
		List<AssignedAssignment> list = assignedAssignments.findAll();
		for (AssignedAssignment aa : list) {
			if (aa.getStudent().getId() == id)	{
				a.add(aa.getAssignment());
			}
		}
		return a;
	}
	
	// Method returns a list of assignments with feedback/grade (assignedAssignment object)
	@RequestMapping("/student/{id}/report")
	List<AssignedAssignment> listAssignedAssignments(@PathVariable Integer id)	{
		List<AssignedAssignment> a = new ArrayList<AssignedAssignment>();
		List<AssignedAssignment> list = assignedAssignments.findAll();
		for (AssignedAssignment aa : list) {
			if (aa.getStudent().getId() == id)	{
				a.add(aa);
			}
		}
		return a;
	}
	
	// Method returns a single assignment overview (assignment object)
	@GetMapping("student/{id}/assignment/{assignment_id}/overview")
	Assignment getAssignment(@PathVariable Integer id, @PathVariable Integer assignment_id)	{
		Assignment a = assignments.findOne(assignment_id);
		return a;
	}
	
	// Method returns a single assignment with feedback and grades (assignedAssignment object)
	@GetMapping("student/{id}/assignment/{assignment_id}/report")
	AssignedAssignment getAssignedAssignment(@PathVariable Integer id, @PathVariable Integer assignment_id)	{
		List<AssignedAssignment> list = assignedAssignments.findAll();
		for (AssignedAssignment aa : list)	{
			if (aa.getAssignment().getId() == assignment_id)	{
				return aa;
			}
		}
		return null;
	}
	*/

}