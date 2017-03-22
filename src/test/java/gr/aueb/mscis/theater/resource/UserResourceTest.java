package gr.aueb.mscis.theater.resource;


import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class UserResourceTest extends TheaterResourceTest {

    public UserResourceTest() {
        super();
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class, DebugExceptionMapper.class);
    }

    @Test
    public void testGetallCustomers() {

    }

    @Test
    public void testGetallEmployees() {

    }

    @Test
    public void testGetUser() {

    }

    @Test
    public void testCreateCustomer() {

    }

    @Test
    public void testCreateEmployee() {

    }

    @Test
    public void testUpdateCustomer() {

    }

    @Test
    public void testUpdateEmployee() {

    }

    @Test
    public void testDeleteUser() {

    }

    @Test
    public void testLoginUser() {

    }

    @Test
    public void testLogoutUser() {

    }

    @Test
    public void testIsAuthenicated() {

    }
}