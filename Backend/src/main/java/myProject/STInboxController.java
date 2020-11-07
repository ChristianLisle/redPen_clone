package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "STInboxController")
@RestController
public class STInboxController {
	@Autowired
	STInboxRepository inbox;

	/**
	 * A get mapping that returns the STInbox of that mapping
	 * 
	 * @param id
	 * @return inbox.findOne(id)
	 */
	@ApiOperation(value = "Returns the STInbox of that mapping")
	@RequestMapping(method = RequestMethod.GET, path ="/stinbox/{id}")
	STInbox getSTInbox(@PathVariable Integer id) {
		return inbox.findOne(id);
	}

	/**
	 * A request mapping that returns a list of all STinboxes
	 * 
	 * @return inbox.findAll()
	 */
	@ApiOperation(value = "Returns a list of all STinboxes")
	@RequestMapping(method = RequestMethod.GET, path ="/stinbox")
	List<STInbox> all() {
		return inbox.findAll();
	}

	/**
	 * A delete mapping that deletes a STinbox and returns a string saying who the conversation was between and the subject 
	 * of the STInbox
	 * 
	 * @param id
	 * @return "Deleted message between " + teach + " and " + paren + " with the subject " + sub
	 */
	@ApiOperation(value = "Deletes a STinbox and returns a string saying who the conversation was between and the subject" + 
			" of the STInbox")
	@RequestMapping(method = RequestMethod.DELETE, path ="/stinbox/{id}")
	String deleteS2TInbox(@PathVariable Integer id) {
		String teach = inbox.findOne(id).teacher.name;
		String stud = inbox.findOne(id).student.name;
		String sub = inbox.findOne(id).subject;
		inbox.delete(id);
		return "Deleted inbox between " + teach + " and " + stud + " with the subject " + sub;
	}
	
	/**
	 * A post mapping to create a new STInbox
	 * 
	 * @param s
	 * @return s
	 */
	@ApiOperation(value = "Creates a new STInbox")
	@RequestMapping(method = RequestMethod.POST, path ="stinbox")
	STInbox createSTInbox(@RequestBody STInbox s) {
		inbox.save(s);
		return s;
	}
	
	@Autowired
	STMessagesRepository stmessages;
	
	/**
	 * A request mapping that returns all stmessages associated with the STInbox
	 * 
	 * @param id
	 * @return messages
	 */
	@ApiOperation(value = "Returns all stmessages associated with the STInbox {id}")
	@RequestMapping(method = RequestMethod.GET, path ="/stinbox/{id}/messages")
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
	
	/**
	 * A request mapping that returns a list of all the messages of STMessages in the STInbox
	 * 
	 * @param id
	 * @return messages
	 */
	@ApiOperation(value = "Returns a list of all the messages of STMessages in the STInbox")
	@RequestMapping(method = RequestMethod.GET, path ="/stinbox/{id}/messagesOnly")
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
	
	/**
	 * A request mapping that returns a list of all the senders of STMessages in the STInbox
	 * 
	 * @param id
	 * @return messages
	 */
	@ApiOperation(value = "Returns a list of all the senders of STMessages in the STInbox {id}")
	@RequestMapping(method = RequestMethod.GET, path ="/stinbox/{id}/messagesSender")
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