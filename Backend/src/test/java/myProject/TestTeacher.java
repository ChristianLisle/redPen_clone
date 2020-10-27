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
public class TestTeacher
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
		Student s2 = new Student("Jonah D.");
		s2.id = 2;
		
		STInbox in1 = new STInbox(s1, t, "test");
		//STInbox in2 = new STInbox(s2, t, "test 2");
		
		assertEquals("Cass Z.", in1.teacher.name);
		assertEquals("Carter M.", in1.student.name);
	}
	
	
	@Test
	public void testSTMessages() {
		Teacher t = new Teacher("Cass Z.");
		t.id = 1;
		
		Student s = new Student("Carter M.");
		s.id = 1;
		
		STInbox sti = new STInbox(s, t, "test");
		sti.id = 1;
		
		STMessages stm1 = new STMessages(sti, sti.subject, t.name, "message 1");
		STMessages stm2 = new STMessages(sti, sti.subject, s.name, "message 2");
		
		ArrayList<STMessages> test = new ArrayList<STMessages>();
		test.add(stm1);
		test.add(stm2);
		
		//assertEquals(test, sti.getMessages());
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
