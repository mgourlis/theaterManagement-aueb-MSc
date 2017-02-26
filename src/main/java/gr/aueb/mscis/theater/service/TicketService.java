package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Ticket;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.*;

public class TicketService {

    EntityManager em;
    FlashMessageService flashserv;

    public TicketService(FlashMessageService flashserv) {
        this.em = JPAUtil.getCurrentEntityManager();
        this.flashserv = flashserv;
    }

    public Ticket save(Ticket ticket) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (ticket.getId() != null) {
            try {
                ticket = em.merge(ticket);
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
