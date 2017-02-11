package gr.aueb.mscis.theater.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.persistence.JPAUtil;

/**
 * CRUD operations and other functionality related to movies
 * 
 * @author bzafiris
 *
 */
public class PlayService {

	EntityManager em;
	
	public PlayService() {
		em = JPAUtil.getCurrentEntityManager();
	}
	
	public Play createPlay(String title, int year, String writer){
		
		if (title == null || year < -1000 || writer == null){
			return null;
		}
		
		Play newPlay = new Play(title, year, writer);
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(newPlay);
		tx.commit();
		
		return newPlay;
		
	}

	public Play findPlayById(int id) {
		return em.find(Play.class, id);
	}
	
}
