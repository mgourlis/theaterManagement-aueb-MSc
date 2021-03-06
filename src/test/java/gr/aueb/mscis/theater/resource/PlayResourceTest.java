package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Play;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlayResourceTest extends TheaterResourceTest{

    public PlayResourceTest() {
        super();
    }

    @Override
    protected Application configure() {
		/*
		 *
		 */
        return new ResourceConfig(PlayResource.class, DebugExceptionMapper.class);
    }

    @Test
    public void testGetPlayWithId() {

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);
        List<Play> plays;
        plays = playService.findAllPlays();
        Assert.assertEquals(2,plays.size());
        PlayInfo playInfo = target("play/"+plays.get(0).getId()).request().get(PlayInfo.class);
        Assert.assertEquals(plays.get(0).getTitle(), playInfo.getTitle());

    }

    @Test
    public void testGetAllPlays() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);
        List<Play> plays;
        plays = playService.findAllPlays();

        List<PlayInfo> playInfos = target("play").request().get(new GenericType<List<PlayInfo>>() {
        });

        Assert.assertEquals(plays.size(),playInfos.size());
    }

    @Test
    public void testCreatePlay() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);
        List<Play> plays = playService.findAllPlays();

        Play cloneplay = plays.get(0);

        PlayInfo postreq = new PlayInfo(cloneplay,true);

        postreq.setTitle("Created from post request");
        postreq.setId(null);
        List<RoleInfo> roleinfos = postreq.getRoles();
        for (RoleInfo rol : roleinfos){
            rol.setId(null);
        }

        Response resp = target("play").request().post(Entity.entity(postreq, MediaType.APPLICATION_JSON));

        List<Play> newplays = playService.findAllPlays();

        Assert.assertEquals(plays.size()+1,newplays.size());

        List<Play> newplay = playService.findPlaysByTitle("Created from post request");

        Assert.assertEquals("Created from post request",newplay.get(0).getTitle());
    }

    @Test
    public void testUpdatePlay(){
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);
        List<Play> plays = playService.findAllPlays();

        Play uptplay = playService.findPlaysByTitle("The Glass Menagerie").get(0);

        PlayInfo postreq = new PlayInfo(uptplay,true);

        postreq.setTitle("Updated from put request");

        Response resp = target("play/"+Integer.toString(postreq.getId())).request().put(Entity.entity(postreq, MediaType.APPLICATION_JSON));

        Assert.assertEquals(200,resp.getStatus());

        List<Play> uptplays = playService.findAllPlays();

        uptplay = playService.findPlaysByTitle("Updated from put request").get(0);

        EntityManager em = JPAUtil.getCurrentEntityManager();
        em.refresh(uptplay);
        em.close();

        Assert.assertEquals(plays.size(),uptplays.size());

        Assert.assertEquals("Updated from put request",uptplay.getTitle());
    }

    @Test
    public void testDeletePlay(){
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);
        List<Play> plays = playService.findAllPlays();

        Play delplay = playService.findPlaysByTitle("The Glass Menagerie").get(0);

        Response resp = target("play/"+Integer.toString(delplay.getId())).request().delete();

        Assert.assertEquals(200,resp.getStatus());

        List<Play> delplays = playService.findAllPlays();

        Assert.assertEquals(delplays.size(),plays.size()-1);

    }

    @Test
    public  void testCreateProgramFromPlay(){
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);
        HallService hallService = new HallService(flashserv);
        ShowService showService = new ShowService(flashserv);

        List<Show> shows = showService.findAllShows();

        Play play = playService.findAllPlays().get(1);
        Hall hall = hallService.findAllHalls().get(1);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,100);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);

        Date startdate = cal.getTime();
        cal.add(Calendar.DATE,9);
        Date enddate = cal.getTime();

        ProgramInfo programInfo = new ProgramInfo(startdate,enddate,play.getId(),hall.getId(),15.0);

        Response resp = target("play/createshowprogram").request().post(Entity.entity(programInfo, MediaType.APPLICATION_JSON));

        Assert.assertEquals(200,resp.getStatus());

        List<Show> newshows = showService.findAllShows();

        Assert.assertEquals(shows.size()+10,newshows.size());
    }
}
