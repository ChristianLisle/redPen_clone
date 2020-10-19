package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PTMessagesController {
	@Autowired
	PTMessagesRepository messages;

	@GetMapping("/ptmessages/{id}")
	PTMessages getPTInbox(@PathVariable Integer id) {
		return messages.findOne(id);
	}

	@RequestMapping("/ptmessages")
	List<PTMessages> all() {
		return messages.findAll();
	}

	@DeleteMapping("/ptmessages/{id}")
	String deleteP2TMessages(@PathVariable Integer id) {
		messages.delete(id);
		return "Deleted message sent by " + messages.findOne(id).sender 
				+ " with the subject " + messages.findOne(id).subject; 
	}

}