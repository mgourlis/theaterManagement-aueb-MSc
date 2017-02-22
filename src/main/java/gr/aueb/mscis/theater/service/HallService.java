package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Hall;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by Myron on 18/2/2017.
 */
public class HallService {

    EntityManager em;

    public HallService(EntityManager em) {
        this.em = em;
    }


    public List<Hall> findAllHalls() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<Hall> results = null;
        try {
            results = em.createQuery("select h from Hall h").getResultList();
        } catch (NoResultException ex) {
            tx.rollback();
        }
        tx.commit();

        return results;
    }

    public Hall findHallById(int id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Hall hall = null;
        try {
            hall = em.find(Hall.class, id);
            tx.commit();
        } catch (NoResultException ex) {
            tx.rollback();
        }
        return hall;
    }

    public List<Hall> findHallsByName(String name) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Hall> results = null;
        try {
            results = em.createQuery("select h from Hall h where h.name like :hallName")
                    .setParameter("hallName", "%"+name+"%").getResultList();
            tx.commit();
        } catch (NoResultException ex) {
            tx.rollback();
        }
        return results;
    }

    public Hall findHallByName(String name) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Hall hall = null;
        try {
            hall = ((Hall) em.createQuery("select h from Hall h where h.name = :hallName")
                    .setParameter("hallName", name).getSingleResult());
            tx.commit();
        } catch (NoResultException ex) {
            tx.rollback();
        }
        return hall;
    }

    public List<Hall> findAvailableHalls() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Hall> results = null;
        try {
            results = em.createQuery("select h from Hall h").getResultList();
            for (Hall hall : results) {
                if (!hall.isAvailable())
                    results.remove(hall);
            }
            tx.commit();
        } catch (NoResultException ex) {
            tx.rollback();
        }
        return results;
    }

    public Hall save(Hall hall) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //Validate on same name with existing
        if (hall.getId() != null) {
            hall = em.merge(hall);
            //Flush to make a refresh of the Entity
            em.flush();
            //Refv cresh for correct ordering in seats
            em.refresh(hall);
        } else {
            try {
                Hall existingHall = ((Hall) em.createQuery("select h from Hall h where h.name = :hallName")
                        .setParameter("hallName", hall.getName()).getSingleResult());
                tx.rollback();
                return null;
            } catch (NoResultException ex) {
                em.persist(hall);
            }
        }
        tx.commit();

        return hall;
    }

    public boolean delete(int hallId) {

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Hall hall = em.getReference(Hall.class, hallId);
            em.remove(hall);
        } catch (EntityNotFoundException e) {
            tx.rollback();
            return false;
        }

        tx.commit();

        return true;
    }


}
