package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Sector;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HallInfo {

	@XmlElement(name="id")
    private Integer id;
	@XmlElement(name="name")
    private String name;
	@XmlElementWrapper(name = "SectorInfoes")
	@XmlElement(name = "SectorInfo")
    private List<SectorInfo> sectors;
    
    public HallInfo() {

	}

//	public HallInfo(Integer id, String name, List<SectorInfo> sectors, List<Show> shows) {
//		this.id = id;
//		this.name = name;
//		this.sectors = sectors;
//		this.shows = shows;
//	}

	public HallInfo(Hall hall, boolean alldata) {
		this.id = hall.getId();
		this.name = hall.getName();
		if(alldata)
			this.sectors = SectorInfo.wrap(hall.getSectors());
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

	public List<SectorInfo> getsectors() {
		return sectors;
	}


	public void setSectors(List<SectorInfo> sectors) {
		this.sectors = sectors;
	}
	
	public static List<HallInfo> wrap(List<Hall> halls, boolean alldata) {

		List<HallInfo> hallInfoList = new ArrayList<HallInfo>();

		for (Hall h : halls) {
			hallInfoList.add(new HallInfo(h,alldata));
		}

		return hallInfoList;

	}

	public Hall getHall(EntityManager em){
		Hall hall = null;

		if (id != null) {
			hall = em.find(Hall.class, id);
		} else {
			hall = new Hall();
		}

		hall.setName(name);
		Set<Sector> newsectors = new HashSet<Sector>();
		for (SectorInfo s : sectors){
			Sector sector = s.getSector(em);
			sector.setHall(hall);
			newsectors.add(sector);
		}
		hall.getSectors().clear();
		hall.getSectors().addAll(newsectors);

		return hall;
	}
	
	
}
