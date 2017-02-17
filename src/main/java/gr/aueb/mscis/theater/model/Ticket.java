package gr.aueb.mscis.theater.model;

import java.util.Date;

import javax.persistence.*;

/**
 * Created by Myron on 12/2/2017.
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "serial", length = 255, nullable = false)
    private String serial;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "moneyReturn", nullable = false)
    private boolean moneyReturn;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne(optional = false, fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="show_id", nullable = false)
    private Show show;

    @ManyToOne(optional = false, fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="seat_id", nullable = false)
    private Seat seat;

	@ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="purchase_id", nullable = false)
    private Purchase purchase;
    
    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Ticket() { }
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isMoneyReturn() {
        return moneyReturn;
    }

    public void setMoneyReturn(boolean moneyReturn) {
        this.moneyReturn = moneyReturn;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
