package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "TeacherCourseController")
@RestController
public class TeacherCourseController {
	@Autowired
	TeacherCourseRepository assignedCourses;

	/**
	 * Gets the teacher course that matches that mapping
	 * 
	 * @param id
	 * @return assignedCourses.findOne(id)
	 */
	@ApiOperation(value = "Returns the teacher course of the id {id}")
	@RequestMapping(method = RequestMethod.GET, path ="/teacherCourse/{id}")
	TeacherCourse getTeacherCourse(@PathVariable Integer id) {
		return assignedCourses.findOne(id);
	}

	/**
	 * Returns all of the teacher courses that exist
	 * 
	 * @return assignedCourses.findAll()
	 */
	@ApiOperation(value = "Returns all of the teacher courses that exist")
	@RequestMapping(method = RequestMethod.GET, path ="/teacherCourses")
	List<TeacherCourse> all() {
		return assignedCourses.findAll();
	}

	/**
	 * Deletes a teacher course
	 * 
	 * @param id
	 * @return "deleted teachers class" + assignedCourses.findOne(id).teacher.name + ":"+ assignedCourses.findOne(id).course.name
	 */
	@ApiOperation(value = "Deletes a teacher course")
	@RequestMapping(method = RequestMethod.DELETE, path ="/teacherCourse/{id}")
	String deleteTeacherToClass(@PathVariable Integer id) {
		assignedCourses.delete(id);
		return "deleted teachers class" + assignedCourses.findOne(id).teacher.name + ":"
				+ assignedCourses.findOne(id).course.name;
	}

	@Autowired
	CourseRegistrationRepository registrar;

	/**
	 * Gets a list of all students taking a course with a specific teacher
	 * 
	 * @param id
	 * @return s
	 */
	@ApiOperation(value = "Gets a list of all students taking a course with a specific teacher")
	@RequestMapping(method = RequestMethod.GET, path ="/teacherCourse/{id}/students")
	List<Student> getStudentsInTeacherCourse(@PathVariable Integer id) {
		List<Student> s = new ArrayList<Student>();
		List<CourseRegistration> list = registrar.findAll();
		for (CourseRegistration cr : list) {
			if (cr.getAssignedCourse().getId() == id) {
				s.add(cr.getStudent());
			}
		}
		return s;
	}

}
