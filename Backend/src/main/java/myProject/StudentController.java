package myProject;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
/**
 * This class handles all interactions that deal with creating, accessing, or updating data specific to Students
 * 
 * @author Christian Lisle & Carter Moseley
 *
 */
public class StudentController {
	@Autowired
	StudentRepository students;
	
	// Student Login/Register mappings
	
	/**
	 * Create a new Student (used to 'register' a student)
	 * @param s Student
	 * @return Successfully created Student
	 */
	@PostMapping("/register-student")
	Student createStudent(@RequestBody Student s) {
		students.save(s);
		return s;
	}
	
	/**
	 * Log into RedPen as Student. This method takes a Student (name and password) as input and finds an identical student in the database.
	 * @param s Student
	 * @return id of student if login is successful, failure message otherwise.
	 */
	@PostMapping("/login-student")
	String getStudent(@RequestBody Student s)	{
		int j = (int) students.count(); // count() method does not include the number of deleted entities (this causes issues when iterating over id with deleted entity)
		for (int i = 1; i <= (int) j; i++) {
			if (!students.exists(i)) j++; // deleted entity id found. Increment number of iterations to make up for this.
			else	{
				if (s.getName().equals((students.getOne(i)).getName()))	{
					if (s.getPassword().equals(students.getOne(i).getPassword()))	{
						return "" + students.getOne(i).getId();
					}
					else return "Incorrect password";
				}
			}
		}
		return "There are no students with the name " + s.getName();
	}
	
	/**
	 * Change the password of a student given their id and a NewPassword object.
	 * @param np Stores the users old and new password, so that user's old password can be validated with the database
	 * @param id The id of the user attempting to reset their password
	 * @return Message notifying user if their password was changed successfully
	 */
	@PutMapping("/student/{id}/reset-password")
	String resetPassword(@RequestBody NewPassword np, @PathVariable Integer id)	{
		Student old_s = students.findOne(id);
		if (old_s.resetPassword(np.getOldPassword(), np.getNewPassword()))	{
			students.save(old_s);
			return "Password reset successfuly.";
		}
		return "Password not reset.";
	}
	
	// Basic student info mappings
	
	/**
	 * Get a student given a student id
	 * @param id Student id
	 * @return Student with parameter id
	 */
	@GetMapping("/student/{id}")
	Student getStudent(@PathVariable Integer id) {
		return students.findOne(id);
	}

	/**
	 * Get all students
	 * @return List of all students
	 */
	@RequestMapping("/students")
	List<Student> getAllStudents() {
		return students.findAll();
	}
	
	/**
	 * Delete the student with the given id
	 * @param id Student id
	 * @return Message
	 */
	@DeleteMapping("/student/{id}")
	String deleteStudent(@PathVariable Integer id) {
		String name = students.findOne(id).getName();
		students.delete(id);
		return "deleted student " + name;
	}
	
	@Autowired
	CourseRepository courses;

	@Autowired
	CourseRegistrationRepository registrar;
	
	@Autowired
	TeacherCourseRepository assignedCourses;
	

	// Students and courses relationship mappings
	
	/**
	 * Allow Student id to register for TeacherCourse assignedCourse_id
	 * @param id Student id
	 * @param assignedCourse_id TeacherCourse id
	 * @return Course Registration that was created when Student registered for TeacherCourse
	 */
	@PutMapping("/student/{id}/register/{assignedCourse_id}")
	CourseRegistration registerCourse(@PathVariable Integer id, @PathVariable Integer assignedCourse_id) {
		Student student = students.findOne(id);
		TeacherCourse assignedCourse = assignedCourses.findOne(assignedCourse_id);
		registrar.save(new CourseRegistration(student, assignedCourse));
		return registrar.findOne((int) registrar.count()); // count() returns the number of entities (last pos)
	}

	/**
	 * Get all CourseRegistrations that a student has
	 * @param id Student id
	 * @return Set of CourseRegistration objects that belong to given (through id) student
	 */
	@RequestMapping("/student/{id}/registrations")
	java.util.Set<CourseRegistration> listRegistrations(@PathVariable Integer id) {
		return students.findOne(id).getCourseRegistrations();
	}

