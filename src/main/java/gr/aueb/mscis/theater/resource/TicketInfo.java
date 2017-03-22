package gr.aueb.mscis.theater.resource;


import gr.aueb.mscis.theater.model.Ticket;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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

	public TicketInfo() {

	}

	public TicketInfo(Ticket ticket) {
		this.id = ticket.getId();
		this.serial = ticket.getSerial();
		this.price = ticket.getPrice();
		this.moneyReturn = ticket.isMoneyReturn();
		this.active = ticket.isActive();

//		this.moneyReturn = false;
//		this.active = true;
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

	public static TicketInfo wrap(Ticket ticket) {
		return new TicketInfo(ticket);
	}

	public Ticket getTicket(EntityManager em) {

		Ticket ticket = null;

		if (id != null) {
			ticket = em.find(Ticket.class, id);
		} else {
			ticket = new Ticket();
		}

		ticket.setMoneyReturn(moneyReturn);
		ticket.setActive(active);
		ticket.setPrice(price);

		return ticket;
	}

}