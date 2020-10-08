package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EntryController {
	@Autowired
	StudentRepository students;
	
	@PostMapping("create-account/student")
	Student createStudent(@RequestBody Student s) {
		students.save(s);
		return s;
	}
	
	/*@GetMapping("/student/login")
	String getStudent(@RequestBody Student s)	{
		for (int i = 1; i < (int) students.count(); i++) {
			if (s.name.equals((students.getOne(i)).getName()))	{
				if (s.password.equals(students.getOne(i).getPassword()))	{
					return "Successfully logged in as " + s.name;
				}
				else return "Incorrect password for " + s.name + ".";
			}
		}
		return "Incorrect name entered.";
	}*/
}