	/**
	 * Get all Courses (not CourseRegistrations) that a student has
	 * @param id Student id
	 * @return Set of Course objects that the given student (through id) is taking or has taken
	 */
	@RequestMapping("/student/{id}/courses")
	java.util.Set<Course> listCourses(@PathVariable Integer id) {
		return students.findOne(id).getCourses();
	}
	
	@Autowired
	AssignmentRepository assignments;
	
	@Autowired
	AssignedAssignmentRepository assignedAssignments;
	
	// Students and assigned assignments relationship mappings
	
	/**
	 * Assign given (through assignment_id) Assignment to given Student (through id).
	 * This creates an AssignedAssignment that is stored in the database and then returned
	 * @param id Student id
	 * @param assignment_id Assignment id
	 * @return AssignedAssignment that was created when Assignment was assigned to Student
	 */
	@PutMapping("/student/{id}/assign/{assignment_id}")
	AssignedAssignment assignStudentAssignment(@PathVariable Integer id, @PathVariable Integer assignment_id) {
		Student student = students.findOne(id);
		Assignment assignment = assignments.findOne(assignment_id);
		assignedAssignments.save(new AssignedAssignment(student, assignment));
		return assignedAssignments.findOne((int) assignedAssignments.count()); // count() returns the number of entities (last pos)
	}
	
	/**
	 * Get all Assignments (not the AssignedAssignment) that have been assigned to a student
	 * @param id Student id
	 * @return Set of Assignment objects that belong to given (through id) student
	 */
	@RequestMapping("/student/{id}/assignments/overview")
	java.util.Set<Assignment> listAssignments(@PathVariable Integer id)	{
		return students.getOne(id).getAssignments();
	}
	
	/**
	 * Get all AssignedAssignments that have been assigned to a student
	 * @param id Student id
	 * @returnSet of AssignedAssignment objects that belong to given (through id) student
	 */
	@RequestMapping("/student/{id}/assignments/report")
	java.util.Set<AssignedAssignment> listAssignedAssignments(@PathVariable Integer id)	{
		return students.getOne(id).getAssignedAssignments();
	}
	
	/**
	 * Get a specific Assignment given a student (through id) and assignment id
	 * @param id Student id
	 * @param assignment_id Assignment id
	 * @return Assignment with assignment_id
	 */
	@GetMapping("student/{id}/assignment/{assignment_id}/overview")
	Assignment getAssignment(@PathVariable Integer id, @PathVariable Integer assignment_id)	{
		Assignment a = assignments.findOne(assignment_id);
		return a;
	}
	
	/**
	 * Get a specific AssignedAssignment given a student (through id) and assignment id
	 * @param id Student id
	 * @param assignment_id Assignment id
	 */
	@GetMapping("student/{id}/assignment/{assignment_id}/report")
	AssignedAssignment getAssignedAssignment(@PathVariable Integer id, @PathVariable Integer assignment_id)	{
		List<AssignedAssignment> list = assignedAssignments.findAll();
		for (AssignedAssignment aa : list)	{
			if (aa.getAssignment().getId() == assignment_id)	{
				return aa;
			}
		}
		return null;
	}
	
	
	@Autowired
	STInboxRepository stinbox;
	
	@Autowired
	STMessagesRepository stmessage;

	/**
	 * Gets all messages a Student (given through id) has between Teachers
	 * @param id Student id
	 * @return List of STInbox (messages)
	 */
	@RequestMapping("/student/{id}/stinbox")
	List<STInbox> studentTeacherInbox(@PathVariable Integer id) {
		List<STInbox> sti = new ArrayList<STInbox>();
		List<STInbox> list = stinbox.findAll();
		for (STInbox st : list) {
			if (st.student.id == id) {
				sti.add(st);
			}
		}
		return sti;
	}
	
