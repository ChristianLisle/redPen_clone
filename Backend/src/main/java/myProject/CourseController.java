package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping("/course/{id}")
	Course getCourse(@PathVariable Integer id) {
		return courseRepository.findOne(id);
	}
	@RequestMapping("/courses")
	List<Course> hello() {
		return courseRepository.findAll();
	}
	
	@PostMapping("/course")
	Course createCourse(@RequestBody Course p) {
		courseRepository.save(p);
		return p;
	}
	
	
	/* putMapping not needed for any course data
	 * 
	 * @PutMapping("/course/{id}")
	Course updateCourse(@RequestBody Course p, @PathVariable Integer id) {
		Course old_p = courseRepository.findOne(id);
		old_p.setAge(p.getAge());
		courseRepository.save(old_p);
		return old_p;
	}*/
	
	@DeleteMapping("/course/{id}")
	String deleteCourse(@PathVariable Integer id) {
		courseRepository.delete(id);
		return "deleted " + id;
	}

}


