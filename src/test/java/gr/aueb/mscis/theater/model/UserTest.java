package gr.aueb.mscis.theater.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {
	
	User user;

	@Before
    public void setUp() throws Exception {
		user = new User("ELEFTHERIA", "TRAPEZANLIDOU",
                "el@aueb.gr", "pass!",
                "Female", new Date(), "6942424242");
    }

    @After
    public void tearDown() throws Exception {

    }

	@Test
    public void getFirstName() throws Exception {
    	Assert.assertEquals("ELEFTHERIA", user.getFirstName());
    }

    @Test
    public void getLastName() throws Exception {
    	Assert.assertEquals("TRAPEZANLIDOU", user.getLastName());
    }
	
    @Test
    public void setFirstName() throws Exception {
    	user.setFirstName("ELINA");
    	Assert.assertEquals("ELINA", user.getFirstName());
    }

    @Test
    public void setLastName() throws Exception {
    	user.setLastName("TRAPEZALIDOU");
    	Assert.assertEquals("TRAPEZALIDOU", user.getLastName());
    }

    @Test
    public void getPassword() throws Exception {
    	Assert.assertEquals("pass!", user.getPassword());
    }
    
    @Test
    public void setPassword() throws Exception {
    	user.setPassword("password");
    	Assert.assertEquals("password", user.getPassword());
    }

//    @Test
//    public void setEmail() throws Exception {
//
//    }

    @Test
    public void passwordLessThan8() throws Exception {
    	Assert.assertFalse(user.isPasswordValid());
    }
    
    @Test
    public void passwordWithoutNumbers() throws Exception {
    	user.setEmail("password");
    	Assert.assertFalse(user.isPasswordValid());
    }
    
    @Test
    public void passwordValidSize8() throws Exception {
    	user.setEmail("pass1wor");
    	Assert.assertTrue(user.isPasswordValid());
    }
    
    @Test
    public void passwordValidSizeMoreThan8() throws Exception {
    	user.setEmail("2pass1wor");
    	Assert.assertTrue(user.isPasswordValid());
    }
    
    @Test
    public void getEmail() throws Exception {
    	Assert.assertEquals("el@aueb.gr", user.getEmail());
    }

    @Test
    public void isEqual() throws Exception {
		User newUser = new User("ELEFTHERIA", "TRAPEZANLIDOU",
                "el@aueb.gr", "pass!",
                "Female", new Date(), "6942424242");
        assertTrue(user.equals(newUser));
    }
    
    @Test
    public void notEqual() throws Exception {
		User newUser = new User("x", "y", "el@auebgr", "p","Female", new Date(), "6942424242");
        assertFalse(user.equals(newUser));
    }

    @Test
    public void notEqualEmail() throws Exception {
		User newUser = new User("ELEFTHERIA", "TRAPEZANLIDOU", "el@auebgr", "pass!","Female", new Date(), "6942424242");
        assertFalse(user.equals(newUser));
    }
    
//    @Test
//    public void getToken() throws Exception {
//
//    }
}