package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Sector;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SectorInfo {

	@XmlElement(name="id")
    private int id;
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