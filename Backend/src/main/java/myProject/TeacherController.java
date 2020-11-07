package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "TeacherController")
@RestController
public class TeacherController {
	@Autowired
	TeacherRepository teachers;
	
	// Teacher Login/Register mappings

	/**
	 * A post mapping to register/add a teacher
	 * 
	 * @param t
	 * @return t
	 */
	@ApiOperation(value = "To register/add a teacher")
	@RequestMapping(method = RequestMethod.POST, path ="/register-teacher")
	Teacher createTeacher(@RequestBody Teacher t) {
		teachers.save(t);
		return t;
	}
	
	/**
	 * A post mapping that logs in the teacher
	 * 
	 * @param t
	 * @return
	 */
	@ApiOperation(value = "Logs in the teacher")
	@RequestMapping(method = RequestMethod.POST, path ="/login-teacher")
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
	
	/**
	 * A put mapping to change the password of a teacher
	 * 
	 * @param np
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Changes the password of a teacher")
	@RequestMapping(method = RequestMethod.PUT, path ="/teacher/{id}/reset-password")
	String resetPassword(@RequestBody NewPassword np, @PathVariable Integer id)	{
		Teacher old_t = teachers.findOne(id);
		if (old_t.resetPassword(np.getOldPassword(), np.getNewPassword()))	{
			teachers.save(old_t);
			return "Password reset successfuly.";
		}
		return "Password not reset.";
	}

	// Basic teacher info mappings
	
	/**
	 * A get mapping that returns a specific teacher whose id matches the {id}
	 * 
	 * @param id
	 * @return teachers.findOne(id)
	 */
	@ApiOperation(value = "Returns a specific teacher whose id matches the {id}")
	@RequestMapping(method = RequestMethod.GET, path ="/teacher/{id}")
	Teacher getTeacher(@PathVariable Integer id) {
		return teachers.findOne(id);
	}
	
	/**
	 * A request mapping that returns all teachers on record as a list
	 * 
	 * @return teachers.findAll();
	 */
	@ApiOperation(value = "Returns all teachers on record as a list")
	@RequestMapping(method = RequestMethod.GET, path ="/teachers")
	List<Teacher> getAllTeachers() {
		return teachers.findAll();
	}

	/**
	 * A delete mapping that deletes a specific teacher based on the {id} and returns a string saying it delete a teacher with 
	 * the specific teachers name
	 * 
	 * @param id
	 * @return "deleted teacher " + name
	 */
	@ApiOperation(value = "Deletes a specific teacher based on the {id} and returns a string of the teachers name")
	@RequestMapping(method = RequestMethod.DELETE, path ="/teacher/{id}")
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
	
	/**
	 * A put mapping to assign a course {course_id} to a teacher {id}. The course must have been created first. 
	 * This just links them together
	 * 
	 * @param id
	 * @param course_id
	 * @return assignedCourses.findOne((int) assignedCourses.count())
	 */
	@ApiOperation(value = "Assign a course {course_id} to a teacher {id}. The course must have been created first.")
	@RequestMapping(method = RequestMethod.PUT, path ="/teacher/{id}/assign-course/{course_id}")
	TeacherCourse addTeacher(@PathVariable Integer id, @PathVariable Integer course_id) {
		Teacher teacher = teachers.findOne(id);					//Gets teacher
		Course course = courses.findOne(course_id);				//Gets class
		assignedCourses.save(new TeacherCourse(teacher, course));		//Creates new row we want
		return assignedCourses.findOne((int) assignedCourses.count());			//Returns the last item in that tables (the newest one added)
	}
	
	/**
	 * A request mapping that gets all the courses for a specific teacher {id}
	 * 
	 * @param id
	 * @return returned
	 */
	@ApiOperation(value = "Gets all the courses for a specific teacher {id}")
	@RequestMapping(method = RequestMethod.GET, path ="/teacher/{id}/courses")
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
	
	/**
	 * Gets all the conversations between a teacher and parents
	 * 
	 * @param id
	 * @return pti
	 */
	@ApiOperation(value = "Gets all the conversations between a teacher and parents")
	@RequestMapping(method = RequestMethod.GET, path ="/teacher/{id}/ptinbox")
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
	
