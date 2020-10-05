package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import javax.persistence.*;


@Entity
public class Student {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    
    @Column
    String name;
    
    @OneToMany(mappedBy = "student")
    Set<CourseRegistration> registrations;
    
    @OneToMany(mappedBy = "student")
    Set<AssignedAssignment> assignments;
    
    public Student()	{}
    
    public Student(String name) {
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
    
    /*public Set<AssignedAssignment> getAssignments()	{
    	return assignments;
    }*/
    
    /*public Set<Course> getCourses()	{
    	Set<Course> courses = Collections.emptySet();
    	for (CourseRegistration cr : registrations) {
    		if (this == cr.student) {
    			courses.add(cr.getCourse());
    		}
    	}
    	return courses;
    }*/
    
    // additional properties
    // standard constructors, getters, and setters
}