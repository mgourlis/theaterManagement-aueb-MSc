package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

public class PlayService {

	EntityManager em;
	FlashMessageService flashserv;
	
	public PlayService(FlashMessageService flashserv) {
		em = JPAUtil.getCurrentEntityManager();
		this.flashserv = flashserv;
	}

	public List<Play> findAllPlays() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		List<Play> results = null;
			results = em.createQuery("select p from Play p").getResultList();
		if(results.isEmpty()) {
			flashserv.addMessage("No results found",FlashMessageType.Info);
		}
		tx.commit();

		return results;
	}

	public Play findPlayById(int id) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Play play = null;
			play = em.find(Play.class, id);
			tx.commit();
		if(play == null) {
			flashserv.addMessage("Play does not exist",FlashMessageType.Warning);
		}
		return play;
	}

	public List<Play> findPlaysByTitle(String title) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<Play> results = null;
			results = em.createQuery("select p from Play p where p.title like :title")
					.setParameter("title", "%"+title+"%").getResultList();

		if(results.isEmpty()) {
			flashserv.addMessage("No results found",FlashMessageType.Info);
		}
		tx.commit();
		return results;
	}

	public Play save(Play play) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (play.getId() != null) {
			try {
				play = em.merge(play);
				em.flush();
				em.refresh(play);
			} catch (PersistenceException ex) {
				tx.rollback();
				flashserv.addMessage("Changed data not valid",FlashMessageType.Error);
				return null;
			}
		} else {
			try {
				em.persist(play);
			} catch (PersistenceException ex) {
				tx.rollback();
				flashserv.addMessage("Data not valid",FlashMessageType.Error);
				return null;
			}
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
			tx.commit();
		} catch (EntityNotFoundException e) {
			flashserv.addMessage("Play not found", FlashMessageType.Error);
			tx.rollback();
			return false;
		}

		return true;
	}
	
}
