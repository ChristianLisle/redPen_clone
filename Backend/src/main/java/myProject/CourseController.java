package myProject;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {
	@Autowired
	CourseRepository courses;
	
	@GetMapping("/course/{id}")
	Course getCourse(@PathVariable Integer id)	{
		return courses.findOne(id);
	}
	
	@RequestMapping("/courses")
	List<Course> hello()	{
		return courses.findAll();
	}
	
	@PostMapping("course")
	Course createCourse(@RequestBody Course s)	{
		courses.save(s);
		return s;
	}
	
	@PutMapping("/course/{id}/update")
	Course updateCourse(@RequestBody Course c, @PathVariable Integer id)	{
		Course old_c = courses.findOne(id);
		old_c.setName(c.getName());
		old_c.setCourseDescription(c.getCourseDescription());
		courses.save(old_c);
		return old_c;
	}
	
	
	@DeleteMapping("/course/{id}")
	String deleteCourse(@PathVariable Integer id)	{
		courses.delete(id);
		return "deleted " + id;
	}
	
	@Autowired
	CourseRegistrationRepository registrar;
	
	@RequestMapping("/course/{id}/students")
	List<Student> getStudents(@PathVariable Integer id)	{
		List<Student> c = new ArrayList<Student>();
		List<CourseRegistration> list = registrar.findAll();
		for (CourseRegistration cr : list) {
			if (cr.getCourse().getId() == id) {
				c.add(cr.getStudent());
			}
		}
		return c;
	}
	
	@Autowired
	AssignmentRepository assignments;
	
	@Autowired
	AssignedAssignmentRepository assignedAssignments;
	
	@PostMapping("/course/{id}/assignment")
	Assignment createCourseAssignment(@PathVariable Integer id, @RequestBody Assignment assignment)	{
		Assignment a = new Assignment(assignment);
		a.setCourse(courses.findOne(id));
		assignments.save(a);
		return a;
	}
	
	@RequestMapping("/course/{id}/assignments")
	List<Assignment> getCourseAssignments(@PathVariable Integer id)	{
		java.util.Set<Assignment> setAssignments = courses.findOne(id).getAssignments();
		List<Assignment> listAssignments = new ArrayList<Assignment>(setAssignments);
		return listAssignments;
	}
	
	@PutMapping("/course/{id}/assignment/{assignment_id}/assign")
	List<AssignedAssignment> assignAllStudents(@PathVariable Integer id, @PathVariable Integer assignment_id)	{
		List<AssignedAssignment> aa = new ArrayList<AssignedAssignment>();
		List<Student> students = getStudents(id);
		for (Student s : students) {
			assignedAssignments.save(new AssignedAssignment(s, assignments.findOne(assignment_id)));
			aa.add(assignedAssignments.findOne((int) assignedAssignments.count())); // count() returns the number of entities (last pos)
		}
		return aa;
	}
	
	
}
