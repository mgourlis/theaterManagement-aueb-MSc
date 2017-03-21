package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.HallService;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/hall")
public class HallResource {

    @Context
    UriInfo uriInfo;
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<HallInfo> getallHalls() {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        HallService hallService = new HallService(flashserv);
        List<Hall> halls = hallService.findAllHalls();
        
        List<HallInfo> hallInfo = HallInfo.wrap(halls,false);

        em.close();

        return hallInfo;
    }

    @GET
    @Path("{hallId:[0-9]*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public HallInfo getHall(@PathParam("hallId") int hallId) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        HallService hallService = new HallService(flashserv);
        Hall hall = hallService.findHallById(hallId);

        HallInfo hallInfo = null;

        if(hall != null){
            hallInfo = new HallInfo(hall,true);
        }

        em.close();

        return hallInfo;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createHall(HallInfo h){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        HallService hallService = new HallService(flashserv);

        Hall hall = h.getHall(em);

        hall = hallService.save(hall);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI hallUri = ub.path("hall"+"/"+Integer.toString(hall.getId())).build();

        em.close();

        return Response.created(hallUri).build();
    }

    @PUT
    @Path("{hallId:[0-9]*}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateHall(HallInfo h){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        HallService hallService = new HallService(flashserv);

        Hall dbhall = hallService.findHallById(h.getId());

        //Validation - Todo other validations for Seat, Sector and Hall
        if(dbhall == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Hall hall = h.getHall(em);

        if(dbhall.getId() != hall.getId()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        hallService.save(hall);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI hallUri = ub.path("hall"+"/"+Integer.toString(hall.getId())).build();

        em.close();

        return Response.ok(hallUri).build();
    }

    @DELETE
    @Path("{hallId:[0-9]*}")
    public Response deleteHall(@PathParam("hallId") int hallId){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        HallService hallService = new HallService(flashserv);

        Hall dbhall = hallService.findHallById(hallId);

        if(dbhall == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        hallService.delete(hallId);

        em.close();

        return Response.ok().build();
    }
}