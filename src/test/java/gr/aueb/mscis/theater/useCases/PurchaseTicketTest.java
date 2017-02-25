package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.*;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.service.PlayService;
import gr.aueb.mscis.theater.service.SerialNumberProvider;
import gr.aueb.mscis.theater.service.SerialNumberProviderImpl;
import gr.aueb.mscis.theater.service.UserService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

//import org.junit.*;

public class PurchaseTicketTest {
    Initializer init = new Initializer();
	PlayService playService;
    UserService userService;
    User user;
    
    @Before
    public void setUp() throws Exception {
        init.prepareData();
		user = new User("ELEFTHERIA", "TRAPEZANLIDOU",
                "el@aueb.gr", "pass!",
                "Female", new Date(), "6942424242");
		userService.newUser(user);
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
        User customer = new User();
        
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
        	if (cal.getTime().compareTo(s.getDate()) == 0) {

				/* if there are available seats that day*/
        		if (s.getHall().isAvailable()) {
        			sec = s.getHall().getSectorByName("sector1");
        			
        			/*find 3 available seats*/
        			freeSeats = sec.getFreeSeats(3, s.getDate());        	    	
        	    	assertEquals(3, freeSeats.size());
        	    	
        	        customer = userService.findUserByEmail("el@aueb.gr");        	        
        	        Assert.assertNotNull(customer);
        	        
        	        Assert.assertEquals(UserType.Customer, customer.getCategory());
        	        
        	        /*validate that there were no tickets for these seats*/
        	        for (int i=0; i<3; i++) {
            	    	assertEquals(0, freeSeats.get(i).getTickets().size());
        	        }
        	        
        	        for (int k=0; k<3; k++) {
        	        	Ticket ticket = new Ticket(s, freeSeats.get(k), serialNo);
        	        	freeSeats.get(k).addTicket(ticket);
        	        	ticket = null;
        	        }
      	        
        	        /*validate that there is one ticket for each seat*/
        	        for (int i=0; i<3; i++) {
            	    	assertEquals(1, freeSeats.get(i).getTickets().size());
        	        }

        	        customer.getToken();
        	        
        	        
        	        
        	        
//        	        for (int i=0; i<3; i++) {
//        	        	freeSeats.get(0).getTickets();
//        	        	ticket = null;
//        	        }
        	        
        		}
        			
        	}
        }
    }
    
    
}
