package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PTMessagesController {
	@Autowired
	PTMessagesRepository messages;

	@GetMapping("/ptmessage/{id}")
	PTMessages getPTInbox(@PathVariable Integer id) {
		return messages.findOne(id);
	}

	@RequestMapping("/ptmessages")
	List<PTMessages> all() {
		return messages.findAll();
	}

	@DeleteMapping("/ptmessage/{id}")
	String deleteP2TMessages(@PathVariable Integer id) {
		String send = messages.findOne(id).sender;
		String sub = messages.findOne(id).subject;
		messages.delete(id);
		return "Deleted message sent by " + send + " with the subject " + sub; 
	}

}