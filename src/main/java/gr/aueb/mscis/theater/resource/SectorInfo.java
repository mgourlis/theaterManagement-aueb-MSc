package gr.aueb.mscis.theater.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Seat;
import gr.aueb.mscis.theater.model.Sector;

@XmlRootElement
public class SectorInfo {
	
    private int id;
    private String name;
    private double priceFactor;
    
    
    private List<SeatInfo> seats;

	public SectorInfo() {

	}

	public SectorInfo(Sector sector) {
		this.id = sector.getId();
		this.name = sector.getName();
		this.priceFactor = sector.getPriceFactor();
		this.seats = SeatInfo.wrap(sector.getSeats());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@XmlElement(name="SectorSeats")
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
    
}