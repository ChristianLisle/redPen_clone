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
}
