package myProject;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "courses", "courseRegistrations", "assignments", "assignedAssignments", "parent"})
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column
	String name;

	@Column
	@JsonProperty(access = Access.WRITE_ONLY)
	String password;

	@OneToMany(mappedBy = "student")
	Set<CourseRegistration> registrations;

	@OneToMany(mappedBy = "student")
	Set<AssignedAssignment> assignments;
	
	@OneToMany(mappedBy = "student")
    Set<STInbox> stinbox;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	Parent parent;

	public Student() {}

	public Student(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public boolean resetPassword(String oldPassword, String newPassword) {
		if (this.password.equals(oldPassword)) {
			this.password = newPassword;
			return true;
		} else
			return false;
	}
	
	public Set<CourseRegistration> getCourseRegistrations()	{
		return registrations;
	}
	
	public Set<Course> getCourses()	{
		Set<Course> c = new HashSet<Course>();
		for (CourseRegistration cr : registrations)	{
			c.add(cr.getCourse());
		}
		return c;
	}
	
	public Set<AssignedAssignment> getAssignedAssignments()	{
		return assignments;
	}
	
	public Set<Assignment> getAssignments()	{
		Set<Assignment> a = new HashSet<Assignment>();
		for (AssignedAssignment aa : assignments) {
			a.add(aa.getAssignment());
		}
		return a;
	}
	
	public Parent getParent()	{
		return parent;
	}
}