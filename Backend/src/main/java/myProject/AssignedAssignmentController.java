package myProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssignedAssignmentController {
	@Autowired
	AssignedAssignmentRepository assignedAssignments;

	@GetMapping("/assigned-assignment/{id}")
	AssignedAssignment getAssignedAssignment(@PathVariable Integer id) {
		return assignedAssignments.findOne(id);
	}

	@PutMapping("/assigned-assignment/{id}/update")
	AssignedAssignment updateAssignment(@RequestBody AssignedAssignment a, @PathVariable Integer id) {
		AssignedAssignment old_a = assignedAssignments.findOne(id);
		old_a.setGrade(a.getGrade());
		old_a.setFeedback(a.getFeedback());
		assignedAssignments.save(old_a);
		return old_a;
	}

	@DeleteMapping("/assigned-assignment/{id}")
	String deleteAssignedAssignment(@PathVariable Integer id) {
		assignedAssignments.delete(id);
		return "deleted " + id;
	}

}