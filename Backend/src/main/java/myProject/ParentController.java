package myProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParentController {
	@Autowired
	ParentRepository parents;
	
	// Parent Login/Register mappings

	@PostMapping("/register-parent")
	Parent createParent(@RequestBody Parent p) {
		parents.save(p);
		return p;
	}
	
	@PostMapping("/login-parent")
	String getParent(@RequestBody Parent p)	{
		int j = (int) parents.count(); // count() method does not include the number of deleted entities (this causes issues when iterating over id with deleted entity)
		for (int i = 1; i <= j; i++) {
			if (!parents.exists(i)) j++; // deleted entity id found. Increment number of iterations to make up for this.
			else	{
				if (p.getName().equals((parents.getOne(i)).getName()))	{
					if (p.getPassword().equals(parents.getOne(i).getPassword()))	{
						return "" + parents.getOne(i).getId();
					}
					else return "Incorrect password";
				}
			}
		}
		return "There are no parents with the name " + p.getName();
	}
	
	@PutMapping("/parent/{id}/reset-password")
	String resetPassword(@RequestBody NewPassword np, @PathVariable Integer id)	{
		Parent old_p = parents.findOne(id);
		if (old_p.resetPassword(np.getOldPassword(), np.getNewPassword()))	{
			parents.save(old_p);
			return "Password reset successfuly.";
		}
		return "Password not reset.";
	}
	
	// Basic Parent info mappings
	
	// Get parent {id}
	@GetMapping("/parent/{id}")
	Parent getParent(@PathVariable Integer id) {
		return parents.findOne(id);
	}

	// Get all parents
	@RequestMapping("/parents")
	List<Parent> getAllParents() {
		return parents.findAll();
	}
	
	// Delete parent {id}
	@DeleteMapping("/parent/{id}")
	String deleteParent(@PathVariable Integer id) {
		String name = parents.findOne(id).getName();
		parents.delete(id);
		return "deleted parent " + name;
	}
	
	
	// Need methods for getting students of a parent
	
	
	@Autowired
	PTInboxRepository ptinbox;
	
	@Autowired
	PTMessagesRepository ptmessage;
	
	//Gets all messages a student has between teachers
	@RequestMapping("/parent/{id}/ptinbox")
	List<PTInbox> parentTeacherInbox(@PathVariable Integer id) {
		List<PTInbox> pti = new ArrayList<PTInbox>();
		List<PTInbox> list = ptinbox.findAll();
		for (PTInbox pt : list) {
			if (pt.parent.id == id) {
				pti.add(pt);
			}
		}
		return pti;
	}
	
	//Gets all the PTMessages between a parent and teacher in an inbox
	@GetMapping("parent/{id}/ptinbox/{pid}/messages")
	List<PTMessages> parentTeacherInboxMessages(@PathVariable Integer id, @PathVariable Integer pid) {
		List<PTMessages> ptm = new ArrayList<PTMessages>();
		List<PTMessages> list = ptmessage.findAll();
		for (PTMessages pt : list) {
			if (pt.ptinbox.parent.id == id && pt.ptinbox.id == pid) {
				ptm.add(pt);
			}
		}
		return ptm;
	}
	
	//Gets all the messages between a parent and a teacher in an inbox
	@GetMapping("parent/{id}/ptinbox/{pid}/messagesOnly")
	List<String> parentTeacherInboxMessagesOnly(@PathVariable Integer id, @PathVariable Integer pid) {
		List<String> ptm = new ArrayList<String>();
		List<PTMessages> list = ptmessage.findAll();
		for (PTMessages pt : list) {
			if (pt.ptinbox.parent.id == id && pt.ptinbox.id == pid) {
				ptm.add(pt.message);
			}
		}
		return ptm;
	}
	
	//Gets all the messages between a parent and a teacher in an inbox
	@GetMapping("parent/{id}/ptinbox/{pid}/messagesSender")
	List<String> parentTeacherInboxMessagesSender(@PathVariable Integer id, @PathVariable Integer pid) {
		List<String> ptm = new ArrayList<String>();
		List<PTMessages> list = ptmessage.findAll();
		for (PTMessages pt : list) {
			if (pt.ptinbox.parent.id == id && pt.ptinbox.id == pid) {
				ptm.add(pt.sender);
			}
		}
		return ptm;
	}
	
	@Autowired
	TeacherRepository teachers;
	
	//Creates a ptinbox
	@PostMapping("/parent/{id}/makePTI/{tid}/titled/{subject}")
	PTInbox createPTInbox(@PathVariable Integer id, @PathVariable Integer tid, @PathVariable String subject) {
		PTInbox pt = new PTInbox(parents.findOne(id), teachers.findOne(tid), subject);
		ptinbox.save(pt);
		return pt;
	}
	
	//Creates a ptmessage
	@PostMapping("/parent/{id}/makePTM/{pid}/message/{message}")
	PTMessages createPTMessages(@PathVariable Integer id, @PathVariable Integer pid, @PathVariable String message) {
		//pid is the id of a ptinbox
		PTMessages ptm = new PTMessages(ptinbox.findOne(pid), ptinbox.findOne(pid).subject, parents.findOne(id).name, message);
		ptmessage.save(ptm);
		return ptm;
	}

}
