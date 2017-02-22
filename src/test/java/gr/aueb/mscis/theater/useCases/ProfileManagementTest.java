package gr.aueb.mscis.theater.useCases;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.UserService;

/**
 * Created by Myron on 19/2/2017.
 */
public class ProfileManagementTest {
    Initializer init = new Initializer();
	Date customerDate;
	User customer1;
	
    @Before
    public void setUp() throws Exception {
        init.prepareData();
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
		UserService newUserService = new UserService(JPAUtil.createEntityManager());
		newUserService.saveUser(customer1);
    	customer1.setPassword("new12passw");
    }

    @Test
    public void AlterUserDataError() throws Exception {

    }
}
