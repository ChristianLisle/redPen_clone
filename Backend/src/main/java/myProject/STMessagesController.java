package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "STMessagesController")
@RestController
public class STMessagesController {
	@Autowired
	STMessagesRepository messages;

	/**
	 * A get mapping to get the JSON info of a STMessage
	 * 
	 * @param id
	 * @return messages.findOne(id)
	 */
	@ApiOperation(value = "Returns the STMessages of the id {id}")
	@GetMapping("/stmessage/{id}")
	STMessages getSTInbox(@PathVariable Integer id) {
		return messages.findOne(id);
	}

	/**
	 * A request mapping to get all STMessages that exist
	 * 
	 * @return messages.findAll()
	 */
	@ApiOperation(value = "Get all STMessages that exist")
	@RequestMapping("/stmessages")
	List<STMessages> all() {
		return messages.findAll();
	}

	/**
	 * A delete mapping to delete a STMessage and returns a string saying who was in the message and 
	 * the subject of the STMessage
	 * 
	 * @param id
	 * @return "Deleted message sent by " + send + " with the subject " + sub
	 */
	@ApiOperation(value = "Delete a STMessage and returns a string saying who was in the message and" + 
			" the subject of the STMessage")
	@DeleteMapping("/stmessage/{id}")
	String deleteS2TMessages(@PathVariable Integer id) {
		String send = messages.findOne(id).sender;
		String mess = messages.findOne(id).message;
		messages.delete(id);
		return "Deleted message sent by " + send + " with the subject " + mess; 
	}
	
	/**
	 * A post mapping to create a new STMessages
	 * 
	 * @param p
	 * @return p
	 */
	@ApiOperation(value = "Create a new STMessages")
	@PostMapping("stmessage")
	STMessages createSTMessage(@RequestBody STMessages s) {
		messages.save(s);
		return s;
	}

}