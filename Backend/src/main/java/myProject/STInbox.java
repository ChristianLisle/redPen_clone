package myProject;

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

	public Integer getId() {
		return id;
	}

	public Student getStudent() {
		return student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

}