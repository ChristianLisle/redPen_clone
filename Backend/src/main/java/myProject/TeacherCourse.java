package myProject;

import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "assigned_course")
public class TeacherCourse {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
 
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    Teacher teacher;
 
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    Course course;
    
    @OneToMany(mappedBy = "teacherCourse")
    Set<CourseRegistration> registrations;
    
    public TeacherCourse()	{}
    
    public TeacherCourse(Teacher t, Course c){
    	this.teacher = t;
    	this.course = c;
    }
    
    public Integer getId()	{
    	return id;
    }
    
    public Teacher getTeacher()	{ 
    	return teacher;
    }
    
    public Course getCourse()	{
    	return course;
    }
    
}
