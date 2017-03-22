package gr.aueb.mscis.theater.resource;


import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class ShowResourceTest extends TheaterResourceTest {

    public ShowResourceTest() {
        super();
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(ShowResource.class, DebugExceptionMapper.class);
    }

    @Test
    public void testSearchShowByName() {

    }

    @Test
    public void testGetFreeSeats() {

    }

    @Test
    public void testUpdateShow() {

    }
}