package gr.aueb.mscis.theater.resource;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.theater.model.Purchase;
import gr.aueb.mscis.theater.model.Seat;
import gr.aueb.mscis.theater.model.Show;
import gr.aueb.mscis.theater.model.Ticket;

@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class TicketInfo {

    @XmlElement(name="id")
    private Integer id;
    @XmlElement(name="serial")
    private String serial;
    @XmlElement(name="price")
    private double price;
    @XmlElement(name="moneyReturn")
    private boolean moneyReturn;
    @XmlElement(name="active")
    private boolean active;
    @XmlElement(name="show")
    private ShowInfo show;
    @XmlElement(name="seat")
    private SeatInfo seat;
//    @XmlElement(name="purchase")
//    private PurchaseInfo purchase;
	
    public TicketInfo() {

	}

	public TicketInfo(Ticket ticket) {
		super();
		this.id = ticket.getId();
		this.serial = ticket.getSerial();
		this.price = ticket.getPrice();
		this.moneyReturn = false;
		this.active = true;
//		this.show = ShowInfo.wrap(ticket.getShow());
//		this.seat = SeatInfo.wrap(ticket.getSeat());
//		this.purchase = SeatInfo.wrap(ticket.getPurchase());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

//	public Show getShow() {
//		return show;
//	}
//
//	public void setShow(Show show) {
//		this.show = show;
//	}
//
//	public Seat getSeat() {
//		return seat;
//	}
//
//	public void setSeat(Seat seat) {
//		this.seat = seat;
//	}
//
//	public Purchase getPurchase() {
//		return purchase;
//	}
//
//	public void setPurchase(Purchase purchase) {
//		this.purchase = purchase;
//	}
	
}