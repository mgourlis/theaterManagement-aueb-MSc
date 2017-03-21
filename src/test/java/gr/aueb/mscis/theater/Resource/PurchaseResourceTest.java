package gr.aueb.mscis.theater.Resource;

import gr.aueb.mscis.theater.model.*;
import gr.aueb.mscis.theater.resource.DebugExceptionMapper;
import gr.aueb.mscis.theater.resource.NewPurchaseInfo;
import gr.aueb.mscis.theater.resource.PurchaseResource;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.PlayService;
import gr.aueb.mscis.theater.service.UserService;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PurchaseResourceTest extends TheaterResourceTest {

	public PurchaseResourceTest() {
		super();
	}
	
	@Override
	protected Application configure() {

        return new ResourceConfig(PurchaseResource.class, DebugExceptionMapper.class);
    }

    @Test
    public void testCreateNewPurchase() {

        Integer showId, userId;
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        //find showId by Play "Amlet"
        PlayService playService = new PlayService(flashserv);
        List<Play> plays = playService.findPlaysByTitle("Amlet");
        Assert.assertEquals(1, plays.size());
    
        Set<Show> shows = plays.get(0).getShows();
        Assert.assertEquals(3, shows.size());

        List<Show> showList = new ArrayList<Show>(shows);
        showId = showList.get(0).getId();

        //find userId //there is no user in Initializer
        UserService userv = new UserService(flashserv);
        Date date = new Date();
        User tmp_user = new User("ELEFTHERIA", "TRAPEZANLIDOU",
                                 "el@mail.gr", "password12",
                                 "Female", date, "6942424242");
        userv.saveUser(tmp_user);
    
        User user = userv.findUserByEmail("el@mail.gr");
        Assert.assertNotEquals((Integer) 0, user.getId());
        userId = user.getId();

        //find seatIds
        //Show->Hall->Sector->Seats
        Set<Sector> sectors = showList.get(0).getHall().getSectors();
        List<Sector> sectorList = new ArrayList<Sector>(sectors);
        Assert.assertEquals(3, sectorList.size());

        List<Seat> seatList = sectorList.get(0).getFreeSeats(date);
        Assert.assertEquals(6, seatList.size());

        List<Integer> seatIds = new ArrayList<Integer>();
        seatIds.add(seatList.get(0).getId());
        seatIds.add(seatList.get(1).getId());
        seatIds.add(seatList.get(2).getId());
        Assert.assertEquals(3, seatIds.size());

        NewPurchaseInfo newPurchaseInfo = new NewPurchaseInfo(showId, userId, seatIds);
    
        Response response = target("purchase").request().post(Entity.entity(newPurchaseInfo, MediaType.APPLICATION_JSON));
    
        Assert.assertEquals(response.getStatus(), Status.CREATED.getStatusCode());
        String location = response.getHeaderString(HttpHeaders.LOCATION);
        Assert.assertNotNull(location);

        //mporw na traviksw ta tickets kai to purchase apo ti vasi gia na dw oti ftiaxtikan...
    }
}
