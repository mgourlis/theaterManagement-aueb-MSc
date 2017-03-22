package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.HallService;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class HallResourceTest extends TheaterResourceTest {

	public HallResourceTest() {
		super();
	}
	
	@Override
	protected Application configure() {
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

	@Test
	public void testGetAllHalls() {
		FlashMessageService flashserv = new FlashMessageServiceImpl();

		HallService hallService = new HallService(flashserv);
		List<Hall> halls;
		halls = hallService.findAllHalls();

		List<HallInfo> hallInfos = target("hall").request().get(new GenericType<List<HallInfo>>() {
		});

		Assert.assertEquals(halls.size(),hallInfos.size());
	}

	@Test
	public void testCreateHall() {
		FlashMessageService flashserv = new FlashMessageServiceImpl();

		HallService hallService = new HallService(flashserv);
		List<Hall> halls = hallService.findAllHalls();

		Hall clonehall = halls.get(0);

		HallInfo postreq = new HallInfo(clonehall,true);

		postreq.setName("Created from post request");
		postreq.setId(null);
		List<SectorInfo> secinfos = postreq.getsectors();
		for (SectorInfo sec : secinfos){
			sec.setId(null);
			List<SeatInfo> seatinfos = sec.getSeats();
			for(SeatInfo seat : seatinfos){
				seat.setId(null);
			}
		}

		Response resp = target("hall").request().post(Entity.entity(postreq, MediaType.APPLICATION_JSON));

		List<Hall> newhalls = hallService.findAllHalls();

		Assert.assertEquals(halls.size()+1,newhalls.size());

		Hall newhall = hallService.findHallByName("Created from post request");

		Assert.assertNotNull(newhall);
	}

	@Test
	public void testUpdateHall(){
		FlashMessageService flashserv = new FlashMessageServiceImpl();

		HallService hallService = new HallService(flashserv);
		List<Hall> halls = hallService.findAllHalls();

		Hall upthall = hallService.findHallByName("hall1");

		HallInfo postreq = new HallInfo(upthall,true);

		postreq.setName("Updated from put request");

		Response resp = target("hall/"+Integer.toString(postreq.getId())).request().put(Entity.entity(postreq, MediaType.APPLICATION_JSON));

		Assert.assertEquals(200,resp.getStatus());

		List<Hall> upthalls = hallService.findAllHalls();

		upthall = hallService.findHallByName("Updated from put request");

		EntityManager em = JPAUtil.getCurrentEntityManager();
		em.refresh(upthall);
		em.close();

		Assert.assertEquals(halls.size(),upthalls.size());

		Assert.assertEquals("Updated from put request",upthall.getName());
	}

	@Test
	public void testDeleteHall(){
		FlashMessageService flashserv = new FlashMessageServiceImpl();

		HallService hallService = new HallService(flashserv);
		List<Hall> halls = hallService.findAllHalls();

		Hall delhall = hallService.findHallByName("hall2");

		Response resp = target("hall/"+Integer.toString(delhall.getId())).request().delete();

		Assert.assertEquals(200,resp.getStatus());

		List<Hall> delhalls = hallService.findAllHalls();

		Assert.assertEquals(delhalls.size(),halls.size()-1);

	}
}