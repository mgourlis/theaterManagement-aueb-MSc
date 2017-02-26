package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class RegisterTest {
    Initializer init = new Initializer();
	Date customerDate;
	User customer1;
	User customer2;
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
		customer2 = new User("NIKOS", "PAPADOPOULOS",
				   			 "nikos@mail.gr", "nik1os567",
				   			 "Male", customerDate, "6942424242");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void RegisterCustomer() throws Exception {
		newUserService.saveUser(customer1);
    	Assert.assertEquals(customer1, newUserService.findUserByEmail("el@mail.gr"));
    }

    @Test
    public void RegisterCustomerRequiredFields() throws Exception {
        customer1.setFirstName(null);
        customer1 = newUserService.saveUser(customer1);
        Assert.assertNull(customer1);

    }

    @Test
    public void RegisterCustomerEmailExists() throws Exception {
		newUserService.saveUser(customer1);
		customer2.setEmail("el@mail.gr");
		Assert.assertNull(newUserService.saveUser(customer2));
    }

    @Test
    public void RegisterCustomerWeakPassword() throws Exception {
        customer1.setPassword("12345");
        Assert.assertNull(newUserService.saveUser(customer1));
    }
}
