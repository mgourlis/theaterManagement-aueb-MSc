package gr.aueb.mscis.theater.resource;

import java.util.List;
import java.net.URI;

import javax.persistence.EntityManager;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.PlayService;
import gr.aueb.mscis.theater.service.ShowService;

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
}