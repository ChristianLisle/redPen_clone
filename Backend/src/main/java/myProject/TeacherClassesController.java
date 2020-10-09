package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherClassesController {
	@Autowired
	TeacherClassesRepository teacherClasses;
	
	@GetMapping("/teacherToClass/{id}")
	TeacherClasses getTeacherClasses(@PathVariable Integer id)	{
		return teacherClasses.findOne(id);
	}
	
	@RequestMapping("/teacherToClass")
	List<TeacherClasses> all()	{
		return teacherClasses.findAll();
	}
	
	@DeleteMapping("/teacherToClass/{id}")
	String deleteTeacherToClass(@PathVariable Integer id)	{
		teacherClasses.delete(id);
		return "deleted teachers class" + teacherClasses.findOne(id).teacher.name + ":" + teacherClasses.findOne(id).course.name;
	}
	
}
