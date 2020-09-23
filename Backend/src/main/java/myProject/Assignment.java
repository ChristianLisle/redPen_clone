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
	String desc; //assignment description (homework, exam, etc.)
	
	public Integer getId() { return id; }
	
	public String getAssignment() { return assignment; }
	public String getDesc() { return desc; }
	public void setDesc(String desc) { this.desc = desc; }
	
	
}