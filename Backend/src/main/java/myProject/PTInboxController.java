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
		inbox.delete(id);
		return "Deleted message between " + inbox.findOne(id).teacher.name + " and " 
				+ inbox.findOne(id).parent.name + " with the subject " + inbox.findOne(id).subject;
	}

}