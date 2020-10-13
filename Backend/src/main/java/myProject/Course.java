package myProject;

import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "course")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer", "assignments"})
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column
	String name;

	@Column
	String courseDescription;

	@OneToMany(mappedBy = "course")
	Set<TeacherCourse> teacherCourses;

	@OneToMany(mappedBy = "course")
	Set<Assignment> assignments;

	public Course() {}

	public Course(String name, String d) {
		this.name = name;
		this.courseDescription = d;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		this.name = n;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String d) {
		this.courseDescription = d;
	}

	public Set<Assignment> getAssignments() {
		return assignments;
	}
}