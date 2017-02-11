package gr.aueb.mscis.theater.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import gr.aueb.mscis.theater.model.Play;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.theater.persistence.Initializer;
import gr.aueb.mscis.theater.persistence.JPAUtil;

public class PlayServiceTest {
	
	protected EntityManager em;
	
	@Before
	public void setup(){
		// prepare database for each test
		em = JPAUtil.getCurrentEntityManager();
		Initializer dataHelper = new Initializer();
		dataHelper.prepareData();
		
	}
	
	@After
	public void tearDown(){
		em.close();
	}
	
	@Test
	public void testFindExistingMovie() {
	
		Play play = em.find(Play.class, 1);
		Assert.assertNotNull(play);
		Assert.assertEquals("Amlet", play.getTitle());
		
	}
	
	@Test
	public void testPersistAValidMovie(){
		
		PlayService service = new PlayService();
		Play newPlay = service.createPlay("Django", 2012, "Tarantino");
	
		Assert.assertNotEquals(0, newPlay.getId());
		em.close();
		
		em = JPAUtil.getCurrentEntityManager();	
		// assertions
		// 3th object created in current test (+ 2 created in previous)
		// don't do this in real tests, use query instead
		Play savedPlay = em.find(Play.class, 3);
		Assert.assertNotNull(savedPlay);
		Assert.assertEquals("Django", savedPlay.getTitle());
		
	}
	
	@Test
	public void testDenyCreationOfInvalidMovie(){
		PlayService service = new PlayService();
		Play newPlay = service.createPlay(null, 2012, "Tarantino");
	
		Assert.assertNull(newPlay);
	}
	
	@Test
	public void testUpdateMovie_afterFind(){
		
		// create a movie in another use case
		PlayService service = new PlayService();
		Play newPlay = service.createPlay("Django", 2012, "Tarantino");
		em.close();
		
		int movieId = newPlay.getId();
		
		em = JPAUtil.getCurrentEntityManager();
		// find the movie and update its title
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Play savedPlay = em.find(Play.class, movieId);
		Assert.assertNotNull(savedPlay);
		savedPlay.setTitle("Django Unchained");
		tx.commit();
		em.close();
		
		// assertions
		em = JPAUtil.getCurrentEntityManager();
		Play m = em.find(Play.class, movieId);
		Assert.assertEquals("Django Unchained", m.getTitle());
		
		
	}
	
	@Test
	public void testMergeMovie_withSavedVersion(){
		
		// Receive a Play object with assigned id (e.g. from form submission)
		Play play = new Play("Snowden", 2012, "O. Stone");
		play.setId(1);
		
		// Update database with data in play object
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Play savedPlay = em.merge(play);
		Assert.assertNotNull(savedPlay);
		
		tx.commit();
		em.close();
		
		// assertions
		em = JPAUtil.getCurrentEntityManager();
		Play m = em.find(Play.class, 1);
		Assert.assertEquals("O. Stone", m.getWriter());
		Assert.assertEquals(2012, m.getYear());
		
	}

}
