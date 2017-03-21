package gr.aueb.mscis.theater.Resource;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.resource.DebugExceptionMapper;
import gr.aueb.mscis.theater.resource.StatisticsInfo;
import gr.aueb.mscis.theater.resource.StatisticsResource;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.PlayService;

public class StatisticsResourceTest extends TheaterResourceTest {

    public StatisticsResourceTest() {
        super();
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(StatisticsResource.class, DebugExceptionMapper.class);
    }

    @Test
    public void testGetStatistics() {
		
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        List <Play> play;
        PlayService playService = new PlayService(flashserv);
        play = playService.findPlaysByTitle("Amlet");
        Assert.assertEquals(1, play.size());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 2);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);

//        StatisticsInfo stats = target("statistics/"+play.get(0).getId())
//                               .queryParam("start", cal.getTime())
//                               .queryParam("end",   cal.getTime())
//                               .request().get(StatisticsInfo.class);
//

        
////		Assert.assertEquals();

	}
}