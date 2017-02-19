package gr.aueb.mscis.theater.model;

import gr.aueb.mscis.theater.service.SerialNumberProvider;
import gr.aueb.mscis.theater.service.SerialNumberProviderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Myron on 19/2/2017.
 */
public class TicketTest {

    Hall hall;
    Play play;
    Show show;
    Date date;
    Ticket ticket;
    SerialNumberProvider serial;

    @Before
    public void setUp() throws Exception {
        hall = new Hall("test hall");
        Sector sector = new Sector("test sector",1.0);
        sector.addLine();
        sector.addSeat(1);
        sector.addSeat(1);
        sector.addLine();
        sector.addSeat(2);
        hall.addSector(sector);

        play = new Play("test play", "description");

        Calendar c = Calendar.getInstance();
        Date currentDate = new Date();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 8);
        date = c.getTime();
        show = new Show(date,50.0,play,hall);

        serial = new SerialNumberProviderStub();

        ticket = new Ticket(show,hall.getSectors().iterator().next().getSeats().get(0),serial);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getSerial() throws Exception {
        assertEquals("111",ticket.getSerial());
    }

    @Test
    public void setSerial() throws Exception {
        ticket.setSerial(serial);
        assertEquals("111",ticket.getSerial());

    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(50.0,ticket.getPrice(),1e-15);
    }

    @Test
    public void setPrice() throws Exception {
        ticket.setPrice(30.0);
        assertEquals(30.0,ticket.getPrice(),1e-15);
    }

    @Test
    public void isMoneyReturn() throws Exception {
        assertFalse(ticket.isMoneyReturn());
    }

    @Test
    public void setMoneyReturn() throws Exception {
        ticket.setMoneyReturn(true);
        assertTrue(ticket.isMoneyReturn());
    }

    @Test
    public void isActive() throws Exception {
        assertTrue(ticket.isActive());
    }

    @Test
    public void setActive() throws Exception {
        ticket.setActive(false);
        assertFalse(ticket.isActive());
    }

    @Test
    public void getShow() throws Exception {
        assertTrue(show.equals(ticket.getShow()));
    }

    @Test
    public void getSeat() throws Exception {
        assertTrue(ticket.getSeat().equals(hall.getSectors().iterator().next().getSeats().get(0)));
    }

    @Test
    public void setSeat() throws Exception {
        hall.getSectors().iterator().next().getSeats().get(1).addTicket(ticket);
        assertTrue(ticket.getSeat().equals(hall.getSectors().iterator().next().getSeats().get(1)));
    }

    @Test
    public void equals() throws Exception {
        Ticket ticket2 = new Ticket(show,hall.getSectors().iterator().next().getSeats().get(0),serial);
        assertTrue(ticket2.equals(ticket));
        hall.getSectors().iterator().next().getSeats().get(1).addTicket(ticket2);
        assertFalse(ticket2.equals(ticket));
    }

}