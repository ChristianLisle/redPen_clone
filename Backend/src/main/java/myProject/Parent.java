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

	/**
	 * This sets the parent's name
	 * 
	 * @param name
	 */
	public Parent(String name) {
		this.name = name;
	}

	/**
	 * Returns the ID of the parent
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Returns the name of the parent
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name fo the parent
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the parent's password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method to change the parent's password
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return true or false
	 */
	public boolean resetPassword(String oldPassword, String newPassword) {
		if (this.password.equals(oldPassword)) {
			this.password = newPassword;
			return true;
		} else
			return false;
	}
	
	/**
	 * Returns the students of a parent
	 * 
	 * @return students
	 */
	public Set<Student> getStudents()	{
		return students;
	}
	
	/**
	 * Properly formats the name of the parent so that it's all lower case except the first letter of each word
	 * which is upper case
	 * 
	 * @param name
	 * @return output
	 */
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