	/**
	 * Gets all the PTMessages between a teacher {id} and parent in a specific PTInbox {pid}
	 * 
	 * @param id
	 * @param pid
	 * @return ptm
	 */
	@ApiOperation(value = "Gets all the PTMessages between a teacher {id} and parent in a specific PTInbox {pid}")
	@RequestMapping(method = RequestMethod.GET, path ="teacher/{id}/ptinbox/{pid}")
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
	

	/**
	 * Gets all the messages between a teacher {id} and parent in a  specific PTInbox {pid}.
	 * This returns an arraylist of strings of all the messages in the PTMessages
	 * 
	 * @param id
	 * @param pid
	 * @return ptm
	 */
	@ApiOperation(value = "Gets all the messages between a teacher {id} and parent in a  specific PTInbox {pid}.")
	@RequestMapping(method = RequestMethod.GET, path ="teacher/{id}/ptinbox/{pid}/messages")
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
	
	/**
	 * Gets all the senders between a teacher {id} and a parent in a specific PTInbox
	 * This returns an arraylist of strings of all the senders in the PTMessages
	 * 
	 * @param id
	 * @param pid
	 * @return ptm
	 */
	@ApiOperation(value = "Gets all the senders between a teacher {id} and a parent in a specific PTInbox")
	@RequestMapping(method = RequestMethod.GET, path ="teacher/{id}/ptinbox/{pid}/senders")
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

	/**
	 * Creates a PTInbox between a teacher {id} and a parent {pid} with the subject {subject}
	 * 
	 * @param id
	 * @param pid
	 * @param subject
	 * @return pt
	 */
	@ApiOperation(value = "Creates a PTInbox between a teacher {id} and a parent {pid} with the subject {subject}")
	@RequestMapping(method = RequestMethod.POST, path ="/teacher/{id}/makePTI/{pid}/titled/{subject}")
	PTInbox createPTInbox(@PathVariable Integer id, @PathVariable Integer pid, @PathVariable String subject) {
		PTInbox pt = new PTInbox(parents.findOne(pid), teachers.findOne(id), subject);
		ptinbox.save(pt);
		return pt;
	}
	
	/**
	 * Creates a PTMessage between a teacher {id} and a parent with the subject of the message {subject}
	 * This is based off of a PTInbox {pid}
	 * 
	 * @param id
	 * @param pid
	 * @param message
	 * @return ptm
	 */
	@ApiOperation(value = "Creates a PTMessage between a teacher {id} and a parent with the subject of the message {subject}." + 
			"Based of an PTInbox {pid}")
	@RequestMapping(method = RequestMethod.POST, path ="/teacher/{id}/makePTM/{pid}/message/{message}")
	PTMessages createPTMessages(@PathVariable Integer id, @PathVariable Integer pid, @PathVariable String message) {
		PTMessages ptm = new PTMessages(ptinbox.findOne(pid), ptinbox.findOne(pid).subject, teachers.findOne(id).name, message);
		ptmessage.save(ptm);
		return ptm;
	}
	
	/**
	 * Deletes a PTInbox and all associated messages for the PTInbox. The returns string says who the inbox is between and 
	 * how many associated messages were deleted
	 * 
	 * @param id
	 * @param ptid
	 * @return "Deleted all " + messages + " messages and the inbox between " + teach + " and " + par + " with the subject " + sub
	 */
	@ApiOperation(value = "Deletes a PTInbox and all associated messages for the PTInbox.")
	@RequestMapping(method = RequestMethod.DELETE, path ="/teacher/{id}/deletePTI/{stid}")
	String deletePTInbox(@PathVariable Integer id, @PathVariable Integer ptid) {
		List<PTMessages> list = ptmessage.findAll();
		int messages = 0;
		for (PTMessages ptm : list) {
			if (ptm.ptinbox.id == ptid && ptm.ptinbox.teacher.id == id) {
				ptmessage.delete(ptm);
				messages++;
			}
		}
		String teach = ptinbox.findOne(id).teacher.name;
		String par = ptinbox.findOne(id).parent.name;
		String sub = ptinbox.findOne(id).subject;
		if (ptinbox.findOne(ptid).teacher.id == id) {
			ptinbox.delete(id);
		}
		return "Deleted all " + messages + " messages and the inbox between " + teach + " and " + par + " with the subject " + sub;
	}
	
	@Autowired
	STInboxRepository stinbox;
	
	@Autowired
	STMessagesRepository stmessage;
	
