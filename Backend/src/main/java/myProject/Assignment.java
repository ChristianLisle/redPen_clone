package myProject;

import javax.persistence.*;

@Entity
class Assignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column
	String assignment;
	
	@Column
	String assignmentDesc; //assignment description (homework, exam, etc.)
	
	public Integer getId() { return id; }
	
	public String getAssignment() { return assignment; }
	public String getAssignmentDesc() { return assignmentDesc; }
	public void setAssignmentDesc(String assignmentDesc) { this.assignmentDesc = assignmentDesc; }
	
	
}