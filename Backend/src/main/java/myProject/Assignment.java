package myProject;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "assignment")
public class Assignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	Course course;

	@OneToMany(mappedBy = "assigned")
	Set<AssignedAssignment> assignedAssignments;

	@Column
	String name;

	@Column
	String assignmentDescription;

	public Assignment() {
	}

	public Assignment(String name, String d) {
		this.name = name;
		this.assignmentDescription = d;
	}

	public Assignment(Assignment a) {
		this.name = a.getName();
		this.assignmentDescription = a.getAssignmentDescription();
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

	public String getAssignmentDescription() {
		return assignmentDescription;
	}

	public void setAssignmentDescription(String d) {
		this.assignmentDescription = d;
	}

	public void setCourse(Course c) {
		this.course = c;
	}
}
