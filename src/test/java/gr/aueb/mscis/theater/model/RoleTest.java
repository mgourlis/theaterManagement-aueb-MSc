package gr.aueb.mscis.theater.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoleTest {

    Play play;
    Role role;

    @Before
    public void setUp() throws Exception {
        play = new Play("test play","description");
        play.addRole(new Role("test role",RoleType.Actor));
        role = play.getRoles().iterator().next();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getName() throws Exception {
        assertEquals("test role", role.getName());
    }

    @Test
    public void setName() throws Exception {
        role.setName("test");
        assertEquals("test", role.getName());
    }

    @Test
    public void getRoleType() throws Exception {
        assertEquals(RoleType.Actor, role.getRoleType());
    }

    @Test
    public void setRoleType() throws Exception {
        role.setRoleType(RoleType.Operator);
        assertEquals(RoleType.Operator, role.getRoleType());
    }

    @Test
    public void getPlay() throws Exception {
        assertTrue(play.equals(role.getPlay()));
    }

    @Test
    public void getAgent() throws Exception {
        assertNull(role.getAgent());
    }

    @Test
    public void setAgent() throws Exception {
        Agent a = new Agent("a","b",1900,"");
        role.setAgent(a);
        assertEquals("a",role.getAgent().getFirstName());
    }

    @Test
    public void equals() throws Exception {
        Role r = new Role("test role",RoleType.Actor);
        assertFalse(role.equals(r));
        play.addRole(r);
        assertTrue(role.equals(r));
    }

}