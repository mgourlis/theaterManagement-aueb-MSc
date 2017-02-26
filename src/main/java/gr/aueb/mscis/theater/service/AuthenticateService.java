package gr.aueb.mscis.theater.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.JPAUtil;

public class AuthenticateService {

    EntityManager em;
    
    public AuthenticateService () {
    	em = JPAUtil.getCurrentEntityManager() ;
    }
    
    public User validateUser(String email, String password)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        User results = null;
        try {
            results = (User) em.createQuery("select user from User user where user.email = :userEmail and user.password = :userPassword")
            								.setParameter("userEmail", email)
            								.setParameter("userPassword", password)
            								.getSingleResult();
            tx.commit();
        }
        catch (NoResultException ex) {
            tx.rollback();
        }
        catch (NonUniqueResultException ex) {
            tx.rollback();
        }
        return results;
    }
    
}
