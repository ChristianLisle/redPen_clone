package parentToStudentSelection;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table (name = "parent - student selection")
class parentToStudentSelection {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@NotEmpty
	//String user;
	String parent;
	
	@NotEmpty
	//String role; //User role (teacher, student, or parent)
	String student;
	
	@NotEmpty
	Integer grade;
	
	public Integer getId() { return id; }
	public String getParent() { return parent; }
	public String getStudent() { return student; }
	public Integer getGrade() { return grade; }
	//public void setRole(String role) { this.role = role; }
	
	
}