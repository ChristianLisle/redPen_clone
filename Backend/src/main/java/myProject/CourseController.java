package myProject;

import java.util.List;

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
	
	
	@PutMapping("/course/{id}")
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
	
}
