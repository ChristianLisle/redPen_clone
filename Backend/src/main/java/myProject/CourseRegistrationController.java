package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseRegistrationController {
	@Autowired
	CourseRegistrationRepository registrations;
	
	@GetMapping("/registration/{id}")
	CourseRegistration getCourseRegistration(@PathVariable Integer id)	{
		return registrations.findOne(id);
	}
	
	@RequestMapping("/registrations")
	List<CourseRegistration> hello()	{
		return registrations.findAll();
	}
	
	
	/*@PutMapping("/registration/{id}")
	CourseRegistration updateCourseRegistration(@RequestBody CourseRegistration s, @PathVariable Integer id)	{
		CourseRegistration old_s = registrations.findOne(id);
		old_s.setName(s.getName());
		registrations.save(old_s);
		return old_s;
	}*/
	
	
	@DeleteMapping("/registration/{id}")
	String deleteCourseRegistration(@PathVariable Integer id)	{
		registrations.delete(id);
		return "deleted " + id;
	}
	
}
