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
		int j = (int) teachers.count(); // count() method does not include the number of deleted entities (this causes issues when iterating over id with deleted entity)
		for (int i = 1; i <= j; i++) {
			if (!teachers.exists(i)) j++; // deleted entity id found. Increment number of iterations to make up for this.
			else	{
				if (t.getName().equals((teachers.getOne(i)).getName()))	{
					if (t.getPassword().equals(teachers.getOne(i).getPassword()))	{
						return "" + teachers.getOne(i).getId();
					}
					else return "Incorrect password";
				}
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
		String name = teachers.findOne(id).getName();
		teachers.delete(id);
		return "deleted teacher " + name;
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
	
	@Autowired
	PTInboxRepository ptinbox;
	
	@Autowired
	PTMessagesRepository ptmessage;
	
	//Gets all conversations a teacher has between parents
	@RequestMapping("/teacher/{id}/ptinbox")
	List<PTInbox> teacherParentInbox(@PathVariable Integer id) {
		List<PTInbox> pti = new ArrayList<PTInbox>();
		List<PTInbox> list = ptinbox.findAll();
		for (PTInbox pt : list) {
			if (pt.teacher.id == id) {
				pti.add(pt);
			}
		}
		return pti;
	}
	
	//Gets all the PTMessages between a parent and teacher in an inbox
	@GetMapping("teacher/{id}/ptinbox/{pid}/messages")
	List<PTMessages> teacherParentInboxMessages(@PathVariable Integer id, @PathVariable Integer pid) {
		List<PTMessages> ptm = new ArrayList<PTMessages>();
		List<PTMessages> list = ptmessage.findAll();
		for (PTMessages pt : list) {
			if (pt.ptinbox.teacher.id == id && pt.ptinbox.id == pid) {
				ptm.add(pt);
			}
		}
		return ptm;
	}
	
	//Gets all the messages between a parent and a teacher in an inbox
	@GetMapping("teacher/{id}/ptinbox/{pid}/messagesOnly")
	List<String> teacherParentInboxMessagesOnly(@PathVariable Integer id, @PathVariable Integer pid) {
		List<String> ptm = new ArrayList<String>();
		List<PTMessages> list = ptmessage.findAll();
		for (PTMessages pt : list) {
			if (pt.ptinbox.teacher.id == id && pt.ptinbox.id == pid) {
				ptm.add(pt.message);
			}
		}
		return ptm;
	}
	
	//Gets all the messages between a parent and a teacher in an inbox
	@GetMapping("teacher/{id}/ptinbox/{pid}/messagesSender")
	List<String> teacherParentInboxMessagesSender(@PathVariable Integer id, @PathVariable Integer pid) {
		List<String> ptm = new ArrayList<String>();
		List<PTMessages> list = ptmessage.findAll();
		for (PTMessages pt : list) {
			if (pt.ptinbox.teacher.id == id && pt.ptinbox.id == pid) {
				ptm.add(pt.sender);
			}
		}
		return ptm;
	}
	
	@Autowired
	ParentRepository parents;

	//Creates a ptinbox
	@PostMapping("/teacher/{id}/makePTI/{pid}/titled/{subject}")
	PTInbox createPTInbox(@PathVariable Integer id, @PathVariable Integer pid, @PathVariable String subject) {
		PTInbox pt = new PTInbox(parents.findOne(pid), teachers.findOne(id), subject);
		ptinbox.save(pt);
		return pt;
	}
	
	//Creates a ptmessage
	@PostMapping("/teacher/{id}/makePTM/{pid}/message/{message}")
	PTMessages createPTMessages(@PathVariable Integer id, @PathVariable Integer pid, @PathVariable String message) {
		//pid is the id of a ptinbox
		PTMessages ptm = new PTMessages(ptinbox.findOne(pid), ptinbox.findOne(pid).subject, teachers.findOne(id).name, message);
		ptmessage.save(ptm);
		return ptm;
	}
	
	@Autowired
	STInboxRepository stinbox;
	
	@Autowired
	STMessagesRepository stmessage;
	
	//Gets all conversations a teacher has between parents
	@RequestMapping("/teacher/{id}/stinbox")
	List<STInbox> teacherStudentInbox(@PathVariable Integer id) {
		List<STInbox> sti = new ArrayList<STInbox>();
		List<STInbox> list = stinbox.findAll();
		for (STInbox st : list) {
			if (st.teacher.id == id) {
				sti.add(st);
			}
		}
		return sti;
	}
	
	//Gets all the PTMessages between a parent and teacher in an inbox
	@GetMapping("teacher/{id}/stinbox/{pid}/messages")
	List<STMessages> teacherStudentInboxMessages(@PathVariable Integer id, @PathVariable Integer pid) {
		List<STMessages> stm = new ArrayList<STMessages>();
		List<STMessages> list = stmessage.findAll();
		for (STMessages st : list) {
			if (st.stinbox.teacher.id == id && st.stinbox.id == pid) {
				stm.add(st);
			}
		}
		return stm;
	}
	
	//Gets all the messages between a parent and a teacher in an inbox
	@GetMapping("teacher/{id}/stinbox/{pid}/messagesOnly")
	List<String> teacherStudentInboxMessagesOnly(@PathVariable Integer id, @PathVariable Integer pid) {
		List<String> stm = new ArrayList<String>();
		List<STMessages> list = stmessage.findAll();
		for (STMessages st : list) {
			if (st.stinbox.teacher.id == id && st.stinbox.id == pid) {
				stm.add(st.message);
			}
		}
		return stm;
	}
	
	//Gets all the messages between a parent and a teacher in an inbox
	@GetMapping("teacher/{id}/stinbox/{pid}/messagesSender")
	List<String> teacherStudentInboxMessagesSender(@PathVariable Integer id, @PathVariable Integer pid) {
		List<String> stm = new ArrayList<String>();
		List<STMessages> list = stmessage.findAll();
		for (STMessages st : list) {
			if (st.stinbox.teacher.id == id && st.stinbox.id == pid) {
				stm.add(st.sender);
			}
		}
		return stm;
	}
	
	@Autowired
	StudentRepository students;

	//Creates a stinbox
	@PostMapping("/teacher/{id}/makeSTI/{sid}/titled/{subject}")
	STInbox createSTInbox(@PathVariable Integer id, @PathVariable Integer sid, @PathVariable String subject) {
		STInbox st = new STInbox(students.findOne(sid), teachers.findOne(id), subject);
		stinbox.save(st);
		return st;
	}
	
	//Creates a stmessage
	@PostMapping("/teacher/{id}/makeSTM/{sid}/message/{message}")
	STMessages createSTMessages(@PathVariable Integer id, @PathVariable Integer sid, @PathVariable String message) {
		//sid is the id of a stinbox
		STMessages stm = new STMessages(stinbox.findOne(sid), stinbox.findOne(sid).subject, teachers.findOne(id).name, message);
		stmessage.save(stm);
		return stm;
	}
	
}