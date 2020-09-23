package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssignmentController {
	
	@Autowired
	AssignmentDatabase db;
	
	@GetMapping("/assignemnt/{id}")
	Assignment getAssignment(@PathVariable Integer id) {
		return db.findOne(id);
	}
	@RequestMapping("/assignment")
	List<Assignment> hello() {
		return db.findAll();
	}
	
	@PostMapping("/assignment")
	Assignment createAssignment(@RequestBody Assignment p) {
		db.save(p);
		return p;
	}
	
	
	@PutMapping("/assignment/{id}")
	Assignment updateAssignment(@RequestBody Assignment p, @PathVariable Integer id) {
		Assignment old_p = db.findOne(id);
		old_p.setDesc(p.desc);
		db.save(old_p);
		return old_p;
	}
	
	@DeleteMapping("/assignment/{id}")
	String deleteAssignment(@PathVariable Integer id) {
		db.delete(id);
		return "deleted " + id;
	}
	
}


