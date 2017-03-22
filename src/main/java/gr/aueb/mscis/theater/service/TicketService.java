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

    public Ticket findTicketBySerialNo(String serialNo) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Ticket ticket = null;
        try {
            ticket = ((Ticket) em.createQuery("select t from Ticket t where t.serial = :serialno")
                    .setParameter("serialno", serialNo).getSingleResult());
        } catch (NoResultException ex) {
            tx.rollback();
            flashserv.addMessage("Ticket not found", FlashMessageType.Warning);
        }
        tx.commit();
        return ticket;
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
