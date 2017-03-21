package gr.aueb.mscis.theater.resource;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.PlayService;
import gr.aueb.mscis.theater.service.ShowService;

@Path("statistics")
public class StatisticsResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{playid:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public StatisticsInfo income(@PathParam("playid") int playId,
                                 @QueryParam("start") Date startDate,
                                 @QueryParam("end")   Date endDate) {
        
        EntityManager em = JPAUtil.getCurrentEntityManager();
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        ShowService showService = new ShowService(flashserv);
        List<Show> shows = showService.findAllShowsByPlay(playId);
        
        StatisticsInfo newStatisticsInfo = new StatisticsInfo(0.0, 0.0);
        newStatisticsInfo.getTheaterIncome(startDate, endDate, shows);
        newStatisticsInfo.getTheaterCompleteness(startDate, endDate, shows);
        
        return newStatisticsInfo;
    }
}