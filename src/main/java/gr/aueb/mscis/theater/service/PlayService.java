package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
	
	public Play createPlay(String title, String description){
		
		if (title == null){
			return null;
		}
		
		Play newPlay = new Play(title, description);
		
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
