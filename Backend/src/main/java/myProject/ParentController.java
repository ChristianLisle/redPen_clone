package myProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParentController {
	@Autowired
	ParentRepository parents;
	
	// Parent Login/Register mappings

	/**
	 * Post mapping to add a parent
	 * 
	 * @param p
	 * @return p
	 */
	@PostMapping("/register-parent")
	Parent createParent(@RequestBody Parent p) {
		parents.save(p);
		return p;
	}
	
	/**
	 * A post mapping to let the user attempt to log in. Depending on if it is successful it will show feedback to let them know
	 * what happened. Like if the parent logs in ot if the password if wrong or if there are no parent with that name
	 * 
	 * @param p
	 * @return String to let the user know if they successfully log in or if something is wrong with their choice
	 */
	@PostMapping("/login-parent")
	String getParent(@RequestBody Parent p)	{
		int j = (int) parents.count(); // count() method does not include the number of deleted entities (this causes issues when iterating over id with deleted entity)
		for (int i = 1; i <= j; i++) {
			if (!parents.exists(i)) j++; // deleted entity id found. Increment number of iterations to make up for this.
			else	{
				if (p.getName().equals((parents.getOne(i)).getName()))	{
					if (p.getPassword().equals(parents.getOne(i).getPassword()))	{
						return "" + parents.getOne(i).getId();
					}
					else return "Incorrect password";
				}
			}
		}
		return "There are no parents with the name " + p.getName();
	}
	
	/**
	 * A put mapping to reset a parents password. Depending on if it resets or not, it lets the parent know what
	 * happened
	 * 
	 * @param np
	 * @param id
	 * @return String to let the parent know if the password has been reset or not
	 */
	@PutMapping("/parent/{id}/reset-password")
	String resetPassword(@RequestBody NewPassword np, @PathVariable Integer id)	{
		Parent old_p = parents.findOne(id);
		if (old_p.resetPassword(np.getOldPassword(), np.getNewPassword()))	{
			parents.save(old_p);
			return "Password reset successfuly.";
		}
		return "Password not reset.";
	}
	
	// Basic Parent info mappings
	
	/**
	 * A get mapping to return a parent whose id matches that in the {id}
	 * 
	 * @param id
	 * @return parents.findOne(id)
	 */
	@GetMapping("/parent/{id}")
	Parent getParent(@PathVariable Integer id) {
		return parents.findOne(id);
	}

	/**
	 * A request mapping that returns all parents on record
	 * 
	 * @return parents.findAll()
	 */
	@RequestMapping("/parents")
	List<Parent> getAllParents() {
		return parents.findAll();
	}
	
	/**
	 * A delete mapping that deletes a parent and displays a string saying which parent has been deleted 
	 * 
	 * @param id
	 * @return String to let user know which parent has been deleted
	 */
	@DeleteMapping("/parent/{id}")
	String deleteParent(@PathVariable Integer id) {
		String name = parents.findOne(id).getName();
		parents.delete(id);
		return "deleted parent " + name;
	}
	
	@Autowired
	PTInboxRepository ptinbox;
	
	@Autowired
	PTMessagesRepository ptmessage;
	
	/**
	 * A request mapping that returns all the messages between a selected parent {id} and all teachers
	 * 
	 * @param id
	 * @return pti
	 */
	@RequestMapping("/parent/{id}/ptinbox")
	List<PTInbox> parentTeacherInbox(@PathVariable Integer id) {
		List<PTInbox> pti = new ArrayList<PTInbox>();
		List<PTInbox> list = ptinbox.findAll();
		for (PTInbox pt : list) {
			if (pt.parent.id == id) {
				pti.add(pt);
			}
		}
		return pti;
	}
	
	/**
	 * A request mapping that returns all the messages for a selected parent {id} in a selected PTIbox {pid}
	 * 
	 * @param id
	 * @param pid
	 * @return ptm
	 */
	@GetMapping("parent/{id}/ptinbox/{pid}")
	List<PTMessages> parentTeacherInboxMessages(@PathVariable Integer id, @PathVariable Integer pid) {
		List<PTMessages> ptm = new ArrayList<PTMessages>();
		List<PTMessages> list = ptmessage.findAll();
		for (PTMessages pt : list) {
			if (pt.ptinbox.parent.id == id && pt.ptinbox.id == pid) {
				ptm.add(pt);
			}
		}
		return ptm;
	}
	
	/**
	 * A request mapping that returns all the messages for a selected parent {id} in a selected PTIbox {pid}. The return here is 
	 * an arraylist of strings with all the conversations
	 * 
	 * @param id
	 * @param pid
	 * @return ptm
	 */
	@GetMapping("parent/{id}/ptinbox/{pid}/messages")
	List<String> parentTeacherInboxMessagesOnly(@PathVariable Integer id, @PathVariable Integer pid) {
		List<String> ptm = new ArrayList<String>();
		List<PTMessages> list = ptmessage.findAll();
		for (PTMessages pt : list) {
			if (pt.ptinbox.parent.id == id && pt.ptinbox.id == pid) {
				ptm.add(pt.message);
			}
		}
		return ptm;
	}
	
	/**
	 * A request mapping that returns all the messages for a selected parent {id} in a selected PTIbox {pid}. The return here is 
	 * an arraylist of strings with all the senders (Those who sent the messages)
	 * 
	 * @param id
	 * @param pid
	 * @return
	 */
	@GetMapping("parent/{id}/ptinbox/{pid}/senders")
	List<String> parentTeacherInboxMessagesSender(@PathVariable Integer id, @PathVariable Integer pid) {
		List<String> ptm = new ArrayList<String>();
		List<PTMessages> list = ptmessage.findAll();
		for (PTMessages pt : list) {
			if (pt.ptinbox.parent.id == id && pt.ptinbox.id == pid) {
				ptm.add(pt.sender);
			}
		}
		return ptm;
	}
	
	@Autowired
	TeacherRepository teachers;
	
	/**
	 * Creates a PTIbox with the parent {id}, the teacher they want to talk with {tid} and the subject the user want the 
	 * conversation to have {subject}
	 * @param id
	 * @param tid
	 * @param subject
	 * @return
	 */
	@PostMapping("/parent/{id}/makePTI/{tid}/titled/{subject}")
	PTInbox createPTInbox(@PathVariable Integer id, @PathVariable Integer tid, @PathVariable String subject) {
		PTInbox pt = new PTInbox(parents.findOne(id), teachers.findOne(tid), subject);
		ptinbox.save(pt);
		return pt;
	}
	
	/**
	 * Creates a PTMessages (the messages for a PTInbox) based on the parent {id}, the ptinbox this conversation uses {pid}
	 * and the message this should contain {message}
	 * 
	 * @param id
	 * @param pid
	 * @param message
	 * @return
	 */
	@PostMapping("/parent/{id}/makePTM/{pid}/message/{message}")
	PTMessages createPTMessages(@PathVariable Integer id, @PathVariable Integer pid, @PathVariable String message) {
		PTMessages ptm = new PTMessages(ptinbox.findOne(pid), ptinbox.findOne(pid).subject, parents.findOne(id).name, message);
		ptmessage.save(ptm);
		return ptm;
	}
	
	/**
	 * Deletes PTInbox and all the assocaited PTMessages for the PTInbox. Returns a string with all the telling who the messages were
	 * deleted between and how many  messages were deleted
	 * 
	 * @param id
	 * @param ptid
	 * @return "Deleted all " + messages + " messages and the inbox between " + teach + " and " + par + " with the subject " + sub
	 */
	@DeleteMapping("/parent/{id}/deletePTI/{stid}")
	String deletePTInbox(@PathVariable Integer id, @PathVariable Integer ptid) {
		List<PTMessages> list = ptmessage.findAll();
		int messages = 0;
		for (PTMessages ptm : list) {
			if (ptm.ptinbox.id == ptid && ptm.ptinbox.parent.id == id) {
				ptmessage.delete(ptm);
				messages++;
			}
		}
		String teach = ptinbox.findOne(id).teacher.name;
		String par = ptinbox.findOne(id).parent.name;
		String sub = ptinbox.findOne(id).subject;
		if (ptinbox.findOne(ptid).parent.id == id) {
			ptinbox.delete(id);
		}
		return "Deleted all " + messages + " messages and the inbox between " + teach + " and " + par + " with the subject " + sub;
	}
	
	//For students through parents
	
	@Autowired
	StudentRepository student;
	
	/**
	 * Links a student and parent together by setting the parent {id} as the student's {sid} parent
	 * 
	 * @param id
	 * @param sid
	 * @return "Added " + p.name + " as " + s.name + "'s parent"
	 */
	@PutMapping("/parent/{id}/student/{sid}")
	String addParent(@PathVariable Integer id, @PathVariable Integer sid) {
		Student s = student.findOne(sid);
		Parent p = parents.findOne(id);
		s.setParent(p);
		return "Added " + p.name + " as " + s.name + "'s parent";
	}
	
	/**
	 * A request mapping that finds all the students of a parent
	 * 
	 * @param id
	 * @return parents.findOne(id).getStudents()
	 */
	@RequestMapping("/parent/{id}/students")
	java.util.Set<Student> getParentsStudents(@PathVariable Integer id) {
		return parents.findOne(id).getStudents();
	}
	
	/**
	 * A get mapping that returns with a specific student {sid} of a parent {id} . This is an arraylist so that if there 
	 * is no parent that matches that student it returns an empty list
	 * 
	 * @param id
	 * @param sid
	 * @return returned
	 */
	@GetMapping("/parent/{id}/student/{sid}")
	List<Student> getParentsStudent(@PathVariable Integer id, @PathVariable Integer sid) {
		List<Student> returned = new ArrayList<Student>();
		List<Student> all = student.findAll();
		for (Student s : all) {
			if (s.id == sid && s.parent.id == id) {
				returned.add(s);
			}
		}
		return returned;
	}
	
	@Autowired
	CourseRepository courses;
	
	@Autowired
	CourseRegistrationRepository cr;
	
	/**
	 * A get mapping that finds all courses for a student {sid} of a parent {id}
	 * 
	 * @param id
	 * @param sid
	 * @return student.findOne(sid).getCourses() or null
	 */
	@GetMapping("/parent/{id}/student/{sid}/courses")
	java.util.Set<Course> getParentsStudentCourses(@PathVariable Integer id, @PathVariable Integer sid) {
		List<Student> allS = student.findAll();
		for (Student s : allS) {
			if (s.id == sid && s.parent.id == id) {
				return student.findOne(sid).getCourses();
			}
		}
		return null;
	}
	
	/**
	 * A get mapping that finds a specific course {cid} of a student {sid} under a parent {id}
	 * 
	 * @param id
	 * @param sid
	 * @param cid
	 * @return
	 */
	@GetMapping("/parent/{id}/student/{sid}/course/{cid}")
	Course getParentsStudentSpecificCourse(@PathVariable Integer id, @PathVariable Integer sid, @PathVariable Integer cid) {
		List<Student> all = student.findAll();
		List<CourseRegistration> allCR = cr.findAll(); 
		for (CourseRegistration crr : allCR) {
			if (crr.student.id == sid && crr.student.parent.id == id && crr.teacherCourse.course.id == cid) {
				return crr.teacherCourse.course;
			}
		}
		return null;
	}
	
	/**
	 * A get mapping that finds a teachers course {cid} of a student {sid} under a parent {id}
	 * 
	 * @param id
	 * @param sid
	 * @param cid
	 * @return crr.teacherCourse or null
	 */
	@GetMapping("/parent/{id}/student/{sid}/tcourse/{cid}")
	TeacherCourse getParentsStudentSpecificTCourse(@PathVariable Integer id, @PathVariable Integer sid, @PathVariable Integer cid) {
		List<Student> all = student.findAll();
		List<CourseRegistration> allCR = cr.findAll(); 
		for (CourseRegistration crr : allCR) {
			if (crr.student.id == sid && crr.student.parent.id == id && crr.teacherCourse.course.id == cid) {
				return crr.teacherCourse;
			}
		}
		return null;
	}
	
	@Autowired
	CourseRegistrationRepository registrar;
	
	/**
	 * A get mapping that finds all assignments of a student {sid} under a parent {id}
	 * 
	 * @param id
	 * @param sid
	 * @return student.findOne(sid).getAssignments() or null
	 */
	@GetMapping("/parent/{id}/student/{sid}/assignments")
	java.util.Set<Assignment> getParentsStudentAssignments(@PathVariable Integer id, @PathVariable Integer sid) {
		List<Student> all = student.findAll();
		for (Student s : all) {
			if (s.id == sid && s.parent.id == id) {
				return student.findOne(sid).getAssignments();
			}
		}
		return null;
	}
	
	

}
