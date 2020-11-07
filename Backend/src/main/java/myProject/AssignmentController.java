package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "AssignmentController")
@RestController
public class AssignmentController {
	@Autowired
	AssignmentRepository assignments;

	@ApiOperation(value = "Get Assignment {id}")
	@GetMapping("/assignment/{id}")
	Assignment getAssignment(@PathVariable Integer id) {
		return assignments.findOne(id);
	}
	
	@ApiOperation(value = "Get AssignedAssignment linked to Assignment {id}")
	@GetMapping("/assignment/{id}/assigned")
	java.util.Set<AssignedAssignment> getAssignedAssignments(@PathVariable Integer id)	{
		return assignments.getOne(id).getAssignedAssignments();
	}

	@ApiOperation(value = "Get all Assignment objects in database")
	@RequestMapping("/assignments")
	List<Assignment> hello() {
		return assignments.findAll();
	}

	@ApiOperation(value = "Update Assignment {id}")
	@PutMapping("/assignment/{id}/update")
	Assignment updateAssignment(@RequestBody Assignment s, @PathVariable Integer id) {
		Assignment old_a = assignments.findOne(id);
		old_a.setName(s.getName());
		old_a.setAssignmentDescription(s.getAssignmentDescription());
		assignments.save(old_a);
		return old_a;
	}

	@ApiOperation(value = "Delete Assignment {id} (remove from database)")
	@DeleteMapping("/assignment/{id}")
	String deleteAssignment(@PathVariable Integer id) {
		assignments.delete(id);
		return "deleted " + id;
	}
}