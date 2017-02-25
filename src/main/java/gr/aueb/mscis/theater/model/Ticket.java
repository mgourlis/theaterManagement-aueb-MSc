package gr.aueb.mscis.theater.model;

import gr.aueb.mscis.theater.service.SerialNumberProvider;

import java.util.UUID;

import javax.persistence.*;

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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="show_id", nullable = true)
    private Show show;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="seat_id", nullable = true)
    private Seat seat;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="purchase_id", nullable = true)
    private Purchase purchase;
    
    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Ticket() { }

    /**
     * Κατασκευαστής της κλάσσης Ticket, δημιουργεί αντικείμενο τύπου Ticket
     * @param show η παράσταση του εισιτηρίου
     * @param seat η θέση του εισιτηρίου
     * @param serial ο σειριακός αριθμός του εισιτηρίου
     */
    public Ticket(Show show, Seat seat, SerialNumberProvider serial) {
        if(!show.getHall().equals(seat.getSector().getHall()))
            throw new IllegalArgumentException("Not same Hall between show and seat");
        this.show = show;
        this.seat = seat;
        this.serial = serial.createUniqueSerial();
        this.active = true;
        this.moneyReturn = false;
        this.price = show.getPrice()*seat.getSector().getPriceFactor();
    }

    public int getId() {
        return id;
    }

    /**
     * Επιστρέφει τον σειριακό αριθμό του εισιτηρίου
     * @return ο σειριακός αριθμός
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Θέτει τον σειριακό αριθμό του εισιτηρίου
     * @param serial SerialNumberProvider κατασκευαστής του σειριακού αριθμού
     */
    public void setSerial(SerialNumberProvider serial) {
        this.serial = serial.createUniqueSerial();
    }

    /**
     * Επιστρέφει την τιμή του εισιτηρίου
     * @return η τιμή
     */
    public double getPrice() {
        return price;
    }

    /**
     * Θέτει την τιμή του εισιτηρίου
     * @param price η τιμή
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Ελέγχει αν έχει γίνει επιστροφή χρημάτων
     * @return true/false αν έχει γίνει επιστροφή ή όχι.
     */
    public boolean isMoneyReturn() {
        return moneyReturn;
    }

    /**
     * Θέτει αν έχει γίνει επιστροφή χρημάτων
     * @param moneyReturn true/false αν έχει γίνει επιστροφή ή όχι.
     */
    public void setMoneyReturn(boolean moneyReturn) {
        this.moneyReturn = moneyReturn;
    }

    /**
     * Ελέγχει αν το εισητήριο είναι ενεργό (μη ακυρωμένο)
     * @return true/flase αν είναι ενεργό ή όχι
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Θέτει το εισιτήριο ως ενεργό ή ακυρωμένο
     * @param active true για ενεργό false για ακυρωμένο
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Επιστρέφει την παράσταση του εισιτηρίου
     * @return η παράσταση
     */
    public Show getShow() {
        return show;
    }

    /**
     * Θέτει την παράσταση του εισιτηρίου
     * @param show η παράσταση
     */
    public void setShow(Show show) {
        this.show = show;
    }

    /**
     * Επιστρέφει τη θέση του εισιτηρίου
     * @return η θέση
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * Θέτει τη θέση του εισιτηρίου
     * @param seat η θέση
     */
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    /**
     * Επιστρέφει την αγορά που αντιστοιχεί στο εισιτήριο
     * @return η αγορά
     */
    public Purchase getPurchase() {
    	return purchase;
    }

    /**
     * Θέτει την αγορά που αντιστοιχεί στο εισιτήριο
     * @param purchase η αγορά
     */
    public void setPurchase(Purchase purchase) {
    	this.purchase = purchase;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (!serial.equals(ticket.serial)) return false;
        if (show != null ? !show.equals(ticket.show) : ticket.show != null) return false;
        return seat != null ? seat.equals(ticket.seat) : ticket.seat == null;
    }

    @Override
    public int hashCode() {
        int result = serial.hashCode();
        result = 31 * result + (show != null ? show.hashCode() : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        return result;
    }
}
