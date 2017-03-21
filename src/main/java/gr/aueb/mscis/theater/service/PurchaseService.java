package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.*;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import java.util.Date;
import java.util.List;

public class PurchaseService {

    EntityManager em;
    FlashMessageService flashserv;
    
    public PurchaseService(FlashMessageService flashserv) {
    	this.em = JPAUtil.getCurrentEntityManager();
    	this.flashserv = flashserv;
    }
	
	public Purchase newPurchase(Date date,
								String wayOfPurchase,
								Integer quantity)
	{
		Purchase  purchase = new Purchase(date, wayOfPurchase, quantity);

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(purchase);
		tx.commit();
		
		return purchase;
	}
	
	public Purchase findPurchaseById(int id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Purchase purchase = null;
		purchase = em.find(Purchase.class, id);

		if(purchase == null){
            flashserv.addMessage("Purchase does not exist",FlashMessageType.Warning);
        }

        tx.commit();
		return purchase;
	}

    public List<Purchase> findPurchaseByDate(Date date) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Purchase> results = null;
            results = em.createQuery("select purchase from Purchase purchase where purchase.date = :Date")
                    .setParameter("Date", date)
                    .getResultList();
            tx.commit();
        if(results.isEmpty()){
            flashserv.addMessage("Purchase not found",FlashMessageType.Info);
        }
        return results;
    }
	
    public List<Purchase> findPurchaseBywayOfPurchase(String wayOfPurchase) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Purchase> results = null;
            results = em.createQuery("select purchase from Purchase purchase where purchase.wayOfPurchase :wayOfPurchase").setParameter("wayOfPurchase", wayOfPurchase).getResultList();
            tx.commit();
            if(results.isEmpty()){
                flashserv.addMessage("Purchase not found",FlashMessageType.Info);
            }
        return results;
    }
    
	public Purchase save(Purchase purchase) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (purchase.getId() != null) {
			try {
				purchase = em.merge(purchase);
				em.flush();
				em.refresh(purchase);
			} catch (PersistenceException ex) {
				tx.rollback();
				flashserv.addMessage("Changed data not valid",FlashMessageType.Error);
				return null;
			}
		} else {
			try {
				em.persist(purchase);
			} catch (PersistenceException ex) {
				tx.rollback();
				flashserv.addMessage("Data not valid",FlashMessageType.Error);
				return null;
			}
		}
		tx.commit();
		return purchase;
	}
}
