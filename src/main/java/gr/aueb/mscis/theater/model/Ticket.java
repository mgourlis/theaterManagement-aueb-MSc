package gr.aueb.mscis.theater.model;

import java.util.UUID;

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

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="show_id", nullable = true)
    private Show show;

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="seat_id", nullable = true)
    private Seat seat;

	@ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="purchase_id", nullable = false)
    private Purchase purchase;
    
    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Ticket() { }

    public Ticket(Show show, Seat seat) {
        if(!show.getHall().equals(seat.getSector().getHall()))
            throw new IllegalArgumentException("Not same Hall between show and seat");
        this.show = show;
        this.seat = seat;
        this.serial = UUID.randomUUID().toString();
        this.active = true;
        this.moneyReturn = false;
        this.price = show.getPrice()*seat.getSector().getPriceFactor();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (Double.compare(ticket.price, price) != 0) return false;
        if (!serial.equals(ticket.serial)) return false;
        if (!show.equals(ticket.show)) return false;
        return seat.equals(ticket.seat);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = serial.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + show.hashCode();
        result = 31 * result + seat.hashCode();
        return result;
    }
}
