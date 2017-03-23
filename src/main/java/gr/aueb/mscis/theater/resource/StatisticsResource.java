package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.ShowService;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("statistics")
public class StatisticsResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{playid:[0-9]*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StatisticsInfo income(@PathParam("playid") int playId,
                                 @QueryParam("start") String startDate,
                                 @QueryParam("end")   String endDate) {
        
        EntityManager em = JPAUtil.getCurrentEntityManager();
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        ShowService showService = new ShowService(flashserv);
        List<Show> shows = showService.findAllShowsByPlay(playId);

        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date a = formatter.parse(startDate);
            Date b = formatter.parse(endDate);

            StatisticsInfo newStatisticsInfo = new StatisticsInfo(0.0, 0.0);
            newStatisticsInfo.getTheaterIncome(a, b, shows);
            newStatisticsInfo.getTheaterCompleteness(a, b, shows);

            return newStatisticsInfo;

        } catch (ParseException e) {
            return null;
        }
    }
}