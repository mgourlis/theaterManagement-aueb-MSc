package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Myron on 12/2/2017.
 */
@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "canceled", nullable = false)
    private boolean canceled;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="play_id", nullable = false)
    private Play play;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="hall_id", nullable = false)
    private Hall hall;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "show")
    private Set<Ticket> tickets = new HashSet<Ticket>();

    public Show(){

    }

    public Show(Date date, double price, Play play, Hall hall) {
        this.date = date;
        this.price = price;
        this.play = play;
        this.hall = hall;
        this.canceled = false;
        this.hall.getShows().add(this);
        this.play.getShows().add(this);
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        if(!play.equals(this.play) && play != null){
            Iterator<Show> it = this.play.getShows().iterator();
            while (it.hasNext()){
                Show sh = it.next();
                if(sh.getPlay().equals(this.play) && sh.getDate().equals(this.date)){
                    it.remove();
                    this.play = play;
                    play.getShows().add(this);
                    break;
                }
            }
        } else {
            this.play = play;
        }
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        if(!hall.equals(this.hall) && hall != null){
            Iterator<Show> it = this.hall.getShows().iterator();
            while (it.hasNext()){
                Show sh = it.next();
                if(sh.getHall().equals(this.hall) && sh.getDate().equals(this.date)){
                    it.remove();
                    this.hall = hall;
                    hall.getShows().add(this);
                    break;
                }
            }
        }else {
            this.hall = hall;
        }
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket){
        ticket.setShow(this);
        this.tickets.add(ticket);
    }

    public boolean removeTicket(Ticket ticket){
        ticket.setShow(null);
        return this.tickets.remove(ticket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (canceled != show.canceled) return false;
        if (!date.equals(show.date)) return false;
        if (!play.equals(show.play)) return false;
        return hall.equals(show.hall);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = date.hashCode();
        result = 31 * result + (canceled ? 1 : 0);
        result = 31 * result + play.hashCode();
        result = 31 * result + hall.hashCode();
        return result;
    }
}
