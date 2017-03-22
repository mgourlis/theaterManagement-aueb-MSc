package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Agent;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.AgentService;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("agent")
public class AgentResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<AgentInfo> getAgents() {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);
        List<Agent> agents = agentService.findAllAgents();

        List<AgentInfo> agentInfos = AgentInfo.wrap(agents);

        em.close();

        return agentInfos;

    }

    @GET
    @Path("{agentId:[0-9]*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public AgentInfo getAgentDetails(@PathParam("agentId") int agentId) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);
        Agent agent = agentService.findAgentById(agentId);

        AgentInfo agentInfo = AgentInfo.wrap(agent);

        em.close();

        return agentInfo;

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createAgent(AgentInfo agentInfo){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);

        Agent agent = agentInfo.getAgent(em);

        //Validation for agentInfo

        agent = agentService.save(agent);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI agentUri = ub.path("agent"+"/"+Integer.toString(agent.getId())).build();

        em.close();

        return Response.created(agentUri).build();
    }

    @PUT
    @Path("{playId:[0-9]*}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateAgent(AgentInfo agentInfo){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);

        //Validations - Todo other validtions
        if(agentService.findAgentById(agentInfo.getId())== null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Agent agent = agentInfo.getAgent(em);

        agent = agentService.save(agent);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI agentUri = ub.path("agent"+"/"+Integer.toString(agent.getId())).build();

        em.close();

        return Response.ok(agentUri).build();
    }

    @DELETE
    @Path("{agentId:[0-9]*}")
    public Response deleteAgent(@PathParam("agentId") int agentId){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        AgentService agentService = new AgentService(flashserv);

        Agent agent = agentService.findAgentById(agentId);

        if(agent == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        agentService.delete(agentId);

        em.close();

        return Response.ok().build();
    }

}
