package myProject;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CourseController")
@RestController
public class CourseController {
	@Autowired
	CourseRepository courses;

	@Autowired
	AssignmentRepository assignments;

	@Autowired
	AssignedAssignmentRepository assignedAssignments;

	// Basic Course mappings

	@ApiOperation(value = "Get course {id}")
	@RequestMapping(method = RequestMethod.GET, path = "/course/{id}")
	Course getCourse(@PathVariable Integer id) {
		return courses.findOne(id);
	}

	@ApiOperation(value = "Get all courses")
	@RequestMapping(method = RequestMethod.GET, path = "/courses")
	List<Course> hello() {
		return courses.findAll();
	}

	@ApiOperation(value = "Create a new course")
	@RequestMapping(method = RequestMethod.POST, path = "course")
	Course createCourse(@RequestBody Course s) {
		courses.save(s);
		return s;
	}

	@ApiOperation(value = "Update course {id}")
	@RequestMapping(method = RequestMethod.PUT, path = "/course/{id}/update")
	Course updateCourse(@RequestBody Course c, @PathVariable Integer id) {
		Course old_c = courses.findOne(id);
		old_c.setName(c.getName());
		old_c.setCourseDescription(c.getCourseDescription());
		courses.save(old_c);
		return old_c;
	}

	@ApiOperation(value = "Delete course {id}")
	@RequestMapping(method = RequestMethod.DELETE, path = "/course/{id}")
	String deleteCourse(@PathVariable Integer id) {
		courses.delete(id);
		return "deleted " + id;
	}

	@Autowired
	CourseRegistrationRepository registrar;

	@Autowired
	TeacherCourseRepository teacherCourses;

	// Course student and teacher mappings

	@ApiOperation(value = "Get students taking course {id} (teacher is irrelevant)")
	@RequestMapping(method = RequestMethod.GET, path = "/course/{id}/students")
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

	@ApiOperation(value = "Get teachers of course {id}")
	@RequestMapping(method = RequestMethod.GET, path = "/course/{id}/teachers")
	List<Teacher> getTeachers(@PathVariable Integer id) {
		List<Teacher> t = new ArrayList<Teacher>();
		List<TeacherCourse> list = teacherCourses.findAll();
		for (TeacherCourse tc : list) {
			if (tc.getCourse().getId() == id) {
				t.add(tc.getTeacher());
			}
		}
		return t;
	}

	// Course assignment mappings

	@ApiOperation(value = "Create assignment for course {id}")
	@RequestMapping(method = RequestMethod.POST, path = "/course/{id}/assignment")
	Assignment createCourseAssignment(@PathVariable Integer id, @RequestBody Assignment assignment) {
		Assignment a = new Assignment(assignment);
		a.setCourse(courses.findOne(id));
		assignments.save(a);
		return a;
	}

	@ApiOperation(value = "Get assignments for course {id}")
	@RequestMapping(method = RequestMethod.GET, path = "/course/{id}/assignments")
	java.util.Set<Assignment> getCourseAssignments(@PathVariable Integer id) {
		return courses.getOne(id).getAssignments();
	}

	@ApiOperation(value = "Assign Assignment {assignment_id} to students in course {id}")
	@RequestMapping(method = RequestMethod.PUT, path = "/course/{id}/assign-assignment/{assignment_id}")
	List<AssignedAssignment> assignAllStudents(@PathVariable Integer id, @PathVariable Integer assignment_id) {
		List<AssignedAssignment> aa = new ArrayList<AssignedAssignment>();
		List<Student> students = getStudents(id);// getStudent() method from above
		Assignment a = assignments.findOne(assignment_id);
		for (Student s : students) {
			assignedAssignments.save(new AssignedAssignment(s, a));
			aa.add(assignedAssignments.findOne((int) assignedAssignments.count())); // count() returns the number of entities (last pos)
		}
		return aa;
	}
}
