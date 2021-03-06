package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Seat;
import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.HallService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HallManagementTest {

    Initializer init = new Initializer();
    HallService hserv;
    Hall hall;
    FlashMessageService flashserv;

    @Before
    public void setUp() throws Exception {
        init.prepareData();
        flashserv = new FlashMessageServiceImpl();
        hserv = new HallService(flashserv);

        hall = new Hall("newHall");
        hall.addSector(new Sector("plateia",1.0));
        hall.addSector(new Sector("theorioA",1.5));
        hall.addSector(new Sector("theorioB",2.0));
        for (Sector sector : hall.getSectors()) {
            if(sector.getName().equals("plateia")) {
                for (int i = 0; i < 30; i++) {
                    sector.addLine();
                    for (int j = 0; j < 19; j++) {
                        sector.addSeat(i+1);
                    }
                }
            } else if(sector.getName().equals("theorioA")){
                for (int i = 0; i < 10; i++) {
                    sector.addLine();
                    for (int j = 0; j < 9; j++) {
                        sector.addSeat(i+1);
                    }
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    sector.addLine();
                    for (int j = 0; j < 9; j++) {
                        sector.addSeat(i+1);
                    }
                }
            }
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void CreateHall() throws Exception {
        hserv.save(hall);
        Hall created = hserv.findHallByName("newHall");

        assertEquals("newHall",created.getName());
        assertEquals(3,hall.getSectors().size());
        for (Sector sector : hall.getSectors()) {
            if(sector.getName().equals("plateia")) {
                assertEquals(600,sector.getSeats().size());
                assertEquals(30,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());
            } else if(sector.getName().equals("theorioA")){
                assertEquals(100,sector.getSeats().size());
                assertEquals(10,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());
            } else {
                assertEquals(50,sector.getSeats().size());
                assertEquals(5,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());
            }
        }

    }

    @Test
    public void CreateSameNameHall() throws Exception {
        hserv.save(hall);
        Hall created = hserv.findHallByName("newHall");

        assertEquals("newHall",created.getName());
        assertEquals(3,hall.getSectors().size());
        Hall sameNameHall = new Hall("newHall");
        assertNull(hserv.save(sameNameHall));

    }

    @Test
    public void AlterHallName() throws Exception {
        hserv.save(hall);
        Hall created = hserv.findHallByName("newHall");
        created.setName("alterName");
        hserv.save(created);
        assertEquals(0,hserv.findHallsByName("newHall").size());
        Hall altered = hserv.findHallById(created.getId());
        assertEquals("alterName",altered.getName());
    }

    @Test
    public void DeleteHall() throws Exception {
        hserv.save(hall);
        String halllName = "newHall";

        Hall created = hserv.findHallByName("newHall");

        hserv.delete(created.getId());

        assertEquals(0,hserv.findHallsByName("newHall").size());
    }

    @Test
    public void CreateSectorInHall() throws Exception {
        hserv.save(hall);
        Hall created = hserv.findHallsByName("newHall").get(0);
        created.addSector(new Sector("new Sector",1.0));
        hserv.save(created);

        Hall createdWithNewSector = hserv.findHallsByName("newHall").get(0);
        assertEquals(4,createdWithNewSector.getSectors().size());


    }

    @Test
    public void AlterSectorData() throws Exception {
        String sectorName = "plateia2";
        double plateiaPricaFactor = 2.0;

        hserv.save(hall);

        Hall created = hserv.findHallByName("newHall");

        for (Sector sector : created.getSectors()) {
            if (sector.getName().equals("plateia")) {
                sector.setName(sectorName);
                sector.setPriceFactor(plateiaPricaFactor);
                break;
            }
        }

        hserv.save(created);
        Hall hallAlteredSector= hserv.findHallByName("newHall");
        for (Sector sector : hallAlteredSector.getSectors()) {
            if (sector.getName().equals("plateia2")) {
                assertEquals(2.0,sector.getPriceFactor(),1e-15);
            }
            if(sector.getName().equals("plateia")){
                Assert.fail();
            }
        }

    }

    @Test
    public void DeleteSector() throws Exception {
        String sectorName = "plateia";
        hserv.save(hall);

        Hall created = hserv.findHallByName("newHall");

        for (Sector sector : created.getSectors()) {
            if (sector.getName().equals(sectorName)) {
                hall.removeSector(sector);
                break;
            }
        }

        hserv.save(created);

        Hall deletedSectorHall = hserv.findHallByName ("newHall");

        assertEquals(2, deletedSectorHall.getSectors().size());
        for (Sector sector : deletedSectorHall.getSectors()) {
            if (sector.getName().equals("plateia")) {
                Assert.fail();
            }
        }

    }

    @Test
    public void CreateLineInSector() throws Exception {
        String sectorName = "plateia";
        hserv.save(hall);

        Hall created = hserv.findHallByName("newHall");

        try {
            Sector sector = created.getSectorByName(sectorName);

            assertEquals(30,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());

            sector.addLine();
            hserv.save(created);

            Hall addLineHall = hserv.findHallByName("newHall");

            sector = addLineHall.getSectorByName(sectorName);

            assertEquals(31,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());

        }catch (IllegalArgumentException e){
            Assert.fail();
        }

    }

    @Test
    public void addSeatToLine() throws Exception {
        String sectorName = "plateia";
        int line = 1;
        hserv.save(hall);

        Hall created = hserv.findHallByName("newHall");

        try {
            Sector sector = created.getSectorByName(sectorName);

            assertEquals(20,sector.lineLength(line));

            sector.addSeat(line);
            hserv.save(created);

            Hall addLineHall = hserv.findHallByName("newHall");

            sector = addLineHall.getSectorByName(sectorName);

            assertEquals(21,sector.lineLength(line));

        }catch (IllegalArgumentException e){
            Assert.fail();
        }
    }

    @Test
    public void removeSeatFromLine() throws Exception {
        String sectorName = "plateia";
        int line = 1;
        hserv.save(hall);

        Hall created = hserv.findHallByName("newHall");

        try {
            Sector sector = created.getSectorByName(sectorName);

            assertEquals(20,sector.lineLength(line));

            sector.removeSeat(line);
            hserv.save(created);

            Hall addLineHall = hserv.findHallByName("newHall");

            sector = addLineHall.getSectorByName(sectorName);

            assertEquals(19,sector.lineLength(line));

        }catch (IllegalArgumentException e){
            Assert.fail();
        }
    }

    @Test
    public void DeleteLine() throws Exception {
        String sectorName = "plateia";
        int line = 5;
        hserv.save(hall);

        Hall created = hserv.findHallByName("newHall");

        try {
            Sector sector = created.getSectorByName(sectorName);

            assertEquals(30,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());

            sector.removeLine(line);
            hserv.save(created);

            Hall addLineHall = hserv.findHallByName("newHall");

            sector = addLineHall.getSectorByName(sectorName);

            assertEquals(29,sector.getSeats().get(sector.getSeats().size()-1).getLineNumber());

        }catch (IllegalArgumentException e){
            Assert.fail();
        }
    }

    @Test
    public void AlterHallAvailability() throws Exception {
        hall = hserv.save(hall);
        assertTrue(hall.isAvailable());

        hall.setAvailability(false);

        hserv.save(hall);

        Hall unavailableHall = hserv.findHallByName("newHall");

        assertFalse(unavailableHall.isAvailable());

    }

    @Test
    public void AlterHallAvailabilityWIthTickets() throws Exception {
        //phase 3
    }

    @Test
    public void AlterSectorAvailability() throws Exception {
        String sectorName = "plateia";
        hall = hserv.save(hall);
        try {
            Sector sector = hall.getSectorByName(sectorName);

            assertTrue(sector.isAvailable());

            sector.setAvailability(false);
            hserv.save(hall);

            Hall unavailableSectorHall = hserv.findHallByName("newHall");

            assertFalse(unavailableSectorHall.getSectorByName(sectorName).isAvailable());

        }catch (IllegalArgumentException e){
            Assert.fail();
        }
    }

    @Test
    public void AlterSectorAvailabilityWIthTickets() throws Exception {
        //phase 3
    }

    @Test
    public void AlterSeatAvailability() throws Exception {
        String sectorName = "plateia";
        int lineNumber = 3;
        int seatNmber = 5;
        hall = hserv.save(hall);
        try {
            Seat seat = hall.getSectorByName(sectorName).getSeat(lineNumber,seatNmber);



            assertTrue(seat.isAvailable());

            seat.setAvailability(false);
            hserv.save(hall);

            Hall unavailableSeatHall = hserv.findHallByName("newHall");

            assertFalse(unavailableSeatHall.getSectorByName(sectorName).getSeat(lineNumber,seatNmber).isAvailable());

        }catch (IllegalArgumentException e){
            Assert.fail();
        }
    }

    @Test
    public void AlterSeatAvailabilityWIthTickets() throws Exception {
        //phase 3
    }


}
