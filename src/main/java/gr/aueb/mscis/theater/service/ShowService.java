package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.*;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TemporalType;
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

    public List<Show> findAllShows(){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Show> shows = null;
        shows = em.createQuery("select s from Show s").getResultList();
        if(shows.isEmpty()){
            flashserv.addMessage("No shows found",FlashMessageType.Info);
        }
        tx.commit();
        return shows;
    }

    public List<Show> findAllShowsByPlay(int playId){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Show> shows = null;
        shows = em.createQuery("select s from Show s where s.play.id = :playId")
                .setParameter("playId",playId)
                .getResultList();
        if(shows.isEmpty()){
            flashserv.addMessage("No shows found",FlashMessageType.Info);
        }
        tx.commit();
        return shows;
    }

    public Show findShowById(int showId) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Show show = null;
        show = em.find(Show.class, showId);
        tx.commit();
        if(show == null) {
            flashserv.addMessage("Show does not exist",FlashMessageType.Warning);
        }
        return show;
    }

    public List<Show> findFutureShows(){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Show> shows = null;
        Calendar cal = Calendar.getInstance();
        shows = em.createQuery("select s from Show s where s.date > :currentDate")
                .setParameter("currentDate",cal.getTime(),TemporalType.DATE)
                .getResultList();
        if(shows.isEmpty()){
            flashserv.addMessage("No shows found",FlashMessageType.Info);
        }
        tx.commit();
        return shows;
    }

    public List<Show> findFutureShowsByPlay(int playId){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Show> shows = null;
        Calendar cal = Calendar.getInstance();
        shows = em.createQuery("select s from Show s where s.play.id = :playId and s.date > :currentDate")
                .setParameter("playId",playId)
                .setParameter("currentDate",cal.getTime(),TemporalType.DATE)
                .getResultList();
        if(shows.isEmpty()){
            flashserv.addMessage("No shows found",FlashMessageType.Info);
        }
        tx.commit();
        return shows;
    }

    public List<Show> createProgram(Play play, Hall hall, Date startDate, Date endDate, double price){

        EntityTransaction tx = em.getTransaction();
        List<Show> shows = null;
        tx.begin();
        if(endDate.before(startDate)){
            flashserv.addMessage("Not valid date data", FlashMessageType.Error);
            tx.commit();
            return null;
        }
        boolean createForDate = false;
        createForDate = em.createQuery("select s from Show s where s.play.id = :playId and s.date >= :startDate and s.date <= :endDate and s.canceled = false ")
                    .setParameter("playId", play.getId())
                    .setParameter("startDate", startDate, TemporalType.DATE)
                    .setParameter("endDate",endDate, TemporalType.DATE)
                    .getResultList().size() == 0;
        boolean createForHall = false;
        createForHall = em.createQuery("select s from Show s where s.hall.id = :hallId and s.date >= :startDate and s.date < :endDate  and s.canceled = false ")
                    .setParameter("hallId", hall.getId())
                    .setParameter("startDate", startDate, TemporalType.DATE)
                    .setParameter("endDate",endDate, TemporalType.DATE)
                    .getResultList().size() == 0;

        shows = new ArrayList<Show>();
        if(!createForHall || !createForDate) {
            tx.rollback();
            flashserv.addMessage("Show program not created show exists on date or hall not available",FlashMessageType.Error);
            return shows;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            while (cal.getTime().before(endDate) || cal.getTime().equals(endDate)){
                Show show = new Show(cal.getTime(),price,play,hall);
                em.persist(show);
                shows.add(show);
                cal.add(Calendar.DATE,1);
            }
        }
        tx.commit();
        return shows;
    }

    public Show cancelShow(int showId, String message, EmailProvider emailserv){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Show show = null;
        List<User> emailUers = new ArrayList<User>();

            show = em.find(Show.class, showId);
            if(show == null){
                tx.commit();
                flashserv.addMessage("Show not found in database",FlashMessageType.Error);
                return show;
            }
            if(!show.getTickets().isEmpty() && !show.isCanceled()){
                for (Ticket ticket : show.getTickets()){
                    ticket.setMoneyReturn(true);
                    ticket.getSeat().removeTicket(ticket);
                    emailUers.add(ticket.getPurchase().getUser());
                }
            }
            show.setCanceled();
            tx.commit();

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
            show = em.find(Show.class, showId);
            if (!show.getDate().equals(date)){
                makeDateChange = em.createQuery("select s from Show s where s.play.id = :playId and s.date = :date and s.canceled = false")
                            .setParameter("playId", show.getPlay().getId())
                            .setParameter("date", date,TemporalType.DATE)
                            .getResultList().size() == 0;
                if(!makeDateChange) {
                    tx.commit();
                    flashserv.addMessage("A show already exists for this play in that date", FlashMessageType.Error);
                    return null;
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
            } else {
                tx.commit();
                flashserv.addMessage("The show already is in that date", FlashMessageType.Error);
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
            show = em.find(Show.class, showId);
            if(show == null) {
                tx.commit();
                return null;
            }
            show.setPrice(price);
            em.merge(show);

        tx.commit();
        return show;
    }
}
