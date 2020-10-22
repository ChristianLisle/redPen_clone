package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PTInboxController {
	@Autowired
	PTInboxRepository inbox;

	@GetMapping("/ptinbox/{id}")
	PTInbox getPTInbox(@PathVariable Integer id) {
		return inbox.findOne(id);
	}

	@RequestMapping("/ptinbox")
	List<PTInbox> all() {
		return inbox.findAll();
	}

	@DeleteMapping("/ptinbox/{id}")
	String deleteP2TInbox(@PathVariable Integer id) {
		String teach = inbox.findOne(id).teacher.name;
		String paren = inbox.findOne(id).parent.name;
		String sub = inbox.findOne(id).subject;
		inbox.delete(id);
		return "Deleted message between " + teach + " and " + paren + " with the subject " + sub;
	}
	
	@Autowired
	PTMessagesRepository ptmessages;
	
	//Finds all the ptmessages associated with an inbox
	@RequestMapping("/ptinbox/{id}/messages")
	List<PTMessages> findMessages(@PathVariable Integer id) {
		List<PTMessages> messages = new ArrayList<PTMessages>();
		List<PTMessages> ptm = ptmessages.findAll();
		for (PTMessages p : ptm) {
			if (p.ptinbox.id == id) {
				messages.add(p);
			}
		}
		return messages;
	}
	
	//finds all the messages associated with an inbox
	@RequestMapping("/ptinbox/{id}/messagesOnly")
	List<String> findMessageOnly(@PathVariable Integer id) {
		List<String> messages = new ArrayList<String>();
		List<PTMessages> ptm = ptmessages.findAll();
		for (PTMessages p : ptm) {
			if (p.ptinbox.id == id) {
				messages.add(p.message);
			}
		}
		return messages;
	}
	
	//finds all the messages associated with an inbox
	@RequestMapping("/ptinbox/{id}/messagesSender")
	List<String> findMessagesSender(@PathVariable Integer id) {
		List<String> messages = new ArrayList<String>();
		List<PTMessages> ptm = ptmessages.findAll();
		for (PTMessages p : ptm) {
			if (p.ptinbox.id == id) {
				messages.add(p.sender);
			}
		}
		return messages;
	}
}