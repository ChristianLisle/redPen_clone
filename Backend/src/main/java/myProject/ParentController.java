package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParentController {
	@Autowired
	ParentRepository parents;
	
	// Parent Login/Register mappings

	@PostMapping("/register-parent")
	Parent createParent(@RequestBody Parent s) {
		parents.save(s);
		return s;
	}
	
	@PostMapping("/login-parent")
	String getParent(@RequestBody Parent s)	{
		for (int i = 1; i <= (int) parents.count(); i++) {
			if (parents.exists(i) && s.getName().equals((parents.getOne(i)).getName()))	{
				if (s.getPassword().equals(parents.getOne(i).getPassword()))	{
					return "" + parents.getOne(i).getId();
				}
				else return "Incorrect password";
			}
		}
		return "There are no parents with the name " + s.getName();
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
		parents.delete(id);
		return "deleted " + id;
	}
	
	// Need methods for getting students of a parent
}
