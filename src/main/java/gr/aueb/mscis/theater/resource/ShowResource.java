package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Seat;
import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.*;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Set;

@Path("show")
public class ShowResource {

    @Context
    UriInfo uriInfo;

    //ΠΧ5. Αγορά Εισιτηρίων
    //Ο πελάτης αναζητα το πρόγραμμα της παράστασης
    //Το σύστημα εμφανίζει λίστα με τις ημερομηνίες
    @GET
	@Path("search")
    @Produces(MediaType.APPLICATION_JSON)
	public List<ShowInfo> searchShowByName(@QueryParam("name") String name) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        ShowService showService = new ShowService(flashserv);
        List<Show> shows = showService.findAllShowsByPlayName(name);

        List<ShowInfo> showInfo = ShowInfo.wrap(shows);

        em.close();

        return showInfo;
    }
    

    /*
     * Propose to the user free seats of the hall with id hallid
     * and sector with sectorid.
     * The number of seats is declared in numberOfSeats 
     */
    @GET
    @Path("seats/{showid:[0-9]*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SeatInfo> getFreeSeats(@PathParam("showid")    int showId,
                                       @QueryParam("sectorid") int sectorId,
                                       @QueryParam("seatnum")  int numberOfSeats) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

//        List<SeatInfo> seatInfoList = new ArrayList<SeatInfo>();
        List<SeatInfo> seatInfoList = null;
        ShowService showService = new ShowService(flashserv);
        Show show = showService.findShowById(showId);
        
        if (show.getId() != 0) {
            Set<Sector> sectors = null;
            List<Seat> seats = null;

            sectors = show.getHall().getSectors();        
            for (Sector sector : sectors) {
                if (sector.getId() == sectorId) {
                    seats = sector.getFreeSeats(numberOfSeats, show.getDate());
                    break;
                }
            }
            
            if (seats != null)
            	seatInfoList = SeatInfo.wrap(seats);
        }
        
        em.close();
        return seatInfoList;
    }

    @PUT
    @Path("{showId:[0-9]*}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateShow(ShowInfo showInfo){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        ShowService showService = new ShowService(flashserv);

        //Validations - Todo other validtions
        Show dbshow = showService.findShowById(showInfo.getId());
        if(dbshow == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(!showInfo.getDate().equals(dbshow.getDate())){
            EmailProvider email = new EmailProviderImpl();
            if(showService.alterShowDate(dbshow.getId(),showInfo.getDate(),showInfo.getMessage(), email) == null)
                return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else if(showInfo.isCanceled() && !dbshow.isCanceled()) {
            EmailProvider email = new EmailProviderImpl();
            if(showService.cancelShow(dbshow.getId(), showInfo.getMessage(), email) == null)
                return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else if(showInfo.getPrice() != dbshow.getPrice()){
            if(showService.alterShowPrice(dbshow.getId(),showInfo.getPrice()) == null)
                return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI showUri = ub.path("show"+"/"+Integer.toString(dbshow.getId())).build();

        em.close();

        return Response.ok(showUri).build();
    }

}