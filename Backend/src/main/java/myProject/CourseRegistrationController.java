package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseRegistrationController {
	@Autowired
	CourseRegistrationRepository registrar;
	
	// Basic CouseRegistration mappings

	// get CourseRegistration {id}
	@GetMapping("/registration/{id}")
	CourseRegistration getCourseRegistration(@PathVariable Integer id) {
		return registrar.findOne(id);
	}

	// Get all CourseRegistrations
	@RequestMapping("/registrations")
	List<CourseRegistration> hello() {
		return registrar.findAll();
	}

	// update CourseRegistration {id}
	@PutMapping("/registration/{id}")
	CourseRegistration updateCourseRegistration(@RequestBody CourseRegistration s, @PathVariable Integer id) {
		CourseRegistration old_s = registrar.findOne(id);
		old_s.setGrade(s.getGrade());
		if (s.getCompleted())	{
			old_s.complete();
		}
		registrar.save(old_s);
		return old_s;
	}
	
	// Get CourseRegistration {id}'s grade
	@GetMapping("/registration/{id}/grade")
	Double getCourseRegistrationGrade(@PathVariable Integer id)	{
		return registrar.findOne(id).getGrade();
	}
	
	// Update CourseRegistration {id}'s grade
	@PutMapping("/registration/{id}/grade")
	CourseRegistration updateCourseRegistrationGrade(@PathVariable Integer id, @RequestBody Double grade)	{
		CourseRegistration old_cr = registrar.findOne(id);
		old_cr.setGrade(grade);
		registrar.save(old_cr);
		return old_cr;
	}
	
	// Complete CourseRegistration {id} (complete class and finalize grade)
	@PutMapping("/registration/{id}/complete")
	CourseRegistration completeCourse(@PathVariable Integer id)	{
		CourseRegistration old_cr = registrar.findOne(id);
		old_cr.complete();
		registrar.save(old_cr);
		return old_cr;
	}
	
	// Delete CourseRegistration {id}
	@DeleteMapping("/registration/{id}")
	String deleteCourseRegistration(@PathVariable Integer id) {
		registrar.delete(id);
		return "deleted " + id;
	}

}
