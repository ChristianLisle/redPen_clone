package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import myProject.Course;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@GetMapping("/student/{id}")
	Student getStudent(@PathVariable Integer id) {
		return studentRepository.findOne(id);
	}

	@RequestMapping("/students")
	List<Student> hello() {
		return studentRepository.findAll();
	}

	/*
	 * Get the list of all courses that are under a particular student. This confirms the
	 * One-to-Many mapping
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/students/{id}")
	public List<Course> getCoursesForaStudent(@PathVariable(name = "id") int id) {
		if (studentRepository.findOne(id) != null)
			return studentRepository.findOne(id).getCourses();
		else
			return new ArrayList<>();
	}

	@PostMapping("/student")
	Student createStudent(@RequestBody Student p) {
		studentRepository.save(p);
		return p;
	}

	@PutMapping("/student/{id}")
	Student updateStudent(@RequestBody Student p, @PathVariable Integer id) {
		Student old_p = studentRepository.findOne(id);
		old_p.setAge(p.getAge());
		studentRepository.save(old_p);
		return old_p;
	}

	@DeleteMapping("/student/{id}")
	String deleteStudent(@PathVariable Integer id) {
		studentRepository.delete(id);
		return "deleted " + id;
	}

}
