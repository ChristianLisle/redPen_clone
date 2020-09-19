package myProject;

import javax.persistence.*;

@Entity
class User {
	
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column
	String user;
	
	@Column
	String role; //User role (teacher, student, or parent)
	
	public Integer getId() { return id; }
	
	public String getUser() { return user; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	
	
}