package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Seat;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SeatInfo {

	@XmlElement(name="id")
    private Integer id;
	@XmlElement (name="lineNumber")
    private int lineNumber;
	@XmlElement (name="seatNumber")
    private int seatNumber;
	@XmlElement (name="availability")
    private boolean availability;

//    private Set<Ticket> tickets = new HashSet<Ticket>();

	public SeatInfo() {

	}
	
	public SeatInfo(Seat seat) {

		this.id = seat.getId();
		this.lineNumber = seat.getLineNumber();
		this.seatNumber = seat.getSeatNumber();
		this.availability = seat.isAvailable();
	}
    
    public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public boolean isAvailability() {
		return availability;
	}
	
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
//	public Set<Ticket> getTickets() {
//		return tickets;
//	}
//	
//	public void setTickets(Set<Ticket> tickets) {
//		this.tickets = tickets;
//	}
    
	public static List<SeatInfo> wrap(List<Seat> Seats) {

		List<SeatInfo> SeatInfoList = new ArrayList<SeatInfo>();

		for (Seat h : Seats) {
			SeatInfoList.add(new SeatInfo(h));
		}

		return SeatInfoList;
	}

	public Seat getSeat(EntityManager em){

		Seat seat = null;

		if (id != null) {
			seat = em.find(Seat.class, id);
		} else {
			seat = new Seat();
		}

		seat.setAvailability(availability);
		seat.setLineNumber(lineNumber);
		seat.setSeatNumber(seatNumber);

		return seat;
	}
}