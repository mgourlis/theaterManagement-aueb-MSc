package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.*;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.service.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

//import org.junit.*;

public class PurchaseTicketTest {
    Initializer init = new Initializer();
	PlayService playService;
    UserService userService;
    TicketService ticketService;
    HallService hallService;
    FlashMessageService flashserv;
    User user;

    @Before
    public void setUp() throws Exception {
        init.prepareData();
        flashserv = new FlashMessageServiceImpl();
		userService = new UserService(flashserv);
		ticketService = new TicketService(flashserv);

		user = new User("ELEFTHERIA", "TRAPEZANLIDOU",
                		"eleftheria@aueb.gr", "pass!wo12",
                		"Female", new Date(), "6942424242");

		user.setToken("token123");
		userService.saveUser(user);
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
        List<Ticket> tickets = new ArrayList<Ticket>();
        User customer = new User();
        Ticket ticket = null;

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

        	        /*validate that there were no tickets for these seats*/
        	        for (int i=0; i<3; i++) {
            	    	assertEquals(0, freeSeats.get(i).getTickets().size());
        	        }

        	        for (int k=0; k<3; k++) {
        	        	ticket = new Ticket(s, freeSeats.get(k), serialNo);
        	        	freeSeats.get(k).addTicket(ticket);
        	        	tickets.add(ticket);
        	        	ticket = null;
        	        }

        	        /*validate that there is one ticket for each seat*/
        	        for (int i=0; i<3; i++) {
            	    	assertEquals(1, freeSeats.get(i).getTickets().size());
        	        }

          	        /*allocate the 3 seats*/
        	        //poia diadikasia to kanei ayto?

        	        /*validate that the customer is signed in*/
        	        customer = userService.findUserByEmail("eleftheria@aueb.gr");
        	        Assert.assertNotNull(customer);
        	        Assert.assertEquals("token123", customer.getToken());

        	        /*ta teleytaia vimata tou useCase einai ta parakatw*/
        	        /*logika gia to 15 kai 17 den prepei na kanoyme kati*/
        	        /*alla gia to 16?*/

        	        //15. Το σύστημα εμφανίζει μήνυμα ολοκλήρωσης αγοράς εισιτηρίων.

        	        //16. Το σύστημα καταχωρεί τα στοιχεία των εισιτηρίων.
        	        //Eftiaksa sto purchase kai sto ticket Service ta antistoixa save
        	        //einai swsto to parakatw?
        	        for (int i=0; i<3; i++) {
        	        	ticket = ticketService.save(tickets.get(i));
            	    	Assert.assertEquals(tickets.get(i), ticket);
            	    	ticket = null;
        	        }

        	        //17. Το σύστημα αποστέλλει email με τα στοιχεία του εισιτηρίου και της αγοράς στον πελάτη με
        	        //      τη χρήση του Διακομιστή Ηλ. Ταχ.
                    EmailProvider emailserv = new EmailProviderStub();
        	        emailserv.sendEmail(user.getEmail(),"purchase mail with ticket data");
        		}
        	}
        }
    }
}
