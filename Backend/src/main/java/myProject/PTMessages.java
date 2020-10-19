package myProject;

import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
//@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "students"})
@Table(name = "pt_messages")
public class PTMessages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@ManyToOne
	@JoinColumn(name = "pt_inbox_id", nullable = false)
	PTInbox ptinbox;
	
	@Column 
	String subject;
	
	@Column
	String sender;
	
	@Column
	String message;

	public PTMessages() {}

	public PTMessages(PTInbox ptinbox,String subject, String sender, String message) {
		this.ptinbox = ptinbox;
		this.subject = subject;
		this.sender = sender;
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public PTInbox getPTInbox() {
		return ptinbox;
	}
	
	public void setPTInbox(PTInbox ptinbox) {
		this.ptinbox = ptinbox;
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