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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Myron on 19/2/2017.
 */
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

        pserv.delete(created.getId());

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

    }

    @Test
    public void AlterAgent() throws Exception {

    }

    @Test
    public void DeleteAgent() throws Exception {

    }

    @Test
    public void CreateRoleInPlay() throws Exception {

    }

    @Test
    public void DeleteRole() throws Exception {

    }


}
