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
 
    /* Changed for assigned courses integreation
    @ManyToOne
<<<<<<< Backend/src/main/java/myProject/CourseRegistration.java
    @JoinColumn(name = "assigned_course_id", nullable = false)
    TeacherCourse teacherCourse;
=======
    @JoinColumn(name = "course_id", nullable = false)
    Course course;
    */
    
    //Added for assigned courses integreation
    @ManyToOne
    @JoinColumn(name = "teacher_classes_id", nullable = false)
    TeacherClasses teacherCourse;
>>>>>>> Backend/src/main/java/myProject/CourseRegistration.java
    
    boolean completed;
 
    Double grade;
    
    public CourseRegistration()	{}
    
<<<<<<< Backend/src/main/java/myProject/CourseRegistration.java
    public CourseRegistration(Student s, TeacherCourse tc){
=======
    public CourseRegistration(Student s, TeacherClasses tc){
>>>>>>> Backend/src/main/java/myProject/CourseRegistration.java
    	this.student = s;
    	this.teacherCourse = tc;
    }
    
    public Integer getId()	{
    	return id;
    }
    
    public Student getStudent()	{ 
    	return student;
    }
    
<<<<<<< Backend/src/main/java/myProject/CourseRegistration.java
    public TeacherCourse getAssignedCourse()	{
    	return teacherCourse;
    }
    
    public Course getCourse()	{
    	return teacherCourse.getCourse();
    }
    
    public Teacher getTeacher()	{
    	return teacherCourse.getTeacher();
=======
    public TeacherClasses getTeacherCourse()	{
    	return teacherCourse;
>>>>>>> Backend/src/main/java/myProject/CourseRegistration.java
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
