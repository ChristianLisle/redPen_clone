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
 
    public Integer getId()	{
    	return id;
    }

    public String getName()	{
    	return name;
    }
    
    public void setName(String name)	{
    	this.name = name;
    }
    
    public String getPassword() {
		return password;
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

	public boolean resetPassword(String oldPassword, String newPassword) {
		if (this.password.equals(oldPassword)) {
			this.password = newPassword;
			return true;
		} else
			return false;
	}
	
	public Set<Course> getCourses()	{
		Set<Course> c = new HashSet<Course>();
		for (TeacherCourse tc : teacherCourses) {
			c.add(tc.getCourse());
		}
		return c;
	}
    
}