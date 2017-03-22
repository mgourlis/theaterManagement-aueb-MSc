package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Agent;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.AgentService;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


public class AgentResourceTest extends TheaterResourceTest{

    public AgentResourceTest() {
        super();
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(AgentResource.class, DebugExceptionMapper.class);
    }

    @Test
    public void testGetAgentWithId() {

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);
        List<Agent> agents;
        agents = agentService.findAllAgents();
        Assert.assertEquals(2,agents.size());
        AgentInfo agentInfo = target("agent/"+agents.get(0).getId()).request().get(AgentInfo.class);
        Assert.assertEquals(agents.get(0).getLastName(), agentInfo.getLastName());

    }

    @Test
    public void testGetAllAgents() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);
        List<Agent> agents;
        agents = agentService.findAllAgents();

        List<AgentInfo> agentInfos = target("agent").request().get(new GenericType<List<AgentInfo>>() {
        });

        Assert.assertEquals(agents.size(),agentInfos.size());
    }

    @Test
    public void testCreateAgent() {
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);
        List<Agent> agents;
        agents = agentService.findAllAgents();

        Agent cloneagent = agents.get(0);

        AgentInfo postreq = new AgentInfo(cloneagent);

        postreq.setLastName("Created from post request");
        postreq.setId(null);

        Response resp = target("agent").request().post(Entity.entity(postreq, MediaType.APPLICATION_JSON));

        List<Agent> newagents = agentService.findAllAgents();

        Assert.assertEquals(agents.size()+1,newagents.size());

        Agent newagent = agentService.searchAgentsNameData("Created from post request").get(0);

        Assert.assertEquals("Created from post request",newagent.getLastName());
    }

    @Test
    public void testUpdateAgent(){
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);
        List<Agent> agents;
        agents = agentService.findAllAgents();

        Agent uptagent = agentService.findAllAgents().get(0);

        AgentInfo postreq = new AgentInfo(uptagent);

        postreq.setLastName("Updated from put request");

        Response resp = target("agent/"+Integer.toString(postreq.getId())).request().put(Entity.entity(postreq, MediaType.APPLICATION_JSON));

        Assert.assertEquals(200,resp.getStatus());

        List<Agent> uptagents = agentService.findAllAgents();

        uptagent = agentService.searchAgentsNameData("Updated from put request").get(0);

        EntityManager em = JPAUtil.getCurrentEntityManager();
        em.refresh(uptagent);
        em.close();

        Assert.assertEquals(agents.size(),uptagents.size());

        Assert.assertEquals("Updated from put request",uptagent.getLastName());
    }

    @Test
    public void testDeleteAgent(){
        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);
        List<Agent> agents = agentService.findAllAgents();

        Agent delagent = agents.get(0);

        Response resp = target("agent/"+Integer.toString(delagent.getId())).request().delete();

        Assert.assertEquals(200,resp.getStatus());

        List<Agent> delagents = agentService.findAllAgents();

        Assert.assertEquals(delagents.size(),agents.size()-1);

    }

}
