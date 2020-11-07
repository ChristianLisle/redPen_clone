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

	/**
	 * Returns the id of the PTMessages
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Returns the PTInbox that is associated with this PTMessages
	 * 
	 * @return ptinbox
	 */
	public PTInbox getPTInbox() {
		return ptinbox;
	}
	
	/**
	 * Sets the PTInbox of this PTMessages
	 * 
	 * @param ptinbox
	 */
	public void setPTInbox(PTInbox ptinbox) {
		this.ptinbox = ptinbox;
	}
	
	/**
	 * Gets the subject of this PTMessages
	 * 
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Sets the subject of this PTMessages
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Gets the sender of a PTMessage
	 * 
	 * @return sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Sets the sender of a PTMessage
	 * 
	 * @param sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	/**
	 * Gets the message of a PTMessage
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message of a PTMessage
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}