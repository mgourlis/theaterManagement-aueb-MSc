package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="purchases")
public class Purchase {
	
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
	@Column(name="date")
	private Date date;
	
    @Column(name="quantity", nullable = false)
    private Integer quantity;
	
    @Column(name="totalAmount", nullable = false)
    private Double totalAmount;
    
    @Column(name="wayOfPurchase", length=50, nullable = false)
    private String wayOfPurchase;

    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false)
	private User user;

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
    CascadeType.DETACH,CascadeType.MERGE}, mappedBy = "purchase")
	private Set<Ticket> tickets = new HashSet<Ticket>();
    
    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Purchase() { }

    /**
     * Βοηθητικός κατασκευαστής.
     * @param date Η ημερομηνία αγοράς
     * @param wayOfPurchase Ο τρόπος πληρωμής
     * @param quantity Το πλήθος των εισιτηρίων
     * @param totalAmount Το συνολικό κόστος
     */
    public Purchase(Date date,
    		    	String wayOfPurchase,
    		    	Integer quantity,
    		    	Double totalAmount) {

    	this.date = date;
        this.wayOfPurchase = wayOfPurchase;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Θέτει την ημερομηνία αγοράς.
     * @param date Η ημερομηνία αγοράς
     */
    public void setPurchaseDate(Date date) {
        this.date = date;
    }

    /**
     * Επιστρέφει την ημερομηνία αγοράς.
     * @return Η ημερομηνία αγοράς
     */
    public Date getPurchaseDate() {
        return date;
    }
    
    /**
     * Θέτει τον τρόπο πληρωμής.
     * @param wayOfPurchase Ο τρόπος πληρωμής
     */
    public void setWayOfPurchase(String wayOfPurchase) {
        this.wayOfPurchase = wayOfPurchase;
    }

    /**
     * Επιστρέφει τον τρόπο πληρωμής.
     * @return Ο τρόπος πληρωμής.
     */
    public String getWayOfPurchase() {
        return wayOfPurchase;
    }

    /**
     * Θέτει την ποσότητα των εισητηρίων.
     * @param quantity Η ποσότητα των εισητηρίων
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Επιστρέφει την ποσότητα των εισιτηρίων.
     * @return Η ποσότητα των εισιτηρίων.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Θέτει τη συνολική τιμή των εισιτηρίων.
     * @param totalAmount Η συνολική τιμή των εισιτηρίων
     */
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Επιστρέφει τη συνολική τιμή των εισητηρίων.
     * @return Η συνολική τιμή των εισητηρίων.
     */
    public Double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Προσθέτει εισιτήριο στην αγορά.
     * @param newTicket Το εισιτήριο που θα προστεθεί στην αγορά.
     */
    public void setTicket(Ticket newTicket) {
        this.tickets.add(newTicket);
        newTicket.setPurchase(this);
    }

    /**
     * Επιστρέφει τα εισιτήρια της αγοράς.
     * @return Τα εισιτήρια της αγοράς.
     */
    public Set<Ticket> getTickets() {
        return tickets;
    }
    
    /**
     * Θέτει τον χρήστη που συνδέεται με την αγορά.
     * @param userOfPurchase Ο χρήστης που έκανε την αγορά
     */
    public void setUser(User userOfPurchase) {
        this.user = userOfPurchase;
        userOfPurchase.setPurchase(this);
    }

    /**
     * Επιστρέφει τον χρήστη που έκανε την αγορά.
     * @return Ο χρήστης της αγοράς.
     */
    public User getUser() {
        return user;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (!date.equals(purchase.date)) return false;
        if (!quantity.equals(purchase.quantity)) return false;
        if (!totalAmount.equals(purchase.totalAmount)) return false;
        if (!wayOfPurchase.equals(purchase.wayOfPurchase)) return false;
        return user.equals(purchase.user);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + totalAmount.hashCode();
        result = 31 * result + wayOfPurchase.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}