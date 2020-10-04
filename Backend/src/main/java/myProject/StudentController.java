package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
	@Autowired
	StudentRepository students;
	
	@GetMapping("/student/{id}")
	Student getStudent(@PathVariable Integer id)	{
		return students.findOne(id);
	}
	
	@RequestMapping("/students")
	List<Student> hello()	{
		return students.findAll();
	}
	
	@PostMapping("student")
	Student createStudent(@RequestBody Student s)	{
		students.save(s);
		return s;
	}
	
	
	@PutMapping("/student/{id}")
	Student updateStudent(@RequestBody Student s, @PathVariable Integer id)	{
		Student old_s = students.findOne(id);
		old_s.setName(s.getName());
		students.save(old_s);
		return old_s;
	}
	
	
	@DeleteMapping("/student/{id}")
	String deleteStudent(@PathVariable Integer id)	{
		students.delete(id);
		return "deleted " + id;
	}
	
}