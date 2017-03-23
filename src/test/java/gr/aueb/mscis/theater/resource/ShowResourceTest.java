package gr.aueb.mscis.theater.resource;


import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Seat;
import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.List;

public class ShowResourceTest extends TheaterResourceTest {

    public ShowResourceTest() {
        super();
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(ShowResource.class, DebugExceptionMapper.class);
    }

    @Test
    public void testSearchShowByName() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);
        List<Play> plays;
        plays = playService.findAllPlays();

        Play play = plays.get(0);

        List<ShowInfo> showInfos = target("show/search").queryParam("name",play.getTitle()).request().get(new GenericType<List<ShowInfo>>() {
        });

        ShowService showService = new ShowService(flashserv);

        List<Show> shows = showService.findAllShowsByPlay(play.getId());

        Assert.assertEquals(shows.size(),showInfos.size());
    }

    @Test
    public void testGetFreeSeats() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        HallService hallService = new HallService(flashserv);
        ShowService showService = new ShowService(flashserv);
        List<Show> shows;
        shows = showService.findAllShows();

        Show show = shows.get(0);

        Iterator<Sector> it = show.getHall().getSectors().iterator();
        it.next();
        it.next();
        Sector sector = it.next();

        List<SeatInfo> seatInfos = target("show/seats/"+show.getId())
                .queryParam("sectorid",sector.getId())
                .queryParam("seatnum", 3)
                .request().get(new GenericType<List<SeatInfo>>() {
        });



       List<Seat> seats = sector.getFreeSeats(3,show.getDate());


       Assert.assertEquals(seats.size(),seatInfos.size());
    }

    @Test
    public void testUpdateShow() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        ShowService showService = new ShowService(flashserv);
        List<Show> shows;
        shows = showService.findAllShows();

        Show show = shows.get(0);

        Double oldprice = show.getPrice();

        ShowInfo showInfo = new ShowInfo(show);

        showInfo.setPrice(20.0);

        Response resp = target("show/"+Integer.toString(showInfo.getId())).request().put(Entity.entity(showInfo, MediaType.APPLICATION_JSON));

        Assert.assertEquals(200,resp.getStatus());

        Show uptshow = showService.findShowById(show.getId());
        EntityManager em = JPAUtil.getCurrentEntityManager();
        em.refresh(uptshow);


        //Oldprice 50.0, newprice 20.0  (assert 50.0-30.0, 20.0)
        Assert.assertEquals(oldprice-30.0,uptshow.getPrice(),0.00001);
    }

}