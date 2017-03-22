package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.service.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class AuthenticateTest {
    Initializer init = new Initializer();
    Date customerDate;
    User customer1;
    UserService userv;
    AuthenticateService authserv;
    FlashMessageService flashserv;
    SerialNumberProvider serialprov;

    @Before
    public void setUp() throws Exception {
        init.prepareData();
        flashserv = new FlashMessageServiceImpl();
        userv = new UserService(flashserv);
        customerDate = new Date();
        customer1 = new User("ELEFTHERIA", "TRAPEZANLIDOU",
                "el@mail.gr", "password12",
                "Female", customerDate, "6942424242");
        userv.saveUser(customer1);
        serialprov = new SerialNumberProviderImpl();
        authserv = new AuthenticateService(flashserv,serialprov);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void LoginUser() throws Exception {
        Assert.assertNotNull(authserv.login(customer1.getEmail(),customer1.getPassword()));
        User u = userv.findUserById(customer1.getId());
        Assert.assertNotNull(u.getToken());
        Assert.assertEquals(u,authserv.isAuthenticated(u.getId(),u.getToken()));
    }

    @Test
    public void LoginUserError() throws Exception {
        Assert.assertNull(authserv.login(customer1.getEmail(),customer1.getPassword()+"123"));
        User u = userv.findUserById(customer1.getId());
        Assert.assertNull(u.getToken());
        Assert.assertNull(authserv.isAuthenticated(u.getId(),u.getToken()));
    }

    @Test
    public void LogoutUser() throws Exception {
        customer1 = authserv.login(customer1.getEmail(),customer1.getPassword());
        Assert.assertNotNull(authserv.isAuthenticated(customer1.getId(),customer1.getToken()));
        authserv.logout(customer1.getId(),customer1.getToken());
        Assert.assertNull(authserv.isAuthenticated(customer1.getId(),customer1.getToken()));

    }
}
