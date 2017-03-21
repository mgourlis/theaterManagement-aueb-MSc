package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.PlayService;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Myron on 9/2/2017.
 */

@Path("/play")
public class PlayResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlayInfo> getPlays() {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);
        List<Play> plays = playService.findAllPlays();

        List<PlayInfo> playInfoes = PlayInfo.wrap(plays,true);

        em.close();

        return playInfoes;

    }

    @GET
    @Path("{playId:[0-9]*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public PlayInfo getPlayDetails(@PathParam("playId") int playId) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);
        Play play = playService.findPlayById(playId);

        PlayInfo playInfo = PlayInfo.wrap(play,true);

        em.close();

        return playInfo;

    }



}
