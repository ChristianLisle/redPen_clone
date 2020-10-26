package myProject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

//Import Java libraries
import java.util.List;
import java.util.ArrayList;

//import junit/spring tests
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;

//import mockito related
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

//Not sure how to import the teacher class from
//import Teacher;

/**
 * Test for Teacher Controller
 */
@RunWith(SpringRunner.class)
public class TestTeacher
{
	
	//tests of proper naming
	@Test
	public void testProperNaming() {
		Teacher t = new Teacher();
		
		assertEquals("Carter Moseley Hello", t.properNaming("carter moseley hello"));
	}
    
}
