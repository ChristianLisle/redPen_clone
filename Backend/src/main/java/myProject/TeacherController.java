package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {
	@Autowired
	TeacherRepository teachers;
	
	// Teacher Login/Register mappings
	
	@PostMapping("/register-teacher")
	Teacher createTeacher(@RequestBody Teacher t) {
		teachers.save(t);
		return t;
	}
	
	@PostMapping("/login-teacher")
	String getTeacher(@RequestBody Teacher t)	{
		for (int i = 1; i < (int) teachers.count(); i++) {
			if (t.getName().equals((teachers.getOne(i)).getName()))	{
				if (t.getPassword().equals(teachers.getOne(i).getPassword()))	{
					return "" + teachers.getOne(i).getId();
				}
				else return "Incorrect password";
			}
		}
		return "There are no teachers with the name " + t.getName();
	}
	
	@PutMapping("/teacher/{id}/reset-password")
	String resetPassword(@RequestBody NewPassword np, @PathVariable Integer id)	{
		Teacher old_t = teachers.findOne(id);
		if (old_t.resetPassword(np.getOldPassword(), np.getNewPassword()))	{
			teachers.save(old_t);
			return "Password reset successfuly.";
		}
		return "Password not reset.";
	}

	// Basic teacher info mappings
	
	@GetMapping("/teacher/{id}")
	Teacher getTeacher(@PathVariable Integer id) {
		return teachers.findOne(id);
	}
	
	@RequestMapping("/teachers")
	List<Teacher> getAllTeachers() {
		return teachers.findAll();
	}

	@DeleteMapping("/teacher/{id}")
	String deleteTeacher(@PathVariable Integer id) {
		teachers.delete(id);
		return "deleted teacher" + teachers.findOne(id).name;
	}	
	
	@Autowired
	CourseRepository courses;
	
	@Autowired
	TeacherCourseRepository assignedCourses;
	
	// Teacher course mappings
	
	//For assigning a course to a teacher
	//The class must have already been created. This just links them
	@PutMapping("/teacher/{id}/assign-course/{course_id}")
	TeacherCourse addTeacher(@PathVariable Integer id, @PathVariable Integer course_id) {
		Teacher teacher = teachers.findOne(id);					//Gets teacher
		Course course = courses.findOne(course_id);				//Gets class
		assignedCourses.save(new TeacherCourse(teacher, course));		//Creates new row we want
		return assignedCourses.findOne((int) assignedCourses.count());			//Returns the last item in that tables (the newest one added)
	}
	
	//Gets all courses a teacher is in
	//Shows all courses that this teacher teaches
	@RequestMapping("/teacher/{id}/courses")
	List<Course> allTeachersCourses(@PathVariable Integer id) {
		List<Course> returned = new ArrayList<Course>();
		List<TeacherCourse> allClasses = assignedCourses.findAll();
		for (TeacherCourse tc : allClasses) {
			if (tc.getTeacher().getId() == id) {
				returned.add(tc.getCourse());
			}
		}
		return returned;
	}
}