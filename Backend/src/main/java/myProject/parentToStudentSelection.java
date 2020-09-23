package myProject;

import javax.persistence.*;

@Entity
@Table (name = "parent - student selection")
class parentToStudentSelection {
	
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column
	//String user;
	String parent;
	
	@Column
	//String role; //User role (teacher, student, or parent)
	String student;
	
	@Column
	Integer grade;
	
	public Integer getId() { return id; }
	public String getParent() { return parent; }
	public String getStudent() { return student; }
	public Integer getGrade() { return grade; }
	//public void setRole(String role) { this.role = role; }
	
	
}