package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ProfileManagementTest {
    Initializer init = new Initializer();
	Date customerDate;
	User customer1;
	UserService newUserService;
    FlashMessageService flashserv;
	
    @Before
    public void setUp() throws Exception {
        init.prepareData();
        flashserv = new FlashMessageServiceImpl();
        newUserService = new UserService(flashserv);
    	customerDate = new Date();
		customer1 = new User("ELEFTHERIA", "TRAPEZANLIDOU",
							  "el@mail.gr", "pass!word2",
							  "Female", customerDate, "6942424242");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void AlterUserData() throws Exception {
		customer1 = newUserService.saveUser(customer1);
		assertEquals("pass!word2",newUserService.findUserById(customer1.getId()).getPassword());
    	customer1.setPassword("new12passw");
        customer1 = newUserService.saveUser(customer1);
        assertEquals("new12passw",newUserService.findUserById(customer1.getId()).getPassword());
    }

    @Test
    public void AlterUserDataError() throws Exception {
        customer1 = newUserService.saveUser(customer1);
        customer1.setFirstName(null);
        newUserService.saveUser(customer1);
        assertEquals("ELEFTHERIA",newUserService.findUserById(customer1.getId()).getFirstName());
    }
}
