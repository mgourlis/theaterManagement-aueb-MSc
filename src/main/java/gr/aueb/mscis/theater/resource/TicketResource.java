package gr.aueb.mscis.theater.resource;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("tickets") //show
public class TicketResource {

    @Context
    UriInfo uriInfo;
    	
    }