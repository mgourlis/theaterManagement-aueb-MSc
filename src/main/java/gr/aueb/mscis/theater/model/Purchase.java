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

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false)
	private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchase")
	private Set<Ticket> tickets = new HashSet<Ticket>();
    
    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Purchase() { }

    /**
     * Βοηθητικός κατασκευαστής.
     * @param date Η ημερομηνία αγοράς
     * @param wayOfPurchase Ο τρόπος πληρωμής
     * @param quantity Το πλήθος των εισητηρίων
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
     * Επιστρέφει την ποσότητα των εισητηρίων.
     * @return Η ποσότητα των εισητηρίων.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Θέτει τη συνολική τιμή των εισητηρίων.
     * @param totalAmount Η συνολική τιμή των εισητηρίων
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
}