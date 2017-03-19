package gr.aueb.mscis.theater.Resource;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.resource.DebugExceptionMapper;
import gr.aueb.mscis.theater.resource.HallInfo;
import gr.aueb.mscis.theater.resource.HallResource;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.HallService;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.List;

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
		HallInfo hall = target("hall/"+halls.get(0).getId()).request().get(HallInfo.class);
		Assert.assertEquals(halls.get(0).getName(), hall.getName());

	}
}