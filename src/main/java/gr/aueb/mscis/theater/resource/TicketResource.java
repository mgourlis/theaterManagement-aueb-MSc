package gr.aueb.mscis.theater.resource;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import gr.aueb.mscis.theater.model.Ticket;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.TicketService;

@Path("tickets")
public class TicketResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{serialno}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TicketInfo getHall(@PathParam("serialno") String serialNo) {

        EntityManager em = JPAUtil.getCurrentEntityManager();
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        TicketService ticketService = new TicketService(flashserv);
        Ticket ticket = ticketService.findTicketBySerialNo(serialNo);

        TicketInfo ticketInfo = null;

        if (ticket != null) {
            ticketInfo = new TicketInfo(ticket);
        }

        em.close();

        return ticketInfo;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTicket(TicketInfo ticketInfo) {

        FlashMessageService flashserv = new FlashMessageServiceImpl();
        EntityManager em = JPAUtil.getCurrentEntityManager();

        Ticket ticket = ticketInfo.getTicket(em);
        TicketService ticketService = new TicketService(flashserv);
        ticket = ticketService.save(ticket);

        em.close();

        return Response.ok().build();
    }
}