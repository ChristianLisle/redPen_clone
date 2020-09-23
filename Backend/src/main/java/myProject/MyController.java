package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {
	
	@Autowired
	MyDatabase db;
	
	@GetMapping("/user/{id}")
	User getUser(@PathVariable Integer id) {
		return db.findOne(id);
	}
	@RequestMapping("/users")
	List<User> hello() {
		return db.findAll();
	}
	
	@PostMapping("/user")
	User createUser(@RequestBody User p) {
		db.save(p);
		return p;
	}
	
	
	@PutMapping("/user/{id}")
	User updateUser(@RequestBody User p, @PathVariable Integer id) {
		User old_p = db.findOne(id);
		old_p.setRole(p.role);
		db.save(old_p);
		return old_p;
	}
	
	@DeleteMapping("/user/{id}")
	String deleteUser(@PathVariable Integer id) {
		db.delete(id);
		return "deleted " + id;
	}
	
}


