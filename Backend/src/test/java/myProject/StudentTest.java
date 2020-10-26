package myProject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.CoreMatchers.is;

/*
 * Tests Student entity, StudentController controller, and StudentRepository repository
 */
public class StudentTest {

	@InjectMocks
	private StudentController studentController;

	@Mock
	private StudentRepository students;
	
	@Mock
	private CourseRepository courses;
	
	@Mock
	private CourseRegistrationRepository registrar;
	
	@Mock
	private TeacherCourseRepository assignedCourses;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testStudentController() {
		Student s = new Student("A student");
		s.id = 1;

		when(students.findOne(1)).thenReturn(s);

		assertThat(studentController.getStudent(1).getId(), is(1));
	}

	@Test
	public void testResetPassword() {
		Student s = new Student("Steve Smith");
		s.password = "password";
		s.id = 2;

		when(students.findOne(2)).thenReturn(s);

		assertThat(studentController.getStudent(2).getPassword(), is("password"));

		// fail reset password
		assertThat(studentController.resetPassword(new NewPassword("wrongOP", "NP!"), 2), is("Password not reset."));

		// Check that password has not changed
		assertThat(studentController.getStudent(2).getPassword(), is("password"));

		// successful reset password
		studentController.resetPassword(new NewPassword("password", "newPassword!"), 2);
		assertThat(studentController.getStudent(2).getPassword(), is("newPassword!"));
	}

	@Test
	public void getAllStudents() {
		// Test getAllStudents on empty student repository
		List<Student> studentList = studentController.getAllStudents();
		assertThat(studentList.size(), is(0));

		// Cannot text to ensure that getAllStudents works on non-empty student
		// repository
		// see: https://stackabuse.com/how-to-test-a-spring-boot-application/ for more
		// info
	}
	
	@Test
	public void registerForCourse()	{
		// Construct Student
		Student s = new Student("Christian Lisle");
		s.id = 3;
		when(students.findOne(3)).thenReturn(s);
		
		// Construct a TeacherCourse (a course that has a teacher assigned to it)
		Teacher t = new Teacher("Simanta Mitra");
		Course c = new Course("COM S 309", "Software Development Practices");
		c.id = 1;
		when(courses.findOne(1)).thenReturn(c);
		TeacherCourse assignedCourse = new TeacherCourse(t, c);
		assignedCourse.id = 1;
		when(assignedCourses.findOne(1)).thenReturn(assignedCourse);
		
		// Register student for TeacherCourse
		CourseRegistration cr = studentController.registerCourse(3, 1);
		when(registrar.findOne(0)).thenReturn(cr);
		
		assertThat(registrar.findOne(0), is(cr));
		
	}
}
