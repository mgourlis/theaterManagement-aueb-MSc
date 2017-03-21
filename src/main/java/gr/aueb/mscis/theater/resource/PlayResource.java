package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.*;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * Created by Myron on 9/2/2017.
 */

@Path("play")
public class PlayResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
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

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createPlay(PlayInfo playInfo){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);

        Play play = playInfo.getPlay(em);

        //Validation for playinfo

        play = playService.save(play);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI playUri = ub.path("play"+"/"+Integer.toString(play.getId())).build();

        em.close();

        return Response.created(playUri).build();
    }

    @POST
    @Path("createshowprogram")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createShowProgram(ProgramInfo program){

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        ShowService showService = new ShowService(flashserv);
        HallService hallService = new HallService(flashserv);
        PlayService playService = new PlayService(flashserv);

        //Validate program data

        Play play = playService.findPlayById(program.getPlayId());
        Hall hall = hallService.findHallById(program.getHallid());

        if(play == null || hall == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Show> shows = showService.createProgram(play,hall,program.getStartdate(),program.getEnddate(),program.getPrice());

        if(shows == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(shows.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

    @PUT
    @Path("{playId:[0-9]*}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updatePlay(PlayInfo playInfo){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);

        //Validations - Todo other validtions
        if(playService.findPlayById(playInfo.getId())== null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Play play = playInfo.getPlay(em);

        play = playService.save(play);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI playUri = ub.path("play"+"/"+Integer.toString(play.getId())).build();

        em.close();

        return Response.ok(playUri).build();
    }

    @DELETE
    @Path("{playId:[0-9]*}")
    public Response deletePlay(@PathParam("playId") int playId){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        PlayService playService = new PlayService(flashserv);

        Play playdb = playService.findPlayById(playId);

        if(playdb == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        playService.delete(playId);

        em.close();

        return Response.ok().build();
    }

}
