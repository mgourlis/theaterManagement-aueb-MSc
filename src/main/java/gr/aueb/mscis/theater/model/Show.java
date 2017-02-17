package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
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

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="play_id", nullable = false)
    private Play play;

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
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
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.play = play;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}