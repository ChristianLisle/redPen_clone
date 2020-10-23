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
		String send = messages.findOne(id).sender;
		String mess = messages.findOne(id).message;
		messages.delete(id);
		return "Deleted message sent by " + send + " with the subject " + mess; 
	}
	
	// Create a new stmessage
	@PostMapping("stmessage")
	STMessages createSTMessage(@RequestBody STMessages s) {
		messages.save(s);
		return s;
	}

}