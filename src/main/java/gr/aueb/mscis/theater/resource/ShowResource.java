package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.ShowService;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

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
//    @GET
//    @Path("{showid:[0-9]*}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<SeatInfo> getFreeSeats(@PathParam("showid")  int showId
//                                       @QueryParam("sectorid" int sectorId,
//                                       @QueryParam("seatnum"  int numberOfSeats)) {
//
//        EntityManager em = JPAUtil.getCurrentEntityManager();
//
//        FlashMessageService flashserv = new FlashMessageServiceImpl();
//
//        List<SeatInfo> seatInfoList = new ArrayList<SeatInfo>();
//        Show show;
//        ShowService showService = new ShowService(flashserv);
//        Show show = showService.findShowById(showId);
//        
//        if (show.getId != 0) {
//            Set<Sector> sectors = null;
//            List<Seat> seats = null;
//
//            sectors = show.getHall().getSectors();        
//            for (Sector sector : sectors) {
//                if (sector.getId() == sectorId) {
//                    seats = getFreeSeats(numberOfSeats, show.getDate());
//                    break;
//                }
//            }
//            
//            if (seats) {
//                for (Seate s : seats)
//                    seatInfoList.add(new SeatInfo(s));
//            }
//
//        }
//        
//        em.close();
//        return seatInfoList;
//    }

}