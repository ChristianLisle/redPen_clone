package myProject;

import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "registration")
public class CourseRegistration {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
 
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;
 
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    Course course;
    
    boolean completed;
 
    double grade;
    
    public CourseRegistration()	{}
    
    public CourseRegistration(Student s, Course c){
    	this.student = s;
    	this.course = c;
    }
    
    public Integer getId()	{
    	return id;
    }
    
    public Student getStudent()	{ 
    	return student;
    }
    
    public Course getCourse()	{
    	return course;
    }
    
    public double getGrade()	{
    	return grade;
    }
    
    public void setGrade(double grade)	{
    	this.grade = grade;
    }
    
    public boolean getCompleted()	{
    	return completed;
    }
    
    public void complete()	{
    	this.completed = true;
    }
}
