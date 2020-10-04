package myProject;

import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "registration")
class CourseRegistration {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
 
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;
 
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    Course course;
 
    // int grade;
    
    public Student getStudent()	{ 
    	return student;
    }
    
    public Course getCourse()	{
    	return course;
    }
    
    // additional properties
    // standard constructors, getters, and setters
}
