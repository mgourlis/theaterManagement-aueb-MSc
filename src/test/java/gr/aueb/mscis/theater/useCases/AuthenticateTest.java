package gr.aueb.mscis.theater.useCases;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.AuthenticateService;
import gr.aueb.mscis.theater.service.UserService;

public class AuthenticateTest {

    Initializer init = new Initializer();
    AuthenticateService authUser;
    UserService userService;
    User user;
    
    @Before
    public void setUp() throws Exception {
        init.prepareData();
        authUser = new AuthenticateService();
		userService = new UserService(JPAUtil.getCurrentEntityManager());

		user = new User("ELEFTHERIA", "TRAPEZANLIDOU",
                		"eleftheria@aueb.gr", "pass!wo12",
                		"Female", new Date(), "6942424242");

		userService.saveUser(user);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void LoginUser() throws Exception {
    	User userFound = null;
    	userFound = authUser.validateUser("eleftheria@aueb.gr", "pass!wo12");
    	Assert.assertEquals(user, userFound);
    }

    @Test
    public void LoginWrongPassword() throws Exception {
    	User userFound = null;
    	userFound = authUser.validateUser("eleftheria@aueb.gr", "pass!wo1");
    	Assert.assertNull(userFound);
    }

    @Test
    public void LoginUnknownEmail() throws Exception {
    	User userFound = null;
    	userFound = authUser.validateUser("elefther@aueb.gr", "pass!wo12");
    	Assert.assertNull(userFound);
    }
    
    @Test
    public void LogoutUser() throws Exception {

    }
}
