package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssignmentController {
	@Autowired
	AssignmentRepository assignments;

	@GetMapping("/assignment/{id}")
	Assignment getAssignment(@PathVariable Integer id) {
		return assignments.findOne(id);
	}
	
	@GetMapping("/assignment/{id}/assigned")
	java.util.Set<AssignedAssignment> getAssignedAssignments(@PathVariable Integer id)	{
		return assignments.getOne(id).getAssignedAssignments();
	}

	@RequestMapping("/assignments")
	List<Assignment> hello() {
		return assignments.findAll();
	}

	@PutMapping("/assignment/{id}/update")
	Assignment updateAssignment(@RequestBody Assignment s, @PathVariable Integer id) {
		Assignment old_a = assignments.findOne(id);
		old_a.setName(s.getName());
		old_a.setAssignmentDescription(s.getAssignmentDescription());
		assignments.save(old_a);
		return old_a;
	}

	@DeleteMapping("/assignment/{id}")
	String deleteAssignment(@PathVariable Integer id) {
		assignments.delete(id);
		return "deleted " + id;
	}
}