package gr.aueb.mscis.theater.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Myron on 11/2/2017.
 */
public class PlayTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getId() throws Exception {
        Play a= new Play("Test",1997,"test Writer");
        a.setId(1);
        Assert.assertEquals(1,a.getId());
    }

    @Test
    public void setId() throws Exception {

    }

    @Test
    public void getTitle() throws Exception {

    }

    @Test
    public void setTitle() throws Exception {

    }

    @Test
    public void getYear() throws Exception {

    }

    @Test
    public void setYear() throws Exception {

    }

    @Test
    public void getWriter() throws Exception {

    }

    @Test
    public void setWriter() throws Exception {

    }

}