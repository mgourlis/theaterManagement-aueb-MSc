package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.service.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShowManagementTest {

    Initializer init = new Initializer();
    ShowService shserv;
    PlayService pserv;
    HallService hserv;
    FlashMessageService flashserv = new FlashMessageServiceImpl();

    @Before
    public void setUp() throws Exception {
        init.prepareData();
        shserv = new ShowService(flashserv);
        pserv = new PlayService(flashserv);
        hserv = new HallService(flashserv);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void CreateShowProgram() throws Exception {
        Play play = pserv.findPlaysByTitle("Amlet").get(0);
        Hall hall = hserv.findHallByName("hall1");
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);
        cal.add(Calendar.DATE,7);
        Date startDate = cal.getTime();
        cal.add(Calendar.DATE,4);
        Date endDate = cal.getTime();
        double price= 50.0;

        Assert.assertEquals(3, pserv.findPlayById(play.getId()).getShows().size());

        List<Show> memoryShows = shserv.createProgram(play,hall,startDate,endDate,price);

        Assert.assertNotNull(memoryShows);

        Assert.assertEquals(8,pserv.findPlayById(play.getId()).getShows().size());



    }

    @Test
    public void CreateShowProgramAlreadyExists() throws Exception {
        Play play = pserv.findPlaysByTitle("Amlet").get(0);
        Hall hall = hserv.findHallByName("hall1");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND,0);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);
        cal.add(Calendar.DATE,1);
        Date startDate = cal.getTime();
        cal.add(Calendar.DATE,4);
        Date endDate = cal.getTime();
        double price= 50.0;

        Assert.assertEquals(3, pserv.findPlayById(play.getId()).getShows().size());

        List<Show> memoryShows = shserv.createProgram(play,hall,startDate,endDate,price);

        Assert.assertEquals(0,memoryShows.size());

        Assert.assertEquals(3,pserv.findPlayById(play.getId()).getShows().size());

    }

    @Test
    public void CreateShowProgramHallNotAvailable() throws Exception {
        Play play = pserv.findPlaysByTitle("Amlet").get(0);
        Hall hall = hserv.findHallByName("hall2");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND,0);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);
        cal.add(Calendar.DATE,2);
        Date startDate = cal.getTime();
        cal.add(Calendar.DATE,4);
        Date endDate = cal.getTime();
        double price= 50.0;

        Assert.assertEquals(3, pserv.findPlayById(play.getId()).getShows().size());

        List<Show> memoryShows = shserv.createProgram(play,hall,startDate,endDate,price);

        Assert.assertEquals(0,memoryShows.size());

        Assert.assertEquals(3,pserv.findPlayById(play.getId()).getShows().size());
    }

    @Test
    public void CancelShow() throws Exception {
        Play play = pserv.findPlaysByTitle("Amlet").get(0);
        Show show = shserv.findFutureShowsByPlay(play.getId()).get(0);

        Assert.assertFalse(show.isCanceled());

        EmailProvider emailprov = new EmailProviderStub();

        shserv.cancelShow(show.getId(),"", emailprov);

        Show show2 = shserv.findFutureShowsByPlay(play.getId()).get(0);

        Assert.assertTrue(show2.isCanceled());

        Assert.assertEquals(0,emailprov.getEmails().size());
    }

    @Test
    public void CancelShowWithTickets() throws Exception {
        //phase 3
    }

    @Test
    public void AlterShowDate() throws Exception {
        Play play = pserv.findPlaysByTitle("Amlet").get(0);
        Show show = shserv.findFutureShowsByPlay(play.getId()).get(0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND,0);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);

        EmailProvider emailprov = new EmailProviderStub();

        cal.add(Calendar.DATE,1);

        Assert.assertEquals(cal.getTime(),show.getDate());

        cal.add(Calendar.DATE,3);

        shserv.alterShowDate(show.getId(),cal.getTime(),"",emailprov);

        Show show2 = shserv.findFutureShowsByPlay(play.getId()).get(0);

        Assert.assertEquals(cal.getTime(),show.getDate());

        Assert.assertEquals(0,emailprov.getEmails().size());


    }

    @Test
    public void AlterShowDateAlreadyExists() throws Exception {
        Play play = pserv.findPlaysByTitle("Amlet").get(0);
        Hall hall = hserv.findHallByName("hall1");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND,0);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);
        cal.add(Calendar.DATE,7);
        Date startDate = cal.getTime();
        cal.add(Calendar.DATE,4);
        Date endDate = cal.getTime();
        double price= 50.0;

        Assert.assertEquals(5,shserv.createProgram(play,hall,startDate,endDate,price).size());

        Show show = shserv.findFutureShowsByPlay(play.getId()).get(0);

        EmailProvider emailprov = new EmailProviderStub();

        cal.add(Calendar.DATE,-1);

        Assert.assertNull(shserv.alterShowDate(show.getId(),cal.getTime(),"",emailprov));

        Show show2 = shserv.findFutureShowsByPlay(play.getId()).get(0);

        cal.add(Calendar.DATE,-9);

        Assert.assertEquals(cal.getTime(),show.getDate());

    }

    @Test
    public void AlterShowDateWithTickets() throws Exception {
        //phase 3
    }
}
