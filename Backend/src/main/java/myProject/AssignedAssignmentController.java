package myProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "AssignedAssignmentController")
@RestController
public class AssignedAssignmentController {
	@Autowired
	AssignedAssignmentRepository assignedAssignments;

	@ApiOperation(value = "Get AssignedAssignment {id}")
	@RequestMapping(method = RequestMethod.GET, path = "/assigned-assignment/{id}")
	AssignedAssignment getAssignedAssignment(@PathVariable Integer id) {
		return assignedAssignments.findOne(id);
	}

	@ApiOperation(value = "Update AssignedAssignment {id} (update grade/feedback)")
	@RequestMapping(method = RequestMethod.PUT, path = "/assigned-assignment/{id}/update")
	AssignedAssignment updateAssignment(@RequestBody AssignedAssignment a, @PathVariable Integer id) {
		AssignedAssignment old_a = assignedAssignments.findOne(id);
		old_a.setGrade(a.getGrade());
		old_a.setFeedback(a.getFeedback());
		assignedAssignments.save(old_a);
		return old_a;
	}
	
	@ApiOperation(value = "Delete AssignedAssignment {id}")
	@RequestMapping(method = RequestMethod.DELETE, path = "/assigned-assignment/{id}")
	String deleteAssignedAssignment(@PathVariable Integer id) {
		assignedAssignments.delete(id);
		return "deleted " + id;
	}

}