package myProject;

import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "teacher", "course"})
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
    
    /**
     * Gets the id of the teacherCourse
     * 
     * @return id
     */
    public Integer getId()	{
    	return id;
    }
    
    /**
     * Gets the teacher of the teacherCourse
     * 
     * @return teacher
     */
    public Teacher getTeacher()	{ 
    	return teacher;
    }
    
    /**
     * Gets the course of the teacherCourse
     * 
     * @return course
     */
    public Course getCourse()	{
    	return course;
    }
    
}
