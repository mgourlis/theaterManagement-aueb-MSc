package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.model.Agent;
import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Role;
import gr.aueb.mscis.theater.model.RoleType;
import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.service.AgentService;
import gr.aueb.mscis.theater.service.PlayService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PlayManagementTest {

    Initializer init = new Initializer();
    PlayService pserv;
    AgentService aserv;

    @Before
    public void setUp() throws Exception {
        init.prepareData();
        pserv = new PlayService();
        aserv = new AgentService();

        Play play = new Play("test play","test descr");

        play.addRole(new Role("actor1", RoleType.Actor));
        play.addRole(new Role("actor2", RoleType.Actor));
        play.addRole(new Role("actor3", RoleType.Actor));

        play.addRole(new Role("operator1", RoleType.Operator));
        play.addRole(new Role("operator2", RoleType.Operator));
        play.addRole(new Role("operator3", RoleType.Operator));

        play = pserv.save(play);

        Play created = pserv.findPlayById(play.getId());

        Agent agent1 = new Agent("name1","surname1",1901,"cv1");
        Agent agent2 = new Agent("name2","surname2",1902,"cv2");
        Agent agent3 = new Agent("name3","surname3",1903,"cv3");
        Agent agent4 = new Agent("name4","surname4",1904,"cv4");
        Agent agent5 = new Agent("name5","surname5",1905,"cv5");
        Agent agent6 = new Agent("name6","surname6",1906,"cv6");

        agent1.addRole(created.getRole("actor1", RoleType.Actor));
        agent2.addRole(created.getRole("actor2", RoleType.Actor));
        agent3.addRole(created.getRole("actor3", RoleType.Actor));

        agent4.addRole(created.getRole("operator1", RoleType.Operator));
        agent5.addRole(created.getRole("operator2", RoleType.Operator));
        agent6.addRole(created.getRole("operator3", RoleType.Operator));

        agent1 = aserv.save(agent1);
        agent2 = aserv.save(agent2);
        agent3 = aserv.save(agent3);
        agent4 = aserv.save(agent4);
        agent5 = aserv.save(agent5);
        agent6 = aserv.save(agent6);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void CreatePlay() throws Exception {

        Play created = pserv.findPlaysByTitle("test play").get(0);

        assertEquals(6, created.getRoles().size());
        assertEquals("test play",created.getTitle());

        for (Role role : created.getRoles()){
            assertNotNull(role.getId());
            assertNotNull(role.getAgent());
        }
    }

    @Test
    public void DeletePlay() throws Exception {
        Play created = pserv.findPlaysByTitle("test play").get(0);

        assertTrue(pserv.delete(created.getId()));

        List<Play> plays = pserv.findPlaysByTitle("test play");

        assertEquals(0, plays.size());

        List<Agent> agents = aserv.findAllAgents();

        for (Agent agent : agents){
            for(Role role :agent.getRoles()){
                if(role.getPlay().getTitle().equals("test play"))
                    Assert.fail();
            }
        }
    }

    @Test
    public void CreateAgent() throws Exception {
        Agent agent1 = new Agent("name1","surname1",1901,"cv1");
        agent1 = aserv.save(agent1);
        assertNotNull(aserv.findAgentById(agent1.getId()));
    }

    @Test
    public void AlterAgent() throws Exception {
        Agent agent1 = new Agent("name1","surname1",1901,"cv1");
        agent1 = aserv.save(agent1);
        Agent agentCreated = aserv.findAgentById(agent1.getId());
        agentCreated.setCv("cv2");
        agentCreated.setFirstName("name2");
        aserv.save(agentCreated);
        agent1 = aserv.findAgentById(agentCreated.getId());
        assertEquals("cv2",agent1.getCv());
        assertEquals("name2",agent1.getFirstName());
    }

    @Test
    public void DeleteAgent() throws Exception {
        Agent agent1 = new Agent("name1","surname1",1901,"cv1");
        agent1 = aserv.save(agent1);
        assertNotNull(aserv.findAgentById(agent1.getId()));
        aserv.delete(agent1.getId());
        assertNull(aserv.findAgentById(agent1.getId()));
    }

    @Test
    public void CreateRoleInPlay() throws Exception {
        Play created = pserv.findPlaysByTitle("test play").get(0);
        created.addRole(new Role("newRole",RoleType.Actor));
        pserv.save(created);
        Play createdWithRole = pserv.findPlaysByTitle("test play").get(0);
        assertEquals(7,createdWithRole.getRoles().size());
        try {
            assertNotNull(createdWithRole.getRole("newRole",RoleType.Actor));
        }catch (IllegalArgumentException ex){
            Assert.fail();
        }
    }

    @Test
    public void DeleteRole() throws Exception {
        String roleName = "actor1";
        RoleType type = RoleType.Actor;

        Play created = pserv.findPlaysByTitle("test play").get(0);
        try {
            assertEquals(6,created.getRoles().size());

            assertTrue(created.removeRole(created.getRole(roleName,type)));
            pserv.save(created);
            Play createdWithoutRole = pserv.findPlaysByTitle("test play").get(0);
            assertEquals(5,createdWithoutRole.getRoles().size());
            try {
                createdWithoutRole.getRole("actor1",RoleType.Actor);
                Assert.fail();
            }catch (IllegalArgumentException ex){
                assertTrue(true);
            }
        }catch (IllegalArgumentException ex){
            Assert.fail();
        }
    }


}
