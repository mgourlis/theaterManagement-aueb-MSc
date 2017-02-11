package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.service.PlayService;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Myron on 9/2/2017.
 */

@Path("/play")
public class PlayResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{playId:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public PlayInfo getBookDetails(@PathParam("playId") int playId) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        PlayService playService = new PlayService();
        Play play = playService.findPlayById(playId);

        PlayInfo playInfo = PlayInfo.wrap(play);
        em.close();

        return playInfo;

    }
}
