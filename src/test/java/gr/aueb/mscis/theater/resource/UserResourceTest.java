package gr.aueb.mscis.theater.resource;


import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.model.UserType;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.UserService;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.List;

public class UserResourceTest extends TheaterResourceTest {

    public UserResourceTest() {
        super();
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class, DebugExceptionMapper.class);
    }

    @Before
    public void setUserTestUp() throws Exception {
        //dataHelper = new Initializer();
        //dataHelper.prepareData();

        FlashMessageService flashserv = new FlashMessageServiceImpl();
        UserService newUserService = new UserService(flashserv);
        Date customerDate = new Date();
        User customer1 = new User("ELEFTHERIA", "TRAPEZANLIDOU",
                "el@mail.gr", "pass!word2",
                "Female", customerDate, "6942424242");
        User customer2 = new User("Myron", "Gourlis",
                "myr@mail.gr", "pass!word1",
                "Male", customerDate, "6969696969");
        User employee1 = new User("Kwstas", "Zarodimos",
                "kwst@mail.gr", "pass!word0", UserType.TechnicalDirector);

        newUserService.saveUser(customer1);
        newUserService.saveUser(customer2);
        newUserService.saveUser(employee1);
    }

    @Test
    public void testGetallCustomers() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        List<User> users;
        users = userService.findAllCustomers();

        List<UserInfo> userInfos = target("user/customers").request().get(new GenericType<List<UserInfo>>() {
        });

        Assert.assertEquals(2,userInfos.size());
        Assert.assertEquals(users.size(),userInfos.size());
    }

    @Test
    public void testGetallEmployees() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        List<User> users;
        users = userService.findAllEmployees();

        List<UserInfo> userInfos = target("user/employees").request().get(new GenericType<List<UserInfo>>() {
        });

        Assert.assertEquals(1,userInfos.size());
        Assert.assertEquals(users.size(),userInfos.size());
    }

    @Test
    public void testGetUser() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserByEmail("myr@mail.gr");

        Assert.assertNotNull(user);

        UserInfo userInfo = target("user/"+user.getEmail()).request().get(UserInfo.class);

        Assert.assertEquals(user.getId(),userInfo.getId());
    }

    @Test
    public void testCreateCustomer() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserByEmail("myr@mail.gr");

        Assert.assertNotNull(user);

        UserInfo newuser = new UserInfo(user);
        newuser.setEmail("myr2@mail.gr");
        newuser.setCategory(null);

        Response resp = target("user/customer").request().post(Entity.entity(newuser, MediaType.APPLICATION_JSON));

        Assert.assertEquals(201, resp.getStatus());

        User newdbuser = userService.findUserByEmail("myr2@mail.gr");

        Assert.assertNotNull(newdbuser);
        Assert.assertEquals("myr2@mail.gr",newdbuser.getEmail());
        Assert.assertEquals(UserType.Customer,newdbuser.getUserCategory().getCategory());
    }

    @Test
    public void testCreateEmployee() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserByEmail("kwst@mail.gr");

        Assert.assertNotNull(user);

        UserInfo newuser = new UserInfo(user);
        newuser.setEmail("myr3@mail.gr");
        newuser.setCategory(null);

        Response resp = target("user/employee").queryParam("type",UserType.ArtDirector).request().post(Entity.entity(newuser, MediaType.APPLICATION_JSON));

        Assert.assertEquals(201, resp.getStatus());

        User newdbuser = userService.findUserByEmail("myr3@mail.gr");

        Assert.assertNotNull(newdbuser);
        Assert.assertEquals("myr3@mail.gr",newdbuser.getEmail());
        Assert.assertEquals(UserType.ArtDirector,newdbuser.getUserCategory().getCategory());
    }

    @Test
    public void testUpdateUser() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserByEmail("myr@mail.gr");

        Assert.assertNotNull(user);

        UserInfo uptuser = new UserInfo(user);
        uptuser.setLastName("test");

        Response resp = target("user/"+uptuser.getEmail())
                .request().put(Entity.entity(uptuser, MediaType.APPLICATION_JSON));



        Assert.assertEquals(200, resp.getStatus());

        User uptdbuser = userService.findUserByEmail("myr@mail.gr");
        EntityManager em = JPAUtil.getCurrentEntityManager();
        em.refresh(uptdbuser);

        Assert.assertNotNull(uptdbuser);
        Assert.assertEquals("test",uptdbuser.getLastName());

        em.close();
    }

    @Test
    public void testDeleteUser() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserByEmail("myr@mail.gr");

        Assert.assertNotNull(user);

        Response resp = target("user/"+user.getEmail()).request().delete();

        Assert.assertEquals(200,resp.getStatus());

        UserService userv = new UserService(flashserv);

        user = userv.findUserByEmail("myr@mail.gr");

        Assert.assertNull(user);
    }

    @Test
    public void testLoginUser() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserByEmail("myr@mail.gr");

        Assert.assertNotNull(user);
        Assert.assertNull(user.getToken());

        Form f = new Form();
        f.param("email", user.getEmail());
        f.param("password", user.getPassword());

        Response resp = target("user/login").request().post(Entity.entity(f,MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        EntityManager em = JPAUtil.getCurrentEntityManager();
        em.refresh(user);

        Assert.assertEquals(200,resp.getStatus());
        Assert.assertNotNull(user.getToken());

        em.close();
    }

    @Test
    public void testLogoutUser() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserByEmail("myr@mail.gr");

        Form f = new Form();
        f.param("email", user.getEmail());
        f.param("password", user.getPassword());

        Response resp = target("user/login").request().post(Entity.entity(f,MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        EntityManager em = JPAUtil.getCurrentEntityManager();
        em.refresh(user);

        Assert.assertEquals(200,resp.getStatus());
        Assert.assertNotNull(user.getToken());

        resp = target("user/logout/"+user.getEmail()).queryParam("token",user.getToken()).request().get();

        em.refresh(user);

        Assert.assertEquals(200,resp.getStatus());
        Assert.assertNull(user.getToken());

        em.close();

    }

    @Test
    public void testIsAuthenicated() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserByEmail("myr@mail.gr");

        Form f = new Form();
        f.param("email", user.getEmail());
        f.param("password", user.getPassword());

        Response resp = target("user/login").request().post(Entity.entity(f,MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        EntityManager em = JPAUtil.getCurrentEntityManager();
        em.refresh(user);

        Assert.assertEquals(200,resp.getStatus());
        Assert.assertNotNull(user.getToken());

        Boolean isloggedIn = target("user/authenticated/"+user.getEmail()).queryParam("token",user.getToken()).request().get(Boolean.class);

        em.refresh(user);

        Assert.assertEquals(200,resp.getStatus());
        Assert.assertNotNull(user.getToken());

        Assert.assertTrue(isloggedIn);

        em.close();

    }
}