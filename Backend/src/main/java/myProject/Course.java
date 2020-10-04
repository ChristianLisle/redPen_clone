package myProject;

import java.util.Set;
import javax.persistence.*;

@Entity
class Course {
 
    @Id
    int id;
 
    @OneToMany(mappedBy = "course")
    Set<CourseRegistration> registrations;
 
    public int getId()	{
    	return id;
    }
    
    /*public Set<CourseRegistration> getRegistrations()	{
    	return registrations;
    }*/
    
    // additional properties
    // standard constructors, getters, and setters
}