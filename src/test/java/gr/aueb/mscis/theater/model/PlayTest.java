package gr.aueb.mscis.theater.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Myron on 18/2/2017.
 */
public class PlayTest {

    Play play;

    @Before
    public void setUp() throws Exception {
        play = new Play("test play","description");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getTitle() throws Exception {
        assertEquals("test play",play.getTitle());
    }

    @Test
    public void setTitle() throws Exception {
        play.setTitle("test");
        assertEquals("test",play.getTitle());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("description",play.getDescription());
    }

    @Test
    public void setDescription() throws Exception {
        play.setDescription("");
        assertEquals("",play.getDescription());
    }

    @Test
    public void getRoles() throws Exception {
        assertEquals(0,play.getRoles().size());
    }

    @Test
    public void addRole() throws Exception {
        play.addRole(new Role("test role1",RoleType.Actor));
        assertEquals(1,play.getRoles().size());
        play.addRole(new Role("test role1",RoleType.Operator));
        assertEquals(2,play.getRoles().size());
    }

    @Test
    public void removeRole() throws Exception {
        play.addRole(new Role("test role1",RoleType.Actor));
        play.addRole(new Role("test role2",RoleType.Operator));
        Role role = play.getRoles().iterator().next();
        assertTrue(play.removeRole(role));
        assertEquals(1,play.getRoles().size());
        role = new Role("test role1", RoleType.Actor);
        assertFalse(play.removeRole(role));
        assertEquals(1,play.getRoles().size());

    }

    @Test
    public void getShows() throws Exception {
        assertEquals(0,play.getShows().size());
    }

    @Test
    public void equals() throws Exception {
        Play play2 = new Play("test play", "description");
        assertTrue(play.equals(play2));
        play2.setDescription("1");
        assertTrue(play.equals(play2));
        play2.setTitle("test");
        assertFalse(play.equals(play2));
    }

}