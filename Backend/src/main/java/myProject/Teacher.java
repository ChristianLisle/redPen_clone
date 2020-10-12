package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import javax.persistence.*;


@Entity
public class Teacher {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    
    @Column
    String name;
    
    @OneToMany(mappedBy = "teacher")
    Set<TeacherClasses> teacherClasses;
    
    public Teacher()	{}
    
    public Teacher(String name) {
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
    
}