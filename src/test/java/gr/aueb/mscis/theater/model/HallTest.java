package gr.aueb.mscis.theater.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HallTest {

    Hall hall;

    @Before
    public void setUp() throws Exception {
        hall = new Hall("test hall");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getName() throws Exception {
        assertEquals("test hall", hall.getName());
    }

    @Test
    public void setName() throws Exception {
        hall.setName("test");
        assertEquals("test",hall.getName());
    }

    @Test
    public void getSectors() throws Exception {
        assertEquals(0,hall.getSectors().size());
    }

    @Test
    public void addSector() throws Exception {
        hall.addSector(new Sector("test sector",1.0));
        assertEquals(1, hall.getSectors().size());
        hall.addSector(new Sector("test sector2",1.0));
        assertEquals(2, hall.getSectors().size());
    }

    @Test
    public void removeSector() throws Exception {
        hall.addSector(new Sector("test sector",1.0));
        hall.addSector(new Sector("test sector2",1.0));
        Sector sector = new Sector("test sector2",1.0);
        sector.setHall(hall);
        assertTrue(hall.removeSector(sector));
        assertEquals(1, hall.getSectors().size());
    }

    @Test
    public void isAvailable() throws Exception {
        Sector sector = new Sector("test sector",1.0);
        hall.addSector(sector);

        sector.addLine();
        sector.addSeat(1);
        sector.addSeat(1);
        sector.addSeat(1);
        sector.addSeat(1);
        sector.addLine();
        sector.addLine();
        sector.addSeat(3);
        sector.addSeat(3);
        sector.addSeat(3);
        sector.addLine();
        sector.addSeat(4);
        sector.addSeat(4);
        sector.addSeat(4);
        sector.addSeat(4);
        sector.addSeat(4);
        sector.addLine();
        sector.addLine();
        sector.addLine();
        sector.addSeat(7);

        assertTrue(hall.isAvailable());
        hall.setAvailability(false);
        assertFalse(hall.isAvailable());
    }

    @Test
    public void equals() throws Exception {
        Hall hall2 = new Hall("test hall 2");
        assertFalse(hall.equals(hall2));
        hall2.setName("test hall");
        assertTrue(hall.equals(hall2));
    }

}