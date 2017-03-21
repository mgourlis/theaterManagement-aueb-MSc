package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Seat;
import gr.aueb.mscis.theater.model.Sector;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SectorInfo {

	@XmlElement(name="id")
    private Integer id;
	@XmlElement(name="name")
    private String name;
	@XmlElement(name="priceFactor")
    private double priceFactor;
	@XmlElementWrapper(name = "SeatInfoes")
	@XmlElement(name = "SeatInfo")
    private List<SeatInfo> seats;

	public SectorInfo() {

	}

	public SectorInfo(Sector sector) {
		this.id = sector.getId();
		this.name = sector.getName();
		this.priceFactor = sector.getPriceFactor();
		this.seats = SeatInfo.wrap(sector.getSeats());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPriceFactor() {
		return priceFactor;
	}

	public void setPriceFactor(double priceFactor) {
		this.priceFactor = priceFactor;
	}


	public List<SeatInfo> getSeats() {
		return seats;
	}

	public void setSeats(List<SeatInfo> seats) {
		this.seats = seats;
	}
	
	public static List<SectorInfo> wrap(Set<Sector> Sectors) {

		List<SectorInfo> SectorInfoList = new ArrayList<SectorInfo>();

		for (Sector h : Sectors) {
			SectorInfoList.add(new SectorInfo(h));
		}

		return SectorInfoList;
	}

	public Sector getSector(EntityManager em){

		Sector sector = null;

		if (id != null) {
			sector = em.find(Sector.class, id);
		} else {
			sector = new Sector();
		}

		sector.setName(name);
		sector.setPriceFactor(priceFactor);
		List<Seat> newseats = new ArrayList<Seat>();
		for (SeatInfo s : seats){
			Seat seat = s.getSeat(em);
			seat.setSector(sector);
			newseats.add(seat);
		}
		sector.getSeats().clear();
		sector.getSeats().addAll(newseats);

		return sector;
	}
    
}