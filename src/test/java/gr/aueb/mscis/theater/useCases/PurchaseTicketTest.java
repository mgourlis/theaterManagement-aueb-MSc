package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.*;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.service.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

//import org.junit.*;

public class PurchaseTicketTest {
    Initializer init = new Initializer();
    PlayService playService;
    FlashMessageService flashserv;


    @Before
    public void setUp() throws Exception {
        init.prepareData();
        flashserv = new FlashMessageServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void PurchaseByCustomer() throws Exception {
        playService = new PlayService(flashserv);
        Sector sec = new Sector();
        Calendar cal = Calendar.getInstance();
        List<Play> play = null;
        Set<Show> shows = null;
        List<Seat> freeSeats = null;
        SerialNumberProvider serialNo = new SerialNumberProviderImpl();

        cal.add(Calendar.DATE,3);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);


        /* find play Amlet*/
        play = playService.findPlaysByTitle("Amlet");
        Assert.assertNotNull(play);

        /* find the theater program of play Amlet*/
        shows = play.get(0).getShows();
        assertEquals(3, shows.size());

        /* find a specific day*/
        for (Show s:shows) {
            if (cal.getTime().compareTo(s.getDate()) == 0) { // Μάλλον υπάρχει θέμα μετατροπής μεταξύ Date και Calendar!!!!

				/* if there are available seats that day*/
                if (s.getHall().isAvailable()) {
                    sec = s.getHall().getSectorByName("sector1");

        			/*find 3 available seats*/
                    freeSeats = sec.getFreeSeats(3, s.getDate());
                    Assert.assertEquals(Integer.valueOf(3), Integer.valueOf(freeSeats.size()));

        	        /*validate that there were no tickets for these seats*/
                    for (int i=0; i<3; i++) {
                        assertEquals(0, freeSeats.get(i).getTickets().size());
                        //Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(freeSeats.get(i).getTickets().size()));
                    }

                    for (int k=0; k<3; k++) {
                        Ticket ticket = new Ticket(s, freeSeats.get(k), serialNo);
                        freeSeats.get(k).addTicket(ticket);
                    }

        	        for (int i=0; i<3; i++) {
        	        	freeSeats.get(0).getTickets();
                        assertEquals(1, freeSeats.get(i).getTickets().size());
        	        }

                }
            }
        }
    }
}
