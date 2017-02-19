package gr.aueb.mscis.theater.model;

import gr.aueb.mscis.theater.service.SerialNumberProvider;
import gr.aueb.mscis.theater.service.SerialNumberProviderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Myron on 19/2/2017.
 */
public class SeatTest {

    Hall hall;
    Sector sector;
    Seat seat;
    SerialNumberProvider serial;

    @Before
    public void setUp() throws Exception {
        hall = new Hall("test hall");
        sector = new Sector("test sector", 1.0);
        hall.addSector(sector);
        sector.addLine();
        seat = sector.getSeats().get(0);

        serial = new SerialNumberProviderImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isBooked() throws Exception {
        Play play = new Play("amlet","description");
        Calendar c = Calendar.getInstance();
        Date currentDate = new Date();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 8);
        Date dummyFutureDate = c.getTime();
        c.add(Calendar.DATE, -16);
        Date dummyPastDate = c.getTime();
        Show futureShow = new Show(dummyFutureDate,50.0,play,hall);
        Show pastShow = new Show(dummyPastDate,50.0,play,hall);
        Ticket futureTicket = new Ticket(futureShow,seat,serial);
        Ticket pastTicket = new Ticket(pastShow,seat,serial);

        assertFalse(seat.isBooked());

        seat.getTickets().add(pastTicket);

        assertFalse(seat.isBooked());

        seat.getTickets().add(futureTicket);

        assertTrue(seat.isBooked());
    }

    @Test
    public void isBookedOnDate() throws Exception {
        Play play = new Play("amlet","description");
        Calendar c = Calendar.getInstance();
        Date currentDate = new Date();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 8);
        Date dummyFutureDate = c.getTime();
        c.add(Calendar.DATE, -16);
        Date dummyPastDate = c.getTime();
        Show futureShow = new Show(dummyFutureDate,50.0,play,hall);
        Show pastShow = new Show(dummyPastDate,50.0,play,hall);
        Ticket futureTicket = new Ticket(futureShow,seat,serial);
        Ticket pastTicket = new Ticket(pastShow,seat,serial);

        assertFalse(seat.isBooked(dummyFutureDate));

        seat.getTickets().add(pastTicket);

        assertTrue(seat.isBooked(dummyPastDate));

        seat.getTickets().add(futureTicket);

        assertTrue(seat.isBooked(dummyFutureDate));
    }

    @Test
    public void getTickets() throws Exception {
        Play play = new Play("amlet","description");
        Calendar c = Calendar.getInstance();
        Date currentdate = new Date();
        c.setTime(currentdate);
        c.add(Calendar.DATE, 8);
        Date dummydate = c.getTime();
        Show show = new Show(dummydate,50.0,play,hall);
        Ticket ticket = new Ticket(show,seat,serial);

        assertEquals(0, seat.getTickets().size());

        seat.getTickets().add(ticket);

        assertEquals(1, seat.getTickets().size());

    }

    @Test
    public void setTickets() throws Exception {
        Play play = new Play("amlet","description");
        Calendar c = Calendar.getInstance();
        Date currentdate = new Date();
        c.setTime(currentdate);
        c.add(Calendar.DATE, 8);
        Date dummydate = c.getTime();
        Show show = new Show(dummydate,50.0,play,hall);
        Ticket ticket = new Ticket(show,seat,serial);
        Ticket ticket2 = new Ticket(show,seat,serial);
        Ticket ticket3 = new Ticket(show,seat,serial);


        assertEquals(0, seat.getTickets().size());

        seat.addTicket(ticket);
        seat.addTicket(ticket2);
        seat.addTicket(ticket3);

        assertEquals(3, seat.getTickets().size());

        seat.removeTicket(ticket);
        seat.removeTicket(ticket2);
        seat.removeTicket(ticket3);

        assertEquals(0, seat.getTickets().size());

    }

    @Test
    public void equals() throws Exception {
        Seat s2 = new Seat(1,1);
        assertFalse(seat.equals(s2));
        s2.setSector(sector);
        assertTrue(seat.equals(s2));
    }

}