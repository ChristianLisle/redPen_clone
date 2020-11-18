package myProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
//@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "students"})
@Table(name = "st_inbox")
public class STInbox {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	Student student;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	Teacher teacher;
	
	@Column
	String subject;
	
	@OneToMany(mappedBy = "stinbox")
	Set<STMessages> messages;

	public STInbox() {}

	public STInbox(Student sid, Teacher tid, String subject) {
		student = sid;
		teacher = tid;
		this.subject = subject;
	}

	/**
	 * Returns the id of the STInbox
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Returns the student of the inbox
	 * 
	 * @return student
	 */
	public Student getStudent() {
		return student;
	}
	
	/**
	 * Allows the user to set the student of the inbox
	 * 
	 * @param student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	
	/**
	 * returns the teacher of the inbox
	 * 
	 * @return teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}
	
	/**
	 * Allows the user to set the teacher of the inbox
	 * 
	 * @param teacher
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	/**
	 * Returns the subject of the inbox
	 * 
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Allows the user to set the subject of the inbox
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
}