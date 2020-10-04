package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
	@Autowired
	StudentRepository students;

	@GetMapping("/student/{id}")
	Student getStudent(@PathVariable Integer id) {
		return students.findOne(id);
	}

	@RequestMapping("/students")
	List<Student> hello() {
		return students.findAll();
	}

	@PostMapping("student")
	Student createStudent(@RequestBody Student s) {
		students.save(s);
		return s;
	}

	@PutMapping("/student/{id}")
	Student updateStudent(@RequestBody Student s, @PathVariable Integer id) {
		Student old_s = students.findOne(id);
		old_s.setName(s.getName());
		students.save(old_s);
		return old_s;
	}

	@DeleteMapping("/student/{id}")
	String deleteStudent(@PathVariable Integer id) {
		students.delete(id);
		return "deleted " + id;
	}

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
		return registrar.findOne((int) registrar.count()); // Make sure that this will always return an id of the last
															// element added (in the line above)
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

}