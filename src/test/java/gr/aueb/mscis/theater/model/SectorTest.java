package gr.aueb.mscis.theater.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SectorTest {

    Hall hall;
    Sector sector;

    @Before
    public void setUp() throws Exception {
        hall = new Hall("test hall");
        sector = new Sector("test sector",1.0);
        hall.addSector(sector);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getName() throws Exception {
        assertEquals("test sector", sector.getName());
    }

    @Test
    public void setName() throws Exception {
        sector.setName("test");
        assertEquals("test", sector.getName());
    }

    @Test
    public void getPriceFactor() throws Exception {
        assertEquals(1.0, sector.getPriceFactor(),1e-15);
    }

    @Test
    public void setPriceFactor() throws Exception {
        sector.setPriceFactor(2.0);
        assertEquals(2.0, sector.getPriceFactor(),1e-15);
    }

    @Test
    public void getHall() throws Exception {
        assertTrue(hall.equals(sector.getHall()));
    }

    @Test
    public void getSeats() throws Exception {
        assertEquals(0, sector.getSeats().size());
    }

    @Test
    public void addLine() throws Exception {
        sector.addLine();
        assertEquals(1, sector.getSeats().size());
        assertEquals(1, sector.getSeats().get(0).getLineNumber());
        sector.addLine();
        assertEquals(2, sector.getSeats().size());
        assertEquals(2, sector.getSeats().get(1).getLineNumber());
        sector.addLine();
        assertEquals(3, sector.getSeats().size());
        assertEquals(3, sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());
    }

    @Test
    public void removeLine() throws Exception {
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

        //teleutaia seira
        assertEquals(7,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());
        assertTrue(sector.removeLine(7));
        assertEquals(6,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());
        assertEquals(18, sector.getSeats().size());

        //proti seira
        assertTrue(sector.removeLine(1));
        assertEquals(1,sector.getSeats().get(0).getLineNumber());
        assertEquals(2,sector.getSeats().get(1).getLineNumber());
        assertEquals(13, sector.getSeats().size());

        //mesea seira me polles theseis
        assertTrue(sector.removeLine(3));
        assertEquals(7, sector.getSeats().size());
        assertEquals(4,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());

        //mesea seira me mia thesi
        assertTrue(sector.removeLine(3));
        assertEquals(6, sector.getSeats().size());
        assertEquals(3,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());
    }

    @Test
    public void removeNonExistenceLine() throws Exception {
        assertFalse(sector.removeLine(100));
    }

    @Test
    public void addSeat() throws Exception {
        sector.addLine();
        sector.addSeat(1);
        assertEquals(2, sector.getSeats().size());
        sector.addLine();
        sector.addSeat(2);
        sector.addSeat(2);
        assertEquals(5, sector.getSeats().size());
        assertEquals(2,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());
        sector.addLine();
        sector.addSeat(3);
        assertEquals(7, sector.getSeats().size());
        assertEquals(3,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());
    }

    @Test
    public void removeSeat() throws Exception {
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
        assertTrue(sector.removeSeat(1));
        assertEquals(19, sector.getSeats().size());
        assertEquals(4,sector.lineLength(1));
        assertTrue(sector.removeSeat(7));
        assertEquals(18, sector.getSeats().size());
        assertEquals(1,sector.lineLength(7));
        assertFalse(sector.removeSeat(7));
        assertEquals(18, sector.getSeats().size());
        assertEquals(1,sector.lineLength(7));
        assertFalse(sector.removeSeat(2));
        assertEquals(18, sector.getSeats().size());
        assertEquals(1,sector.lineLength(2));
        assertTrue(sector.removeSeat(4));
        assertEquals(17, sector.getSeats().size());
        assertEquals(5,sector.lineLength(4));
        assertEquals(6,sector.getSeats().get(15).getLineNumber());

    }

    @Test
    public void isAvailable() throws Exception {
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

        assertTrue(sector.isAvailable());
        sector.setAvailability(false);
        assertFalse(sector.isAvailable());
    }

    @Test
    public void equals() throws Exception {
        Sector s2 = new Sector("sector new 2",9.0);
        hall.addSector(s2);
        assertFalse(s2.equals(sector));
        Hall hall2 = new Hall("test hall 2");
        s2 = new Sector("test sector",1.0);
        hall2.addSector(s2);
        assertFalse(s2.equals(sector));
        hall.addSector(s2);
        assertTrue(s2.equals(sector));

    }

}