package myProject;

import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "students"})
@Table(name = "parent")
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column
	String name;

	@Column
	@JsonProperty(access = Access.WRITE_ONLY)
	String password;
	
	@OneToMany(mappedBy = "parent")
	Set<Student> students;
	
	@OneToMany(mappedBy = "parent")
    Set<PTInbox> ptinbox;

	public Parent() {}

	public Parent(String name) {
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
	
	public Set<Student> getStudents()	{
		return students;
	}
	
	public String properNaming(String name) {
    	name = name.toLowerCase();
    	String output = "";
		String[] split = name.split(" ");
		for (int i = 0; i < split.length - 1; i++) {
			output += split[i].substring(0, 1).toUpperCase() + split[i].substring(1) + " ";
		} 
		output += split[split.length - 1].substring(0, 1).toUpperCase() + split[split.length - 1].substring(1);
		return output;
    }
}