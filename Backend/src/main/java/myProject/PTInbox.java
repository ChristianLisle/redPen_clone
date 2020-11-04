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

	/**
	 * A method that get the id of a ptinbox
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * A method that gets the parent of the PTInbox
	 * 
	 * @return parent
	 */
	public Parent getParent() {
		return parent;
	}
	
	/**
	 * Sets the parent of the PTInbox
	 * 
	 * @param parent
	 */
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the teacher of the PTInbox
	 * 
	 * @return teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}
	
	/**
	 * Sets the teacher of the PTInbox
	 * 
	 * @param teacher
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	/**
	 * Returns the subject of the PTInbox
	 * 
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * A method to set the subject of a PTInbox
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

}