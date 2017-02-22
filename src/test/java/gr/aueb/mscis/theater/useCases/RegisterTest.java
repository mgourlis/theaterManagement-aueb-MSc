package gr.aueb.mscis.theater.useCases;

import java.util.Date;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.*;

/**
 * Created by Myron on 19/2/2017.
 */
public class RegisterTest {
    Initializer init = new Initializer();
	Date customerDate;
	User customer1;
	User customer2;
	
	@Before
    public void setUp() throws Exception {
        init.prepareData();
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
		UserService newUserService = new UserService(JPAUtil.createEntityManager());
		newUserService.saveUser(customer1);
    	Assert.assertEquals(customer1, newUserService.findUserByEmail("pass!word2"));
    }

    @Test
    public void RegisterCustomerRequiredFields() throws Exception {

    }

    @Test
    public void RegisterCustomerEmailExists() throws Exception {
    	UserService newUserService = new UserService(JPAUtil.createEntityManager());
		newUserService.saveUser(customer1);
		customer2.setPassword("pass!word2");
		Assert.assertNull(newUserService.saveUser(customer2));
    }

    @Test
    public void RegisterCustomerWeakPassword() throws Exception {

    }
}
