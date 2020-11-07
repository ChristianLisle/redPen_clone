package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "PTInboxController")
@RestController
public class PTInboxController {
	@Autowired
	PTInboxRepository inbox;

	/**
	 * A get mapping that returns the PTInbox of that mapping
	 * 
	 * @param id
	 * @return inbox.findOne(id)
	 */
	@ApiOperation(value = "Returns the PTInbox of that mapping")
	@RequestMapping(method = RequestMethod.GET, path ="/ptinbox/{id}")
	PTInbox getPTInbox(@PathVariable Integer id) {
		return inbox.findOne(id);
	}

	/**
	 * A request mapping that returns a list of all PTinboxes
	 * 
	 * @return inbox.findAll()
	 */
	@ApiOperation(value = "Returns a list of all PTinboxes")
	@RequestMapping(method = RequestMethod.GET, path ="/ptinbox")
	List<PTInbox> all() {
		return inbox.findAll();
	}

	/**
	 * A delete mapping that deletes a PTinbox and returns a string saying who the conversation was between and the subject 
	 * of the PTInbox
	 * 
	 * @param id
	 * @return "Deleted message between " + teach + " and " + paren + " with the subject " + sub
	 */
	@ApiOperation(value = "Deletes a PTinbox and returns a string saying who the conversation was between and the subject" + 
			" of the PTInbox")
	@RequestMapping(method = RequestMethod.DELETE, path ="/ptinbox/{id}")
	String deleteP2TInbox(@PathVariable Integer id) {
		String teach = inbox.findOne(id).teacher.name;
		String paren = inbox.findOne(id).parent.name;
		String sub = inbox.findOne(id).subject;
		inbox.delete(id);
		return "Deleted message between " + teach + " and " + paren + " with the subject " + sub;
	}
	
	/**
	 * A post mapping to create a new PTInbox
	 * 
	 * @param p
	 * @return p
	 */
	@ApiOperation(value = "Creates a new PTInbox")
	@RequestMapping(method = RequestMethod.POST, path ="ptinbox")
	PTInbox createInbox(@RequestBody PTInbox p) {
		inbox.save(p);
		return p;
	}
	
	@Autowired
	PTMessagesRepository ptmessages;
	
	/**
	 * A request mapping that returns all ptmessages associated with the PTInbox
	 * 
	 * @param id
	 * @return messages
	 */
	@ApiOperation(value = "Returns all ptmessages associated with the PTInbox {id}")
	@RequestMapping(method = RequestMethod.GET, path ="/ptinbox/{id}/messages")
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
	
	/**
	 * A request mapping that returns a list of all the messages of PTMessages in the PTInbox
	 * 
	 * @param id
	 * @return messages
	 */
	@ApiOperation(value = "Returns a list of all the messages of PTMessages in the PTInbox")
	@RequestMapping(method = RequestMethod.GET, path ="/ptinbox/{id}/messagesOnly")
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
	
	/**
	 * A request mapping that returns a list of all the senders of PTMessages in the PTInbox
	 * 
	 * @param id
	 * @return messages
	 */
	@ApiOperation(value = "Returns a list of all the senders of PTMessages in the PTInbox")
	@RequestMapping(method = RequestMethod.GET, path ="/ptinbox/{id}/messagesSender")
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