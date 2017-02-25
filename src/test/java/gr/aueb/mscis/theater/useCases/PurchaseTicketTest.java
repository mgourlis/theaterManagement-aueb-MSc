package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.Ticket;
import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Seat;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.PurchaseService;
import gr.aueb.mscis.theater.service.SerialNumberProvider;
import gr.aueb.mscis.theater.service.SerialNumberProviderImpl;
import gr.aueb.mscis.theater.service.PlayService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

//import org.junit.*;

public class PurchaseTicketTest {
    Initializer init = new Initializer();
	PlayService playService;

    
    @Before
    public void setUp() throws Exception {
        init.prepareData();
    }

    @After
    public void tearDown() throws Exception {

    }
    
    @Test
    public void PurchaseByCustomer() throws Exception {
    	playService = new PlayService();
    	Sector sec = new Sector();
        Calendar cal = Calendar.getInstance();
    	List<Play> play = null;
    	Set<Show> shows = null;
    	List<Seat> freeSeats = null;
        SerialNumberProvider serialNo = new SerialNumberProviderImpl();
        
        cal.set(2017, Calendar.AUGUST, 25);
    	
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
        			sec = s.getHall().getSectorByName("hall1sector1");
        			
        			/*find 3 available seats*/
        			freeSeats = sec.getFreeSeats(3, s.getDate());
        	    	Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(freeSeats.size()));

        	        /*validate that there were no tickets for these seats*/
        	        for (int i=0; i<3; i++) {
        	        	//assertEquals(1, freeSeats.get(i).getTickets().size());
            	    	Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(freeSeats.get(i).getTickets().size()));
        	        }
        	        
        	        for (int k=0; k<3; k++) {
        	        	Ticket ticket = new Ticket(s, freeSeats.get(k), serialNo);
        	        	freeSeats.get(k).addTicket(ticket);
        	        	ticket = null;
        	        }
        	        
//        	        for (int i=0; i<3; i++) {
//        	        	freeSeats.get(0).getTickets();
//        	        	ticket = null;
//        	        }
        	        
        		}
        			
        	}
        }
    }
    
    
}
