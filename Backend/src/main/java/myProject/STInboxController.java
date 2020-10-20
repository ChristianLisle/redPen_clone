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

}