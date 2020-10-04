package myProject;

import java.util.Set;
import javax.persistence.*;


@Entity
class Student {
 
    @Id
    int id;
 
    @OneToMany(mappedBy = "student")
    Set<CourseRegistration> registrations;
 
    public int getId()	{
    	return id;
    }
    
    /*public Set<CourseRegistration> getCourses()	{
    	return registrations;
    }*/
    // additional properties
    // standard constructors, getters, and setters
}