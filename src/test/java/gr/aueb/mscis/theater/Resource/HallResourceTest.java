package gr.aueb.mscis.theater.Resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import javax.ws.rs.client.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.resource.DebugExceptionMapper;
import gr.aueb.mscis.theater.resource.HallInfo;
import gr.aueb.mscis.theater.resource.HallResource;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.HallService;

public class HallResourceTest extends TheaterResourceTest {

	public HallResourceTest() {
		super();
	}
	
	@Override
	protected Application configure() {
		/*
		 * 
		 */
		return new ResourceConfig(HallResource.class, DebugExceptionMapper.class);
	}
	
	@Test
	public void testSearchHallById() {
		
        FlashMessageService flashserv = new FlashMessageServiceImpl();
        
		HallService hallService = new HallService(flashserv);
		List<Hall> halls;
		
		halls = hallService.findAllHalls();
		Assert.assertEquals(2,halls.size());
		HallInfo hall = target("hall/9").request().get(HallInfo.class);
		Assert.assertEquals(halls.get(0).getName(), hall.getName());

	}
}