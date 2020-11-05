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

	/**
	 * Gets the id of the messages
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Gets the inbox of this message
	 * 
	 * @return stinbox
	 */
	public STInbox getSTInbox() {
		return stinbox;
	}
	
	/**
	 * Sets the inbox of the message
	 * 
	 * @param stinbox
	 */
	public void setSTInbox(STInbox stinbox) {
		this.stinbox = stinbox;
	}
	
	/**
	 * Returns the subject of this message
	 * 
	 * @return subjet
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Sets the subject of the message
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Gets the sender of this message
	 * 
	 * @return sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Sets the sender of a message
	 * 
	 * @param sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	/**
	 * Gets the message of this message
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message of the message
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}