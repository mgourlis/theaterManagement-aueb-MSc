package gr.aueb.mscis.theater.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.model.Ticket;
import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Seat;

@XmlRootElement
public class SeatInfo {
	
    private int id;
    private int lineNumber;
    private int seatNumber;
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
    
    public int getId() {
		return id;
	}
	
	public void setId(int id) {
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
	
}