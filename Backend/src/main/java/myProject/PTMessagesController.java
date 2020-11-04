package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PTMessagesController {
	@Autowired
	PTMessagesRepository messages;

	/**
	 * A get mapping to get the JSON info of a PTMessage
	 * 
	 * @param id
	 * @return messages.findOne(id)
	 */
	@GetMapping("/ptmessage/{id}")
	PTMessages getPTInbox(@PathVariable Integer id) {
		return messages.findOne(id);
	}

	/**
	 * A request mapping to get all PTMessages that exist
	 * 
	 * @return messages.findAll()
	 */
	@RequestMapping("/ptmessages")
	List<PTMessages> all() {
		return messages.findAll();
	}

	/**
	 * A delete mapping to delete a PTMessage and returns a string saying who was in the message and 
	 * the subject of the PTMessage
	 * 
	 * @param id
	 * @return "Deleted message sent by " + send + " with the subject " + sub
	 */
	@DeleteMapping("/ptmessage/{id}")
	String deleteP2TMessages(@PathVariable Integer id) {
		String send = messages.findOne(id).sender;
		String sub = messages.findOne(id).subject;
		messages.delete(id);
		return "Deleted message sent by " + send + " with the subject " + sub; 
	}
	
	/**
	 * A post mapping to create a new PTMessages
	 * 
	 * @param p
	 * @return p
	 */
	@PostMapping("ptmessage")
	PTMessages createPTMessage(@RequestBody PTMessages p) {
		messages.save(p);
		return p;
	}

}