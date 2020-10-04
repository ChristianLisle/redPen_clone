package myProject;

import java.util.Set;
import javax.persistence.*;


@Entity
class Student {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    
    @Column
    String name;
    
    @OneToMany(mappedBy = "student")
    Set<CourseRegistration> registrations;
    
 
    public Integer getId()	{
    	return id;
    }

    public String getName()	{
    	return name;
    }
    
    public void setName(String name)	{
    	this.name = name;
    }
    
    /*
    public Set<Course> getCourses()	{
    	return registrations.getCourse();
    }
    */
    
    /*public Set<CourseRegistration> getCourses()	{
    	return registrations;
    }*/
    // additional properties
    // standard constructors, getters, and setters
}