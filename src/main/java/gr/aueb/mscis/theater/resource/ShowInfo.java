package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Show;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import gr.aueb.mscis.theater.model.Ticket;

@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class ShowInfo {

	@XmlElement(name="id")
    private int id;
	@XmlElement(name="date")
	private Date date;
	@XmlElement(name="price")
	private double price;
	@XmlElement(name="canceled")
	private boolean canceled;
	@XmlElement(name="play")
	private PlayInfo play;
	@XmlElement(name="hall")
	private HallInfo hall;
//	@XmlElement(name="tickets")
//	private Set<Ticket> tickets = new HashSet<Ticket>();
	
	public ShowInfo() {

	}
	
	public ShowInfo(Show show) {
		this.id = show.getId();
		this.date = show.getDate();
		this.price = show.getPrice();
		this.canceled = show.isCanceled();
		this.play = PlayInfo.wrap(show.getPlay());
		this.hall = new HallInfo(show.getHall(),false);
//		this.tickets = tickets;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public boolean isCanceled() {
		return canceled;
	}
	
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
	public PlayInfo getPlay() {
		return play;
	}
	
	public void setPlay(PlayInfo play) {
		this.play = play;
	}
	
	public HallInfo getHall() {
		return hall;
	}
	
	public void setHall(HallInfo hall) {
		this.hall = hall;
	}
//	
//	public Set<Ticket> getTickets() {
//		return tickets;
//	}
//	
//	public void setTickets(Set<Ticket> tickets) {
//		this.tickets = tickets;
//	}
	
	public static List<ShowInfo> wrap(List<Show> shows) {

		List<ShowInfo> showInfoList = new ArrayList<ShowInfo>();

		for (Show s : shows) {
	        showInfoList.add(new ShowInfo(s));
		}

		return showInfoList;

	}
	
}