package gr.aueb.mscis.theater.model;

import gr.aueb.mscis.theater.service.SerialNumberProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class ShowTest {
    Play play;
    Show show;
    Hall hall;
    Date date;
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
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getDate() throws Exception {
        assertTrue(date.equals(show.getDate()));
    }

    @Test
    public void setDate() throws Exception {
        Date date1 = new Date();
        show.setDate(date1);
        assertTrue(date1.equals(show.getDate()));
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(50.0, show.getPrice(),1e-15);
    }

    @Test
    public void setPrice() throws Exception {
        show.setPrice(70.0);
        assertEquals(70.0, show.getPrice(),1e-15);
    }

    @Test
    public void getPlay() throws Exception {
        assertTrue(play.equals(show.getPlay()));
    }

    @Test
    public void setPlay() throws Exception {
        Play play2 = new Play("play2", "");
        show.setPlay(play2);
        assertEquals(0,play.getShows().size());
        assertEquals(1, play2.getShows().size());
    }

    @Test
    public void getHall() throws Exception {
        assertTrue(hall.equals(show.getHall()));
    }

    @Test
    public void setHall() throws Exception {
        Hall hall2 = new Hall("test hall2");
        show.setHall(hall2);
        assertEquals(0,hall.getShows().size());
        assertEquals(1,hall2.getShows().size());
    }

    @Test
    public void isCanceled() throws Exception {
        assertFalse(show.isCanceled());
    }

    @Test
    public void setCanceled() throws Exception {
        show.setCanceled();
        assertTrue(show.isCanceled());
    }

    @Test
    public void getTickets() throws Exception {
        assertEquals(0, show.getTickets().size());
    }

    @Test
    public void addTicket() throws Exception {
        Ticket t = new Ticket(show,hall.getSectors().iterator().next().getSeats().get(0),serial);
        show.addTicket(t);
        assertEquals(1,show.getTickets().size());
    }

    @Test
    public void removeTicket() throws Exception {
        Ticket t = new Ticket(show,hall.getSectors().iterator().next().getSeats().get(0),serial);
        show.removeTicket(t);
        assertEquals(0, show.getTickets().size());
    }

    @Test
    public void equals() throws Exception {
        Hall hall2 = new Hall("test hall2");
        Play play2 = new Play("play2", "");
        Show show2 = new Show(date,50.0,play2,hall2);
        show2.setHall(hall);
        show2.setPlay(play);
        assertTrue(show2.equals(show));
        assertEquals(1,play.getShows().size());
    }

}