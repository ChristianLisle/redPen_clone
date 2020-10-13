package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherCourseController {
	@Autowired
	TeacherCourseRepository assignedCourses;

	@GetMapping("/teacherCourse/{id}")
	TeacherCourse getTeacherCourse(@PathVariable Integer id) {
		return assignedCourses.findOne(id);
	}

	@RequestMapping("/teacherCourses")
	List<TeacherCourse> all() {
		return assignedCourses.findAll();
	}

	@DeleteMapping("/teacherCourse/{id}")
	String deleteTeacherToClass(@PathVariable Integer id) {
		assignedCourses.delete(id);
		return "deleted teachers class" + assignedCourses.findOne(id).teacher.name + ":"
				+ assignedCourses.findOne(id).course.name;
	}

	@Autowired
	CourseRegistrationRepository registrar;

	// Get a list of students taking a course with a specific teacher (i.e. students taking 230 with Dr. Lutz)
	@RequestMapping("/teacherCourse/{id}/students")
	List<Student> getStudentsInTeacherCourse(@PathVariable Integer id) {
		List<Student> s = new ArrayList<Student>();
		List<CourseRegistration> list = registrar.findAll();
		for (CourseRegistration cr : list) {
			if (cr.getAssignedCourse().getId() == id) {
				s.add(cr.getStudent());
			}
		}
		return s;
	}

}
