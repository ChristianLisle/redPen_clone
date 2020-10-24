package myProject;

import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
//@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "students"})
@Table(name = "st_messages")
public class STMessages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@ManyToOne
	@JoinColumn(name = "st_inbox_id", nullable = false)
	STInbox stinbox;
	
	@Column 
	String subject;
	
	@Column
	String sender;
	
	@Column
	String message;

	public STMessages() {}

	public STMessages(STInbox stinbox,String subject, String sender, String message) {
		this.stinbox = stinbox;
		this.subject = subject;
		this.sender = sender;
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public STInbox getSTInbox() {
		return stinbox;
	}
	
	public void setSTInbox(STInbox stinbox) {
		this.stinbox = stinbox;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}