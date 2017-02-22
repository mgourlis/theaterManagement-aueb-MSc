package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;

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

	public List<Play> findAllPlays() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		List<Play> results = null;
		try {
			results = em.createQuery("select p from Play p").getResultList();
		} catch (NoResultException ex) {
			tx.rollback();
		}
		tx.commit();

		return results;
	}

	public Play findPlayById(int id) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Play play = null;
		try {
			play = em.find(Play.class, id);
			tx.commit();
		} catch (NoResultException ex) {
			tx.rollback();
		}
		return play;
	}

	public List<Play> findPlaysByTitle(String title) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<Play> results = null;
		try {
			results = em.createQuery("select p from Play p where p.title like :title")
					.setParameter("title", "%"+title+"%").getResultList();
			tx.commit();
		} catch (NoResultException ex) {
			tx.rollback();
		}
		return results;
	}

	public Play save(Play play) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (play.getId() != null) {
			play = em.merge(play);
			em.flush();
			em.refresh(play);
		} else {
			em.persist(play);
		}
		tx.commit();
		return play;
	}

	public boolean delete(int playId) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Play play = em.getReference(Play.class, playId);
			em.remove(play);
			em.flush();
		} catch (EntityNotFoundException e) {
			tx.rollback();
			return false;
		}

		tx.commit();

		return true;
	}
	
}
