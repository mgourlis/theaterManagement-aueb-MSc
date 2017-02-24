package gr.aueb.mscis.theater.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AgentTest {

    Agent agent;
    Play play;

    @Before
    public void setUp() throws Exception {
        agent = new Agent("name","surname",1900,"test cv");
        play = new Play("title","description");
        play.addRole(new Role("role1",RoleType.Actor));
        agent.addRole(play.getRoles().iterator().next());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getFirstName() throws Exception {
        assertEquals("name",agent.getFirstName());
    }

    @Test
    public void setFirstName() throws Exception {
        agent.setFirstName("name2");
        assertEquals("name2",agent.getFirstName());
    }

    @Test
    public void getLastName() throws Exception {
        assertEquals("surname",agent.getLastName());
    }

    @Test
    public void setLastName() throws Exception {
        agent.setLastName("surname2");
        Assert.assertEquals("surname2",agent.getLastName());
    }

    @Test
    public void getYearOfBirth() throws Exception {
        Assert.assertEquals(1900, agent.getYearOfBirth());
    }

    @Test
    public void setYearOfBirth() throws Exception {
        agent.setYearOfBirth(1950);
        assertEquals(1950,agent.getYearOfBirth());
    }

    @Test
    public void getCv() throws Exception {
        assertEquals("test cv", agent.getCv());
    }

    @Test
    public void setCv() throws Exception {
        agent.setCv("test cv changed");
        assertEquals("test cv changed", agent.getCv());
    }

    @Test
    public void getRoles() throws Exception {
        assertEquals(1,agent.getRoles().size());
    }

    @Test
    public void addRole() throws Exception {
        Role role2 = new Role("role2",RoleType.Operator);
        play.addRole(role2);
        agent.addRole(role2);
        assertEquals(2, agent.getRoles().size());
    }

    @Test
    public void removeRole() throws Exception {
        Role role = new Role("role1", RoleType.Actor);
        Role role2 = new Role("role2", RoleType.Actor);
        play.addRole(role);
        play.addRole(role2);
        assertTrue(agent.removeRole(role));
        assertEquals(0, agent.getRoles().size());
        assertEquals(2, play.getRoles().size());
        assertFalse(agent.equals(play.getRoles().iterator().next().getAgent()));
    }

    @Test
    public void equals() throws Exception {
        Agent agent2 = new Agent("name","surname",1900,"");
        assertTrue(agent.equals(agent2));
    }

}