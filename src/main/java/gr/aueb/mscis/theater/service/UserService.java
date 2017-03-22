package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.model.UserType;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import java.util.List;

import javax.persistence.*;

public class UserService {

    EntityManager em;
    FlashMessageService flashserv;

    public UserService(FlashMessageService flashserv) {
        em = JPAUtil.getCurrentEntityManager();
        this.flashserv = flashserv;
    }

    public List<User> findAllCustomers() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<User> results = null;
        results = em.createQuery("select user from User user where user.userCategory.category = :cattype").setParameter("cattype", UserType.Customer).getResultList();
        if (results.isEmpty()) {
            flashserv.addMessage("No results found", FlashMessageType.Info);
        }
        tx.commit();

        return results;
    }

    public List<User> findAllEmployees() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<User> results = null;
        results = em.createQuery("select user from User user where user.userCategory.category != :cattype").setParameter("cattype", UserType.Customer).getResultList();
        if (results.isEmpty()) {
            flashserv.addMessage("No results found", FlashMessageType.Info);
        }
        tx.commit();

        return results;
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
		    try {
                if(user.isPasswordValid()) {
                    user = em.merge(user);
                    em.flush();
                } else {
                    tx.commit();
                    flashserv.addMessage("weak password",FlashMessageType.Error);
                    return null;
                }
            } catch (PersistenceException ex){
		        tx.rollback();
                flashserv.addMessage("Changed data provided not valid", FlashMessageType.Error);
                return null;
            }
		}
		else {
            try {
                User existingUser = ((User) em.createQuery("select u from User u where u.email = :userEmail")
                        .setParameter("userEmail", user.getEmail()).getSingleResult());
                tx.rollback();
                flashserv.addMessage("User already exists", FlashMessageType.Error);
                return null;
            } catch (NoResultException ex) {
                try {
                    if(user.isPasswordValid())
                        em.persist(user);
                    else {
                        tx.commit();
                        flashserv.addMessage("weak password",FlashMessageType.Error);
                        return null;
                    }
                } catch (PersistenceException ex2){
                    tx.rollback();
                    flashserv.addMessage("Data for User provided not valid", FlashMessageType.Error);
                    return null;
                }
            }
        }

		tx.commit();
        return user;
	}

    public boolean deleteUser(int userId) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            User user = em.getReference(User.class, userId);
            em.remove(user);
            tx.commit();
        } catch (EntityNotFoundException e) {
            flashserv.addMessage("User not found", FlashMessageType.Error);
            tx.rollback();
            return false;
        }

        return true;
    }
}