	/**
	 * Gets all the STMessages between a Student and Teacher in an inbox
	 * @param id Student id
	 * @param sid STInbox id
	 * @return List of STMessages
	 */
	@GetMapping("student/{id}/stinbox/{sid}")
	List<STMessages> studentTeacherInboxMessages(@PathVariable Integer id, @PathVariable Integer sid) {
		List<STMessages> stm = new ArrayList<STMessages>();
		List<STMessages> list = stmessage.findAll();
		for (STMessages st : list) {
			if (st.stinbox.student.id == id && st.stinbox.id == sid) {
				stm.add(st);
			}
		}
		return stm;
	}
	
	/**
	 * Gets all the messages between a Student and a Teacher in an inbox
	 * @param id Student id
	 * @param sid STInbox id
	 * @return List of messages from STMessages belonging to STInbox
	 */
	@GetMapping("student/{id}/stinbox/{sid}/messages")
	List<String> studentTeacherInboxMessagesOnly(@PathVariable Integer id, @PathVariable Integer sid) {
		List<String> stm = new ArrayList<String>();
		List<STMessages> list = stmessage.findAll();
		for (STMessages st : list) {
			if (st.stinbox.student.id == id && st.stinbox.id == sid) {
				stm.add(st.message);
			}
		}
		return stm;
	}
	
	/**
	 * Gets all the messages between a Student and a Teacher in an inbox
	 * @param id Student id
	 * @param sid STInbox id
	 * @return List (String) of message senders 
	 */
	@GetMapping("student/{id}/stinbox/{sid}/senders")
	List<String> studentTeacherInboxMessagesSender(@PathVariable Integer id, @PathVariable Integer sid) {
		List<String> stm = new ArrayList<String>();
		List<STMessages> list = stmessage.findAll();
		for (STMessages st : list) {
			if (st.stinbox.student.id == id && st.stinbox.id == sid) {
				stm.add(st.sender);
			}
		}
		return stm;
	}
	
	@Autowired
	TeacherRepository teachers;
	
	/**
	 * Create a STInbox and store it in the database
	 * @param id Student id
	 * @param tid Teacher id
	 * @param subject Subject title
	 * @return STInbox created
	 */
	@PostMapping("/student/{id}/makeSTI/{tid}/titled/{subject}")
	STInbox createSTInbox(@PathVariable Integer id, @PathVariable Integer tid, @PathVariable String subject) {
		STInbox st = new STInbox(students.findOne(id), teachers.findOne(tid), subject);
		stinbox.save(st);
		return st;
	}
	
	/**
	 * Create a STMessage and store it in the database
	 * @param id Student id
	 * @param sid STInbox id
	 * @param message Message to be stored in the database
	 * @return Saved STMessages object
	 */
	@PostMapping("/student/{id}/makeSTM/{sid}/message/{message}")
	STMessages createSTMessages(@PathVariable Integer id, @PathVariable Integer sid, @PathVariable String message) {
		STMessages stm = new STMessages(stinbox.findOne(sid), stinbox.findOne(sid).subject, students.findOne(id).name, message);
		stmessage.save(stm);
		return stm;
	}
	
	/**
	 * Delete an inbox and all associated messages for stid
	 * @param id Student id
	 * @param stid STInbox id
	 * @return Message informing user of all deleted messages
	 */
	@DeleteMapping("/student/{id}/deleteSTI/{stid}")
	String deleteSTInbox(@PathVariable Integer id, @PathVariable Integer stid) {
		List<STMessages> list = stmessage.findAll();
		int messages = 0;
		for (STMessages stm : list) {
			if (stm.stinbox.id == stid && stm.stinbox.student.id == id) {
				stmessage.delete(stm);
				messages++;
			}
		}
		String teach = stinbox.findOne(id).teacher.name;
		String stu = stinbox.findOne(id).student.name;
		String sub = stinbox.findOne(id).subject;
		if (stinbox.findOne(stid).student.id == id) {
			stinbox.delete(id);
		}
		return "Deleted all " + messages + " messages and the inbox between " + teach + " and " + stu + " with the subject " + sub;
	}
	
	@Autowired
	ParentRepository parents;
	
	/**
	 * Get the Parent of a Student
	 * @param id Student id
	 * @return Parent of Student with id
	 */
	@RequestMapping("/student/{id}/parent")
	Parent getParent(@PathVariable Integer id) {
		return students.findOne(id).parent;
	}
}

