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