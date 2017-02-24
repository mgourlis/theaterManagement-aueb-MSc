package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.*;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShowService {

    EntityManager em;
    FlashMessageService flashserv;

    public ShowService(FlashMessageService flashserv) {
        em = JPAUtil.getCurrentEntityManager();
        this.flashserv = flashserv;
    }

    public List<Show> createProgram(Play play, Hall hall, Date startDate, Date endDate, double price){
        EntityTransaction tx = em.getTransaction();
        List<Show> shows = null;
        tx.begin();

        try {
            em.createQuery("select s from Show s where s.play.id = :playId and s.date > :startDate and s.date < :endDate and s.canceled = false ")
                    .setParameter("playId", play.getId())
                    .setParameter("startDate", startDate)
                    .setParameter("endDate",endDate)
                    .getResultList();
            em.createQuery("select s from Show s where s.hall.id = :hallId and s.date > :startDate and s.date < :endDate  and s.canceled = false ")
                    .setParameter("hallId", hall.getId())
                    .setParameter("startDate", startDate)
                    .setParameter("endDate",endDate)
                    .getResultList();
            tx.rollback();
            flashserv.addMessage("Show program not created show exists on date or hall not available",FlashMessageType.Error);
        } catch (NoResultException ex) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            while (cal.getTime().before(endDate) || cal.getTime().equals(endDate)){
                Show show = new Show(cal.getTime(),price,play,hall);
                em.persist(show);
                shows.add(show);
            }
        }
        flashserv.addMessage("Show program created successfully",FlashMessageType.Info);
        tx.commit();
        return shows;
    }

    public Show cancelShow(int showId, String message, EmailProvider emailserv){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Show show = null;
        List<User> emailUers = new ArrayList<User>();
        try {
            show = em.find(Show.class, showId);
            if(!show.getTickets().isEmpty() && show.isCanceled() == false ){
                for (Ticket ticket : show.getTickets()){
                    ticket.setMoneyReturn(true);
                    ticket.getSeat().removeTicket(ticket);
                    emailUers.add(ticket.getPurchase().getUser());
                }
            }
            show.setCanceled();
            tx.commit();
        } catch (NoResultException ex) {
            tx.rollback();
            flashserv.addMessage("Show not found in database",FlashMessageType.Error);
            return show;
        }

        for(User user : emailUers){
            emailserv.sendEmail(user.getEmail(),message);
        }
        flashserv.addMessage("Show was canceled",FlashMessageType.Error);
        return show;
    }

    public Show alterShowDate(int showId, Date date, String message, EmailProvider emailserv){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Show show = null;
        List<User> emailUers = new ArrayList<User>();
        Boolean makeDateChange = false;
        try {
            show = em.find(Show.class, showId);
            if (!show.getDate().equals(date)){
                makeDateChange = true;
                try {
                    em.createQuery("select s from Show s where s.play.id = :playId and s.date = :date and s.canceled = false")
                            .setParameter("playId", show.getPlay().getId())
                            .setParameter("date", date)
                            .getResultList();
                    tx.rollback();
                    flashserv.addMessage("A show already exists for this play in that date",FlashMessageType.Error);
                    return null;
                } catch (NoResultException ex) {

                }
            }

            if(makeDateChange) {
                show.setDate(date);
                em.merge(show);
                if (!show.getTickets().isEmpty()) {
                    for (Ticket ticket : show.getTickets()) {
                        emailUers.add(ticket.getPurchase().getUser());
                    }
                }
            }

        } catch (NoResultException ex) {
            tx.rollback();
            return null;
        }

        tx.commit();

        for(User user : emailUers){
            emailserv.sendEmail(user.getEmail(),message);
        }

        flashserv.addMessage("Show was altered successfully",FlashMessageType.Info);

        return show;
    }

    public Show alterShowPrice(int showId, double price){
        if(price<0.0)
            return null;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Show show = null;
        try {
            show = em.find(Show.class, showId);
            show.setPrice(price);
            em.merge(show);
        } catch (NoResultException ex) {
            tx.rollback();
            return null;
        }

        tx.commit();
        return show;
    }
}
