package gr.aueb.mscis.theater.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Myron on 18/2/2017.
 */


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
        Assert.assertEquals("name",agent.getFirstName());
    }

    @Test
    public void setFirstName() throws Exception {
        agent.setFirstName("name2");
        Assert.assertEquals("name2",agent.getFirstName());
    }

    @Test
    public void getLastName() throws Exception {
        Assert.assertEquals("surname",agent.getLastName());
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
        Assert.assertEquals(1950,agent.getYearOfBirth());
    }

    @Test
    public void getCv() throws Exception {
        Assert.assertEquals("test cv", agent.getCv());
    }

    @Test
    public void setCv() throws Exception {
        agent.setCv("test cv changed");
        Assert.assertEquals("test cv changed", agent.getCv());
    }

    @Test
    public void getRoles() throws Exception {
        Assert.assertEquals(1,agent.getRoles().size());
    }

    @Test
    public void addRole() throws Exception {
        Role role2 = new Role("role2",RoleType.Operator);
        play.addRole(role2);
        agent.addRole(role2);
        Assert.assertEquals(2, agent.getRoles().size());
    }

    @Test
    public void removeRole() throws Exception {
        Role role = new Role("role1", RoleType.Actor);
        Role role2 = new Role("role2", RoleType.Actor);
        play.addRole(role);
        play.addRole(role2);
        assertTrue(agent.removeRole(role));
        Assert.assertEquals(0, agent.getRoles().size());
        Assert.assertEquals(2, play.getRoles().size());
        assertFalse(agent.equals(play.getRoles().iterator().next().getAgent()));
    }

    @Test
    public void equals() throws Exception {
        Agent agent2 = new Agent("name","surname",1900,"");
        Assert.assertTrue(agent.equals(agent2));
    }

}