package myProject;

import javax.persistence.*;

@Entity
@Table(name = "assigned_assignment")
public class AssignedAssignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	Student student;

	@ManyToOne
	@JoinColumn(name = "assignment_id", nullable = false)
	Assignment assigned;
	
	@Column
	double grade;

	@Column
	String feedback;

	public AssignedAssignment() {}

	public AssignedAssignment(Student s, Assignment a) {
		this.student = s;
		this.assigned = a;
	}

	public AssignedAssignment(Student s, Assignment a, double grade, String feedback) {
		this(s, a); // Call the constructor above
		this.grade = grade;
		this.feedback = feedback;
	}

	public Integer getId() {
		return id;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public Student getStudent()	{
		return student;
	}
	
	public Assignment getAssignment()	{
		return assigned;
	}
}
