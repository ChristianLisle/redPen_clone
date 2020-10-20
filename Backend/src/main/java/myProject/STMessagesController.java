package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class STMessagesController {
	@Autowired
	STMessagesRepository messages;

	@GetMapping("/stmessage/{id}")
	STMessages getSTInbox(@PathVariable Integer id) {
		return messages.findOne(id);
	}

	@RequestMapping("/stmessages")
	List<STMessages> all() {
		return messages.findAll();
	}

	@DeleteMapping("/stmessage/{id}")
	String deleteS2TMessages(@PathVariable Integer id) {
		messages.delete(id);
		return "Deleted message sent by " + messages.findOne(id).sender 
				+ " with the subject " + messages.findOne(id).subject; 
	}

}