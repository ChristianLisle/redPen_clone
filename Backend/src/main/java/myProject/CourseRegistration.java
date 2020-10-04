package myProject;

import java.util.Set;
import javax.persistence.*;

@Entity
class CourseRegistration {
 
    @Id
    Long id;
 
    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;
 
    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;
 
    // int grade;
    
    /*public Student getStudent()	{ 
    	return student;
    }
    
    public Course getCourse()	{
    	return course;
    }*/
    
    // additional properties
    // standard constructors, getters, and setters
}