package gr.aueb.mscis.theater.model;

import gr.aueb.mscis.theater.service.SerialNumberProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PurchaseTest {
	
	User user1;
	User user2;
	Purchase purchase;
	private Date testDate;
	
    Hall hall;
    Play play;
    Show show;
    Date date;
    Sector sector;
    Ticket ticket1;
    Ticket ticket2;
    Date currentDate;
    SerialNumberProvider serial;

	@Before
    public void setUp() throws Exception {
		testDate = new Date();
		purchase = new Purchase(testDate, "tameio", 3, 45.50);
		user1 = new User("user_fname", "user_lname", "email@aueb.gr", "passw0rd", "Female", new Date(), "6942424242");
		user2 = new User("user2_fname", "user2_lname", "email2@aueb.gr", "pass2w0rd", "Female", new Date(), "6942424242");

		/* Create ticket1*/
        hall = new Hall("hall1");
        sector = new Sector("sector1", 1.0);
        sector.addLine();
        sector.addSeat(1);
        hall.addSector(sector);

        play = new Play("play", "description");
        currentDate = new Date();
        show = new Show(currentDate, 50.0, play, hall);
        serial = new SerialNumberProviderStub();
        ticket1 = new Ticket(show, hall.getSectors().iterator().next().getSeats().get(0), serial);
		
		/* Create ticket2*/
        hall = new Hall("hall1");
        sector = new Sector("sector1", 1.0);
        sector.addLine();
        sector.addSeat(1);
        sector.addSeat(1);
        hall.addSector(sector);

        play = new Play("play", "description");
        currentDate = new Date();
        show = new Show(currentDate, 50.0, play, hall);
        serial = new SerialNumberProviderStub();
        ticket2 = new Ticket(show, hall.getSectors().iterator().next().getSeats().get(1), serial);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getPurchaseDate() throws Exception {
    	Assert.assertEquals(testDate, purchase.getPurchaseDate());
    }
    
    @Test
    public void setPurchaseDate() throws Exception {
    	Date newTestDate = new Date();
    	purchase.setPurchaseDate(newTestDate);
    	Assert.assertEquals(newTestDate, purchase.getPurchaseDate());
    }

    @Test
    public void getWayOfPurchase() throws Exception {
    	Assert.assertEquals("tameio", purchase.getWayOfPurchase());
    }
    
    @Test
    public void setWayOfPurchase() throws Exception {
    	String newWayOfPurchase = new String("internet");
    	purchase.setWayOfPurchase(newWayOfPurchase);
    	Assert.assertEquals(newWayOfPurchase, purchase.getWayOfPurchase());
    }

    @Test
    public void getQuantity() throws Exception {
    	Assert.assertEquals(Integer.valueOf(3), purchase.getQuantity());
    }
    
    @Test
    public void setQuantity() throws Exception {
    	purchase.setQuantity(1);
    	Assert.assertEquals(Integer.valueOf(1), purchase.getQuantity());
    }

    @Test
    public void getTotalAmount() throws Exception {
    	Assert.assertEquals(Double.valueOf(45.50), purchase.getTotalAmount());
    }
    
    @Test
    public void setTotalAmount() throws Exception {
    	Double newPrice = new Double(30);
    	purchase.setTotalAmount(newPrice);
    	Assert.assertEquals(newPrice, purchase.getTotalAmount());
    }

    @Test
    public void isEqual() throws Exception {
    	purchase.setUser(user1);
		Purchase newPurchase = new Purchase(testDate, "tameio", 3, 45.50);
		newPurchase.setUser(user1);
		Assert.assertTrue(newPurchase.equals(purchase));
    }

    @Test
    public void userNotEqual() throws Exception {
    	purchase.setUser(user1);
		Purchase newPurchase = new Purchase(testDate, "tameio", 3, 45.50);
		newPurchase.setUser(user2);
		Assert.assertFalse(newPurchase.equals(purchase));
    }
    
    @Test
    public void wayOfPurchaseNotEqual() throws Exception {
    	purchase.setUser(user1);
		Purchase newPurchase = new Purchase(testDate, "internet", 3, 45.50);
		newPurchase.setUser(user1);
		Assert.assertFalse(newPurchase.equals(purchase));
    }

    @Test
    public void getUser() throws Exception {
		purchase.setUser(user1);
		Assert.assertEquals(user1, purchase.getUser());
    }

    @Test
    public void getOneTicket() throws Exception {
    	purchase.setTicket(ticket1);
    	Set<Ticket> tickets = new HashSet<Ticket>();
    	tickets.add(ticket1);
    	Assert.assertEquals(tickets, purchase.getTickets());
    }
    
    @Test
    public void getManyTickets() throws Exception {
    	purchase.setTicket(ticket1);
    	purchase.setTicket(ticket2);
    	Set<Ticket> tickets = new HashSet<Ticket>();
    	tickets.add(ticket1);
    	tickets.add(ticket2);
    	Assert.assertEquals(tickets, purchase.getTickets());
    }

}