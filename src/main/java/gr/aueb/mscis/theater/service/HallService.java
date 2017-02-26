package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.*;
import java.util.List;

public class HallService {

    EntityManager em;
    FlashMessageService flashserv;

    public HallService(FlashMessageService flashserv) {
        em = JPAUtil.getCurrentEntityManager();
        this.flashserv = flashserv;
    }


    public List<Hall> findAllHalls() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<Hall> results = null;
        results = em.createQuery("select h from Hall h").getResultList();
        if (results.isEmpty()) {
            flashserv.addMessage("No results found", FlashMessageType.Info);
        }
        tx.commit();

        return results;
    }

    public Hall findHallById(int id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Hall hall = null;
        hall = em.find(Hall.class, id);
        tx.commit();
        if(hall == null){
            flashserv.addMessage("Hall not found", FlashMessageType.Warning);
        }
        return hall;
    }

    public List<Hall> findHallsByName(String name) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Hall> results = null;
        results = em.createQuery("select h from Hall h where h.name like :hallName")
                    .setParameter("hallName", "%"+name+"%").getResultList();
        if(results.isEmpty()){
            flashserv.addMessage("No results found", FlashMessageType.Info);
        }
        tx.commit();
        return results;
    }

    public Hall findHallByName(String name) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Hall hall = null;
        try {
            hall = ((Hall) em.createQuery("select h from Hall h where h.name = :hallName")
                    .setParameter("hallName", name).getSingleResult());
        } catch (NoResultException ex) {
            tx.rollback();
            flashserv.addMessage("Hall not found", FlashMessageType.Warning);
        }
        tx.commit();
        return hall;
    }

    public List<Hall> findAvailableHalls() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Hall> results = null;
        results = em.createQuery("select h from Hall h").getResultList();
        for (Hall hall : results) {
            if (!hall.isAvailable())
                results.remove(hall);
        }
        if(results.isEmpty()){
            flashserv.addMessage("There are no available Halls", FlashMessageType.Info);
        }
        tx.commit();
        return results;
    }


    public Hall save(Hall hall) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //Validate on same name with existing
        if (hall.getId() != null) {
            try {
                hall = em.merge(hall);
                //Flush to make a refresh of the Entity
                em.flush();
                //Refv cresh for correct ordering in seats
                em.refresh(hall);
            } catch (PersistenceException ex) {
                tx.rollback();
                flashserv.addMessage("Changed data provided not valid", FlashMessageType.Error);
                return null;
            }
        } else {
            try {
                Hall existingHall = ((Hall) em.createQuery("select h from Hall h where h.name = :hallName")
                        .setParameter("hallName", hall.getName()).getSingleResult());
                tx.rollback();
                flashserv.addMessage("Hall already exists", FlashMessageType.Error);
                return null;
            } catch (NoResultException ex) {
                try{
                    em.persist(hall);
                } catch (PersistenceException ex2) {
                    tx.rollback();
                    flashserv.addMessage("Data for Hall provided not valid", FlashMessageType.Error);
                    return null;
                }
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
            if(hall.getShows().isEmpty()) {
                em.remove(hall);
                tx.commit();
            }else{
                tx.commit();
                flashserv.addMessage("Hall has shows and cannot be deleted", FlashMessageType.Error);
                return false;
            }
        } catch (EntityNotFoundException e) {
            flashserv.addMessage("Hall not found", FlashMessageType.Error);
            tx.rollback();
            return false;
        }

        return true;
    }


}
