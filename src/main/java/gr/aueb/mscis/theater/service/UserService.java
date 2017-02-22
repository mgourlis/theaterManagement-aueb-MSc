package gr.aueb.mscis.theater.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Myron on 18/2/2017.
 */
public class UserService {

    EntityManager em;

    public UserService(EntityManager em) {
    	this.em = em;
    }

	public User findUserById(int id) {
		return em.find(User.class, id);
	}
    
	public User newUser(User user)
	{
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(user);
		tx.commit();
		
		return user;
	}

    public User findUserByEmail(String email)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        User results = null;
        try {
            results = (User) em.createQuery("select user from User user where user.email = :userEmail").setParameter("userEmail", email).getSingleResult();
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
    
	public User saveUser(User user)
	{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (user.getId() != null) {
            user = em.merge(user);
		}
		else {
            try {
                User existingUser = ((User) em.createQuery("select u from User u where u.email = :userEmail")
                        .setParameter("userEmail", user.getEmail()).getSingleResult());
                tx.rollback();
                return null;
            } catch (NoResultException ex) {
                em.persist(user);
            }
        }

		tx.commit();
        return user;
	}
}
