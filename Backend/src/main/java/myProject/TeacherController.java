package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {
	@Autowired
	TeacherRepository teacher;

	@GetMapping("/teacher/{id}")
	Teacher getTeacher(@PathVariable Integer id) {
		return teacher.findOne(id);
	}
	
	@PostMapping("teacher")
	Teacher createTeacher(@RequestBody Teacher t) {
		teacher.save(t);
		return t;
	}

	@RequestMapping("/teachers") //maybe "/teacher"
	List<Teacher> all() {
		return teacher.findAll();
	}

	@DeleteMapping("/teacher/{id}")
	String deleteTeacher(@PathVariable Integer id) {
		teacher.delete(id);
		return "deleted teacher" + teacher.findOne(id).name;
	}	
	
	@Autowired
	CourseRepository courses;
	
	@Autowired
	TeacherClassesRepository classes;
	
	//For adding a class to a teacher
	//The class must have already been created. This just links them
	@PutMapping("/teacher/{id}/addClass/{class_id}")
	TeacherClasses addTeacher(@PathVariable Integer id, @PathVariable Integer class_id) {
		Teacher teachers = teacher.findOne(id);					//Gets teacher
		Course course = courses.findOne(class_id);				//Gets class
		classes.save(new TeacherClasses(teachers, course));		//Creates new row we want
		return classes.findOne((int) classes.count());			//Returns the last item in that tables (the newest one added)
	}
	
	//Gets all courses a teacher is in
	//Shows all courses that this teacher teaches
	@RequestMapping("/teacher/{id}/courses")
	List<Course> allTeachersCourses(@PathVariable Integer id) {
		List<Course> returned = new ArrayList<Course>();
		List<TeacherClasses> allClasses = classes.findAll();
		for (TeacherClasses tc : allClasses) {
			if (tc.getTeacher().getId() == id) {
				returned.add(tc.getCourse());
			}
		}
		return returned;
	}
	
	//Gets all teacherClasses the teacher is in
	//Shows all items of assigned_classes that include this teacher
	@RequestMapping("/teacher/{id}/teacherClasses")
	List<TeacherClasses> allTeachersClasses(@PathVariable Integer id) {
		List<TeacherClasses> returned = new ArrayList<TeacherClasses>();
		List<TeacherClasses> allClasses = classes.findAll();
		for (TeacherClasses tc : allClasses) {
			if (tc.getTeacher().getId() == id) {
				returned.add(tc);
			}
		}
		return returned;
	}

}