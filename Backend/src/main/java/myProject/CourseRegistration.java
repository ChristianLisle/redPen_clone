package myProject;

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
    @JoinColumn(name = "teacher_classes_id", nullable = false)
    TeacherCourse teacherCourse;
    
    boolean completed;
 
    Double grade;
    
    public CourseRegistration()	{}
    
    public CourseRegistration(Student s, TeacherCourse tc){
    	this.student = s;
    	this.teacherCourse = tc;
    }
    
    public Integer getId()	{
    	return id;
    }
    
    public Student getStudent()	{ 
    	return student;
    }
    
    public TeacherCourse getAssignedCourse()	{
    	return teacherCourse;
    }
    
    public Course getCourse()	{
    	return teacherCourse.getCourse();
    }
    
    public Teacher getTeacher()	{
    	return teacherCourse.getTeacher();
    }
    
    public Double getGrade()	{
    	return grade;
    }
    
    public void setGrade(double grade)	{
    	// Grade can not be set if the course has already been completed.
    	if (!getCompleted())
    		this.grade = grade;
    }
    
    public boolean getCompleted()	{
    	return completed;
    }
    
    public void complete()	{
    	// Course cannot be completed if no grade has been set
    	if (getGrade() != null)
    		this.completed = true;
    }
}
