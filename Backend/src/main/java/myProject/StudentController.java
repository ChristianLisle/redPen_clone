package myProject;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
	@Autowired
	StudentRepository students;
	
	// Student Login/Register mappings
	
	@PostMapping("/register-student")
	Student createStudent(@RequestBody Student s) {
		students.save(s);
		return s;
	}
	
	@PostMapping("/login-student")
	String getStudent(@RequestBody Student s)	{
		int j = (int) students.count(); // count() method does not include the number of deleted entities (this causes issues when iterating over id with deleted entity)
		for (int i = 1; i <= (int) j; i++) {
			if (!students.exists(i)) j++; // deleted entity id found. Increment number of iterations to make up for this.
			else	{
				if (s.getName().equals((students.getOne(i)).getName()))	{
					if (s.getPassword().equals(students.getOne(i).getPassword()))	{
						return "" + students.getOne(i).getId();
					}
					else return "Incorrect password";
				}
			}
		}
		return "There are no students with the name " + s.getName();
	}
	
	@PutMapping("/student/{id}/reset-password")
	String resetPassword(@RequestBody NewPassword np, @PathVariable Integer id)	{
		Student old_s = students.findOne(id);
		if (old_s.resetPassword(np.getOldPassword(), np.getNewPassword()))	{
			students.save(old_s);
			return "Password reset successfuly.";
		}
		return "Password not reset.";
	}
	
	// Basic student info mappings
	
	@GetMapping("/student/{id}")
	Student getStudent(@PathVariable Integer id) {
		return students.findOne(id);
	}

	@RequestMapping("/students")
	List<Student> getAllStudents() {
		return students.findAll();
	}
	
	@DeleteMapping("/student/{id}")
	String deleteStudent(@PathVariable Integer id) {
		String name = students.findOne(id).getName();
		students.delete(id);
		return "deleted student " + name;
	}

	@Autowired
	CourseRepository courses;

	@Autowired
	CourseRegistrationRepository registrar;
	
	@Autowired
	TeacherCourseRepository assignedCourses;
	

	// Students and courses relationship mappings
	
	// Method for course registration
	@PutMapping("/student/{id}/register/{assignedCourse_id}")
	CourseRegistration registerCourse(@PathVariable Integer id, @PathVariable Integer assignedCourse_id) {
		Student student = students.findOne(id);
		TeacherCourse assignedCourse = assignedCourses.findOne(assignedCourse_id);
		registrar.save(new CourseRegistration(student, assignedCourse));
		return registrar.findOne((int) registrar.count()); // count() returns the number of entities (last pos)
	}

	// Method for getting all registrations for a student
	@RequestMapping("/student/{id}/registrations")
	java.util.Set<CourseRegistration> listRegistrations(@PathVariable Integer id) {
		return students.findOne(id).getCourseRegistrations();
	}

	// Method for getting all courses for a specific student
	@RequestMapping("/student/{id}/courses")
	java.util.Set<Course> listCourses(@PathVariable Integer id) {
		return students.findOne(id).getCourses();
	}
	
	@Autowired
	AssignmentRepository assignments;
	
	@Autowired
	AssignedAssignmentRepository assignedAssignments;
	
	// Students and assigned assignments relationship mappings
	
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
	java.util.Set<Assignment> listAssignments(@PathVariable Integer id)	{
		return students.getOne(id).getAssignments();
	}
	
	// Method returns a list of assignments with feedback/grade (assignedAssignment object)
	@RequestMapping("/student/{id}/assignments/report")
	java.util.Set<AssignedAssignment> listAssignedAssignments(@PathVariable Integer id)	{
		return students.getOne(id).getAssignedAssignments();
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
	
	
	// Need methods for assigning student to a parent
}

