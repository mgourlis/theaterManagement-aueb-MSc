package gr.aueb.mscis.theater.resource;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Seat;
import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.model.Ticket;
import gr.aueb.mscis.theater.resource.DebugExceptionMapper;
import gr.aueb.mscis.theater.resource.TicketInfo;
import gr.aueb.mscis.theater.resource.TicketResource;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.PlayService;
import gr.aueb.mscis.theater.service.SerialNumberProvider;
import gr.aueb.mscis.theater.service.SerialNumberProviderImpl;
import gr.aueb.mscis.theater.service.TicketService;

public class TicketResourceTest extends TheaterResourceTest {

    FlashMessageService flashserv;
    SerialNumberProvider serialNo;
    TicketService ticketService;
    PlayService playService;
    Ticket newTicket;

    public TicketResourceTest() {
        super();
        flashserv = new FlashMessageServiceImpl();
        serialNo = new SerialNumberProviderImpl();
        ticketService = new TicketService(flashserv);
        playService = new PlayService(flashserv);
        newTicket = null;
    }

    @Override
    protected Application configure() {

        return new ResourceConfig(TicketResource.class, DebugExceptionMapper.class);
    }

    @Before
    public void setUpTicket() throws Exception {

        /*Create a ticket
         *A ticket needs show and seat*/

        /* find play Amlet*/
        List <Play> play = playService.findPlaysByTitle("Amlet");
        Assert.assertNotNull(play);

        /* find the theater program of play Amlet*/
        Set <Show> shows = play.get(0).getShows();
        assertEquals(3, shows.size());

    	/*find an available seat*/
        List<Show> showList = new ArrayList<Show>(shows);
        if (showList.get(0).getHall().isAvailable()) {
            Sector sec = showList.get(0).getHall().getSectorByName("sector1");

            List<Seat> seatList = sec.getFreeSeats(1, showList.get(0).getDate());
            assertEquals(1, seatList.size());

            Ticket ticket = new Ticket(showList.get(0), seatList.get(0), serialNo);
            seatList.get(0).addTicket(ticket);


            newTicket = ticketService.save(ticket);
            Assert.assertNotNull(newTicket);
        }
    }

    @Test
    public void testSearchTicketBySerialNo() {
        TicketInfo ticketInfo = target("tickets/"+newTicket.getSerial()).request().get(TicketInfo.class);
        Assert.assertNotEquals((Integer) 0, ticketInfo.getId());
    }

    @Test
    public void testUpdateTicket() {

        Assert.assertEquals(true, newTicket.isActive());
        newTicket.setActive(false);

        TicketInfo ticketInfo = TicketInfo.wrap(newTicket);

        Response response = target("tickets").request()
                .put(Entity.entity(ticketInfo, MediaType.APPLICATION_JSON));

        Assert.assertEquals(200, response.getStatus());
        newTicket = ticketService.findTicketBySerialNo(newTicket.getSerial());
        Assert.assertEquals(false, newTicket.isActive());
    }
}