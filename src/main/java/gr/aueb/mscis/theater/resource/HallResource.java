package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.HallService;
import gr.aueb.mscis.theater.service.PlayService;
import gr.aueb.mscis.theater.model.Seat;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Path("hall")
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
        
        List<HallInfo> hallInfo = HallInfo.wrap(halls);

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
        
        HallInfo hallInfo = new HallInfo(hall);
        
        em.close();

        return hallInfo;
    }
    
}