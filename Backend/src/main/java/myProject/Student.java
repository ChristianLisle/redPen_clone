package myProject;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private int id;
    
    private String name;
    
    private int age;


    /*  One Student may have multiple courses assigned. So 	One-to-Many mapping. Eager fetching type is
        to load together everything. LAZY fetch type is on-demand fetching. For simplicity, use EAGER.
        @JoinColumn annotation is used here to join list of courses with id of the student. So, course table
        will have a column called "id" corresponding to "id" of TA
        it is associated to (Automatically created by SpringBoot).
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private List<Course> courses;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public int getAge()	{
    	return age;
    }
    
    public void setAge(int age)	{
    	this.age = age;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
