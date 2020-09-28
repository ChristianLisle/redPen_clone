package myProject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "assignment")
public class Assignment {

    @Id
    private int id;

    private String name;

    private String assignmentDesc;
    
    private int grade;
    
    public int getId() {
        return id;
    }
    
    public void setId(Integer id)	{
    	this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name)	{
    	this.name = name;
    }
    
    public String getAssignmentDescription()	{
    	return assignmentDesc;
    }
    
    public void setAssignmentDescription(String description)	{
    	this.assignmentDesc = description;
    }
    
    public int getGrade()	{
    	return grade;
    }
    
    public void setGrade(int grade)	{
    	this.grade = grade;
    }
    
}
