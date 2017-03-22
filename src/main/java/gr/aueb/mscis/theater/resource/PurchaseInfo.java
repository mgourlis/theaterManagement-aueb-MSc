package gr.aueb.mscis.theater.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Purchase;
import gr.aueb.mscis.theater.model.Ticket;
import gr.aueb.mscis.theater.model.User;

@XmlRootElement
public class PurchaseInfo {

    @XmlElement(name="id")
    private Integer id;
    @XmlElement(name="date")
    private Date date;
    @XmlElement(name="quantity")
    private Integer quantity;
    @XmlElement(name="totalAmount")
    private Double totalAmount;
    @XmlElement(name="wayOfPurchase")
    private String wayOfPurchase;
//    @XmlElement(name="user")
//    private UserInfo user;
//    @XmlElement(name="")
//    private Set<Ticket> tickets = new HashSet<Ticket>();
    
	public PurchaseInfo() {
		
	}

    public PurchaseInfo(Purchase purchase) {

        this.id = purchase.getId();
        this.date = purchase.getPurchaseDate();
        this.quantity = purchase.getQuantity();
        this.totalAmount = purchase.getTotalAmount();
        this.wayOfPurchase = purchase.getWayOfPurchase();
    }
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getWayOfPurchase() {
		return wayOfPurchase;
	}

	public void setWayOfPurchase(String wayOfPurchase) {
		this.wayOfPurchase = wayOfPurchase;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

    public static PurchaseInfo wrap(Purchase purchase) {

    	PurchaseInfo purchaseInfo = new PurchaseInfo(purchase);

        return purchaseInfo;

    }

}