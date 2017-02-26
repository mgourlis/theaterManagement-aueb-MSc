package gr.aueb.mscis.theater.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import gr.aueb.mscis.theater.model.Purchase;
import gr.aueb.mscis.theater.model.Ticket;

public class PurchaseService {

    EntityManager em;
    
    public PurchaseService(EntityManager em) {
    	this.em = em;
    }
	
	public Purchase newPurchase(Date date,
								String wayOfPurchase,
								Integer quantity,
								Double totalAmount)
	{
		Purchase  purchase = new Purchase(date, wayOfPurchase, quantity, totalAmount);
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(purchase);
		tx.commit();
		
		return purchase;
	}
	
	public Purchase findPurchaseById(int id) {
		return em.find(Purchase.class, id);
	}

    public List<Purchase> findPurchaseByDate(Date date) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Purchase> results = null;
        try {
            results = em.createQuery("select purchase from Purchase purchase where purchase.date = :currentDate").setParameter("currentDate", date).getResultList();
            tx.commit();
        }
        catch (NoResultException ex) {
            tx.rollback();
        }
        return results;
    }
	
    public List<Purchase> findPurchaseBywayOfPurchase(String wayOfPurchase) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Purchase> results = null;
        try {
            results = em.createQuery("select purchase from Purchase purchase where purchase.wayOfPurchase :wayOfPurchase").setParameter("wayOfPurchase", wayOfPurchase).getResultList();
            tx.commit();
        }
        catch (NoResultException ex) {
            tx.rollback();
        }
        return results;
    }
    
    public Purchase save(Purchase purchase) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (purchase.getId() != null) {
            try {
                purchase = em.merge(purchase);
                //Flush to make a refresh of the Entity
                em.flush();
                em.refresh(purchase);
            } catch (PersistenceException ex) {
                tx.rollback();
                return null;
            }
        }
        else {
            try {
                em.persist(purchase);
            }
            catch (PersistenceException ex) {
                tx.rollback();
                return null;
            }
        }
        tx.commit();
        return purchase;
    }
}
