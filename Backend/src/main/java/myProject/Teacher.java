package myProject;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "courses"})
@Table(name = "teacher")
public class Teacher {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    
    @Column
    String name;
    
    @Column
    @JsonProperty(access = Access.WRITE_ONLY)
    String password;
    
    @OneToMany(mappedBy = "teacher")
    Set<TeacherCourse> teacherCourses;
    
    @OneToMany(mappedBy = "teacher")
    Set<PTInbox> ptinbox;
    
    @OneToMany(mappedBy = "teacher")
    Set<STInbox> stinbox;
    
    public Teacher()	{}
    
    public Teacher(String name) {
    	this.name = name;
    }
 
    /**
     * Returns the ID of the teacher
     * 
     * @return id
     */
    public Integer getId()	{
    	return id;
    }

    /**
     * Returns the name of the teacher
     * 
     * @return name
     */
    public String getName()	{
    	return name;
    }
    
    /**
     * Sets the name of the teacher
     * 
     * @param name
     */
    public void setName(String name)	{
    	this.name = name;
    }
    
    /**
     * Gets the password of the teacher
     * 
     * @return password
     */
    public String getPassword() {
		return password;
	}
    
    /**
     * Sets the name of the teacher to the proper format so it is all lower case and the first letter of 
     * every word is upper case
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

    /**
     * A method to resent the password of the teacher 
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
	 * A method that gets all courses of a specific teacher
	 * 
	 * @return c
	 */
	public Set<Course> getCourses()	{
		Set<Course> c = new HashSet<Course>();
		for (TeacherCourse tc : teacherCourses) {
			c.add(tc.getCourse());
		}
		return c;
	}
    
}