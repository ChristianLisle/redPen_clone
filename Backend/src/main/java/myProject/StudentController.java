package myProject;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
	@Autowired
	StudentRepository students;
	
	// Student Login/Register mappings
	
	@PostMapping("/register-student")
	Student createStudent(@RequestBody Student s) {
		students.save(s);
		return s;
	}
	
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
	
	@GetMapping("/student/{id}")
	Student getStudent(@PathVariable Integer id) {
		return students.findOne(id);
	}

	@RequestMapping("/students")
	List<Student> getAllStudents() {
		return students.findAll();
	}
	
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
	
	// Method for course registration
	@PutMapping("/student/{id}/register/{assignedCourse_id}")
	CourseRegistration registerCourse(@PathVariable Integer id, @PathVariable Integer assignedCourse_id) {
		Student student = students.findOne(id);
		TeacherCourse assignedCourse = assignedCourses.findOne(assignedCourse_id);
		registrar.save(new CourseRegistration(student, assignedCourse));
		return registrar.findOne((int) registrar.count()); // count() returns the number of entities (last pos)
	}

	// Method for getting all registrations for a student
	@RequestMapping("/student/{id}/registrations")
	java.util.Set<CourseRegistration> listRegistrations(@PathVariable Integer id) {
		return students.findOne(id).getCourseRegistrations();
	}

	// Method for getting all courses for a specific student
	@RequestMapping("/student/{id}/courses")
	java.util.Set<Course> listCourses(@PathVariable Integer id) {
		return students.findOne(id).getCourses();
	}
	
	@Autowired
	AssignmentRepository assignments;
	
	@Autowired
	AssignedAssignmentRepository assignedAssignments;
	
	// Students and assigned assignments relationship mappings
	
	// Method assigns assignment to student
	@PutMapping("/student/{id}/assign/{assignment_id}")
	AssignedAssignment assignStudentAssignment(@PathVariable Integer id, @PathVariable Integer assignment_id) {
		Student student = students.findOne(id);
		Assignment assignment = assignments.findOne(assignment_id);
		assignedAssignments.save(new AssignedAssignment(student, assignment));
		return assignedAssignments.findOne((int) assignedAssignments.count()); // count() returns the number of entities (last pos)
	}
	
	// Method returns a list of assignments
	@RequestMapping("/student/{id}/assignments/overview")
	java.util.Set<Assignment> listAssignments(@PathVariable Integer id)	{
		return students.getOne(id).getAssignments();
	}
	
	// Method returns a list of assignments with feedback/grade (assignedAssignment object)
	@RequestMapping("/student/{id}/assignments/report")
	java.util.Set<AssignedAssignment> listAssignedAssignments(@PathVariable Integer id)	{
		return students.getOne(id).getAssignedAssignments();
	}
	
	// Method returns a single assignment overview (assignment object)
	@GetMapping("student/{id}/assignment/{assignment_id}/overview")
	Assignment getAssignment(@PathVariable Integer id, @PathVariable Integer assignment_id)	{
		Assignment a = assignments.findOne(assignment_id);
		return a;
	}
	
	// Method returns a single assignment with feedback and grades (assignedAssignment object)
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
	
	
	// Need methods for assigning student to a parent
	
	
	@Autowired
	STInboxRepository stinbox;
	
	@Autowired
	STMessagesRepository stmessage;
	
	//Gets all messages a student has between teachers
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
	
	//Gets all the PTMessages between a parent and teacher in an inbox
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
	
	//Gets all the messages between a parent and a teacher in an inbox
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
	
	//Gets all the messages between a parent and a teacher in an inbox
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
	
	//Creates a stinbox
	@PostMapping("/student/{id}/makeSTI/{tid}/titled/{subject}")
	STInbox createSTInbox(@PathVariable Integer id, @PathVariable Integer tid, @PathVariable String subject) {
		STInbox st = new STInbox(students.findOne(id), teachers.findOne(tid), subject);
		stinbox.save(st);
		return st;
	}
	
	//Creates a stmessage
	@PostMapping("/student/{id}/makeSTM/{sid}/message/{message}")
	STMessages createSTMessages(@PathVariable Integer id, @PathVariable Integer sid, @PathVariable String message) {
		//sid is the id of a stinbox
		STMessages stm = new STMessages(stinbox.findOne(sid), stinbox.findOne(sid).subject, students.findOne(id).name, message);
		stmessage.save(stm);
		return stm;
	}
	
	//Deletes an inbox and all associated messages for sti
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
	
	//Newly added by Carter
	
	@Autowired
	ParentRepository parents;
	
	@RequestMapping("/student/{id}/parent")
	Parent getParent(@PathVariable Integer id) {
		return students.findOne(id).parent;
	}
	
}

