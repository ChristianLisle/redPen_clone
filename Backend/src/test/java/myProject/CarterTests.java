package myProject;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

//Not sure how to import the teacher class from
//import Teacher;

/**
 * Test for Teacher Controller
 */
//@RunWith(SpringRunner.class)
public class CarterTests
{
	
	@InjectMocks
	private TeacherController teacher;
	
	@InjectMocks
	private ParentController parent;
	
	@InjectMocks
	private StudentController student;
	
	@Mock
	private STInboxRepository stinbox;
	
	@Mock
	private STMessagesRepository stmessages;
	
	@Mock 
	private TeacherCourseRepository tc;
	
	//to check that the properNaming() function returns the name
	//lower-cased, and then the first letter of each word is capitalized
	//Checks for this with parents and students.
	@Test
	public void testProperNaming() {
		Teacher t = new Teacher("cArTer s mOseLeY");
		
		Parent p = new Parent("sTeVe mOsELEy");
		
		assertEquals("Carter S Moseley", t.properNaming(t.name));
		
		assertEquals("Steve Moseley", p.properNaming(p.name));
	}
	
	//Tests to check and make sure that when an inbox is made, it 
	//stores the right name
	@Test
	public void testSTInbox() {
		Teacher t = new Teacher("Cass Z.");
		t.id = 1;
		
		Student s1 = new Student("Carter M.");
		s1.id = 1;
		
		STInbox in1 = new STInbox(s1, t, "test");
		
		assertEquals("Cass Z.", in1.teacher.name);
		assertEquals("Carter M.", in1.student.name);
		
		s1.setName("Jonah D");
		
		assertEquals("Jonah D", in1.student.name);
	}
	
	//Tests to make sure that it returns the right parent when
	//the parent changes name or it changes parent
	//Makes sure the getParent() works
	@Test
	public void testAddingParentToStudent() {
		Parent p1 = new Parent("Cass Z.");
		p1.id = 1;
		
		Parent p2 = new Parent("Dan B.");
		p2.id = 2;

		Student s = new Student("Carter M.");
		s.id = 1;
		
		s.setParent(p1);
		
		assertEquals(p1, s.getParent());
		
		p1.setName("Cass Y.");
		
		assertEquals("Cass Y.", s.getParent().name);
		
		s.setParent(p2);
		
		assertEquals(p2, s.getParent());
	}
    
}
