package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Ticket;

import javax.persistence.*;

public class TicketService {

    EntityManager em;

    public TicketService(EntityManager em) {
        this.em = em;
    }

    public Ticket save(Ticket ticket) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (ticket.getId() != null) {
            try {
                ticket = em.merge(ticket);
                //Flush to make a refresh of the Entity
                em.flush();
                em.refresh(ticket);
            } catch (PersistenceException ex) {
                tx.rollback();
                return null;
            }
        }
        else {
            try {
                em.persist(ticket);
            }
            catch (PersistenceException ex) {
                tx.rollback();
                return null;
            }
        }
        tx.commit();
        return ticket;
    }
}
