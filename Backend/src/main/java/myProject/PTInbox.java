package myProject;

import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
//@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "students"})
@Table(name = "pt_inbox")
public class PTInbox {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@ManyToOne
	@JoinColumn(name = "parent_id", nullable = false)
	Parent parent;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	Teacher teacher;
	
	@Column
	String subject;
	
	@OneToMany(mappedBy = "ptinbox")
	Set<PTMessages> messages;

	public PTInbox() {}

	public PTInbox(Parent pid, Teacher tid, String subject) {
		parent = pid;
		teacher = tid;
		this.subject = subject;
	}

	public Integer getId() {
		return id;
	}

	public Parent getParent() {
		return parent;
	}
	
	public void setParent(Parent parent) {
		this.parent = parent;
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