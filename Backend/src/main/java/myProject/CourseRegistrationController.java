package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CourseRegistrationController")
@RestController
public class CourseRegistrationController {
	@Autowired
	CourseRegistrationRepository registrar;
	
	// Basic CouseRegistration mappings

	@ApiOperation(value = "get CourseRegistration {id}")
	@RequestMapping(method = RequestMethod.GET, path = "/registration/{id}")
	CourseRegistration getCourseRegistration(@PathVariable Integer id) {
		return registrar.findOne(id);
	}

	@ApiOperation(value = "Get all CourseRegistrations")
	@RequestMapping(method = RequestMethod.GET, path = "/registrations")
	List<CourseRegistration> hello() {
		return registrar.findAll();
	}

	@ApiOperation(value = "update CourseRegistration {id}")
	@RequestMapping(method = RequestMethod.PUT, path = "/registration/{id}")
	CourseRegistration updateCourseRegistration(@RequestBody CourseRegistration s, @PathVariable Integer id) {
		CourseRegistration old_s = registrar.findOne(id);
		old_s.setGrade(s.getGrade());
		if (s.getCompleted())	{
			old_s.complete();
		}
		registrar.save(old_s);
		return old_s;
	}
	
	@ApiOperation(value = "Get CourseRegistration {id}'s grade")
	@RequestMapping(method = RequestMethod.GET, path = "/registration/{id}/grade")
	Double getCourseRegistrationGrade(@PathVariable Integer id)	{
		return registrar.findOne(id).getGrade();
	}
	
	@ApiOperation(value = "Update CourseRegistration {id}'s grade")
	@RequestMapping(method = RequestMethod.PUT, path = "/registration/{id}/grade")
	CourseRegistration updateCourseRegistrationGrade(@PathVariable Integer id, @RequestBody Double grade)	{
		CourseRegistration old_cr = registrar.findOne(id);
		old_cr.setGrade(grade);
		registrar.save(old_cr);
		return old_cr;
	}
	
	@ApiOperation(value = "Complete CourseRegistration {id} (complete class and finalize grade)")
	@RequestMapping(method = RequestMethod.PUT, path = "/registration/{id}/complete")
	CourseRegistration completeCourse(@PathVariable Integer id)	{
		CourseRegistration old_cr = registrar.findOne(id);
		old_cr.complete();
		registrar.save(old_cr);
		return old_cr;
	}
	
	@ApiOperation(value = "Delete CourseRegistration {id}")
	@RequestMapping(method = RequestMethod.DELETE, path = "/registration/{id}")
	String deleteCourseRegistration(@PathVariable Integer id) {
		registrar.delete(id);
		return "deleted " + id;
	}

}
