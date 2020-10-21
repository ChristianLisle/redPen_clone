package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class STInboxController {
	@Autowired
	STInboxRepository inbox;

	@GetMapping("/stinbox/{id}")
	STInbox getSTInbox(@PathVariable Integer id) {
		return inbox.findOne(id);
	}

	@RequestMapping("/stinbox")
	List<STInbox> all() {
		return inbox.findAll();
	}

	@DeleteMapping("/stinbox/{id}")
	String deleteS2TInbox(@PathVariable Integer id) {
		inbox.delete(id);
		return "Deleted message between " + inbox.findOne(id).teacher.name + " and " 
				+ inbox.findOne(id).student.name + " with the subject " + inbox.findOne(id).subject;
	}
	
	@Autowired
	STMessagesRepository stmessages;
	
	//Finds all the stmessages associated with an inbox
	@RequestMapping("/stinbox/{id}/messages")
	List<STMessages> findMessages(@PathVariable Integer id) {
		List<STMessages> messages = new ArrayList<STMessages>();
		List<STMessages> stm = stmessages.findAll();
		for (STMessages s : stm) {
			if (s.stinbox.id == id) {
				messages.add(s);
			}
		}
		return messages;
	}
	
	//finds all the messages associated with an inbox
	@RequestMapping("/stinbox/{id}/messagesOnly")
	List<String> findMessageOnly(@PathVariable Integer id) {
		List<String> messages = new ArrayList<String>();
		List<STMessages> stm = stmessages.findAll();
		for (STMessages s : stm) {
			if (s.stinbox.id == id) {
				messages.add(s.message);
			}
		}
		return messages;
	}
	
	//finds all the messages associated with an inbox
	@RequestMapping("/stinbox/{id}/messagesSender")
	List<String> findMessagesSender(@PathVariable Integer id) {
		List<String> messages = new ArrayList<String>();
		List<STMessages> stm = stmessages.findAll();
		for (STMessages s : stm) {
			if (s.stinbox.id == id) {
				messages.add(s.sender);
			}
		}
		return messages;
	}
}