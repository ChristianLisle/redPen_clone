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

	@RequestMapping("/assignments")
	List<Assignment> hello() {
		return assignments.findAll();
	}

	// Not used, since we only want to create assignments specific to courses.
	/*@PostMapping("/assignment")
	Assignment createAssignment(@RequestBody Assignment a) {
		assignments.save(a);
		return a;
	}*/

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