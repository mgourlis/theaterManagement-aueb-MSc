package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HallServiceTest {
    Initializer init = new Initializer();
    HallService hserv;

    @Before
    public void setUp() throws Exception {
        init.prepareData();
        hserv = new HallService(JPAUtil.createEntityManager());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void findAllHalls() throws Exception {
        Assert.assertEquals(2,hserv.findAllHalls().size());
    }

    @Test
    public void findHallById() throws Exception {
       assertEquals("hall1", hserv.findHallById(hserv.findAllHalls().get(0).getId()).getName());
    }

    @Test
    public void findHallsByName() throws Exception {
        assertEquals(1, hserv.findHallsByName("hall1").size());
    }

    @Test
    public void findAvailableHalls() throws Exception {
        Hall hall = hserv.findHallById(hserv.findAllHalls().get(0).getId());
        hall.setAvailability(false);
        hserv.save(hall);
        assertEquals(1, hserv.findAvailableHalls().size());
    }

    @Test
    public void save() throws Exception {
        Hall hall = new Hall("hall3");
        hserv.save(hall);
        Assert.assertEquals(3,hserv.findAllHalls().size());
    }

    @Test
    public void addSectorToHall() throws Exception {
        List<Hall> halls = hserv.findAllHalls();
        Hall hall = halls.get(0);
        hall.addSector(new Sector("newly added Sector", 1.0));
        hserv.save(hall);
        halls = hserv.findAllHalls();
        hall = halls.get(0);
        Assert.assertEquals(4,hall.getSectors().size());
    }

    @Test
    public void delete() throws Exception {
        Hall hall = hserv.findHallById(hserv.findAllHalls().get(0).getId());
        hserv.delete(hall.getId());
        Assert.assertEquals(1,hserv.findAllHalls().size());
    }

}