	/**
	 * Gets all the conversations between a teacher and students
	 * 
	 * @param id
	 * @return sti
	 */
	@ApiOperation(value = "Gets all the conversations between a teacher and students")
	@RequestMapping(method = RequestMethod.GET, path ="/teacher/{id}/stinbox")
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
	
	/**
	 * Gets all the PTMessages between a teacher {id} and student in a specific STInbox {sid}
	 * 
	 * @param id
	 * @param pid
	 * @return stm
	 */
	@ApiOperation(value = "Gets all the PTMessages between a teacher {id} and student in a specific STInbox {sid}")
	@RequestMapping(method = RequestMethod.GET, path ="teacher/{id}/stinbox/{pid}")
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
	
	/**
	 * Gets all the messages between a teacher {id} and student in a  specific STInbox {pid}.
	 * This returns an arraylist of strings of all the messages in the STMessages
	 * 
	 * @param id
	 * @param pid
	 * @return stm
	 */
	@ApiOperation(value = "Gets all the messages between a teacher {id} and student in a  specific STInbox {pid}.")
	@RequestMapping(method = RequestMethod.GET, path ="teacher/{id}/stinbox/{pid}/messages")
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
	
	/**
	 * Gets all the senders between a teacher {id} and a student in a specific STInbox
	 * This returns an arraylist of strings of all the senders in the PTMessages
	 * 
	 * @param id
	 * @param pid
	 * @return stm
	 */
	@ApiOperation(value = "Gets all the senders between a teacher {id} and a student in a specific STInbox {pid}")
	@RequestMapping(method = RequestMethod.GET, path ="teacher/{id}/stinbox/{pid}/senders")
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

	/**
	 * Creates a STInbox between a teacher {id} and a student {sid} with the subject {subject}
	 * 
	 * @param id
	 * @param pid
	 * @param subject
	 * @return st
	 */
	@ApiOperation(value = "Creates a STInbox between a teacher {id} and a student {sid} with the subject {subject}")
	@RequestMapping(method = RequestMethod.POST, path ="/teacher/{id}/makeSTI/{sid}/titled/{subject}")
	STInbox createSTInbox(@PathVariable Integer id, @PathVariable Integer sid, @PathVariable String subject) {
		STInbox st = new STInbox(students.findOne(sid), teachers.findOne(id), subject);
		stinbox.save(st);
		return st;
	}
	
	/**
	 * Creates a STMessage between a teacher {id} and a student with the subject of the message {subject}
	 * This is based off of a PTInbox {sid}
	 * 
	 * @param id
	 * @param pid
	 * @param message
	 * @return stm
	 */
	@ApiOperation(value = "Creates a STMessage between a teacher {id} and a student with the subject of the message {subject}" + 
			"Based off of a PTInbox {sid}")
	@RequestMapping(method = RequestMethod.POST, path ="/teacher/{id}/makeSTM/{sid}/message/{message}")
	STMessages createSTMessages(@PathVariable Integer id, @PathVariable Integer sid, @PathVariable String message) {
		STMessages stm = new STMessages(stinbox.findOne(sid), stinbox.findOne(sid).subject, teachers.findOne(id).name, message);
		stmessage.save(stm);
		return stm;
	}
	
	/**
	 * Deletes a STInbox and all associated messages for the STInbox. The returns string says who the inbox is between and 
	 * how many associated messages were deleted
	 * 
	 * @param id
	 * @param stid
	 * @return "Deleted all " + messages + " messages and the inbox between " + teach + " and " + stu + " with the subject " + sub
	 */
	@ApiOperation(value = "Deletes a STInbox and all associated messages for the STInbox.")
	@RequestMapping(method = RequestMethod.DELETE, path ="/teacher/{id}/deleteSTI/{stid}")
	String deleteSTInbox(@PathVariable Integer id, @PathVariable Integer stid) {
		List<STMessages> list = stmessage.findAll();
		int messages = 0;
		for (STMessages stm : list) {
			if (stm.stinbox.id == stid && stm.stinbox.teacher.id == id) {
				stmessage.delete(stm);
				messages++;
			}
		}
		String teach = stinbox.findOne(id).teacher.name;
		String stu = stinbox.findOne(id).student.name;
		String sub = stinbox.findOne(id).subject;
		if (stinbox.findOne(stid).teacher.id == id) {
			stinbox.delete(id);
		}
		return "Deleted all " + messages + " messages and the inbox between " + teach + " and " + stu + " with the subject " + sub;
	}
	
}