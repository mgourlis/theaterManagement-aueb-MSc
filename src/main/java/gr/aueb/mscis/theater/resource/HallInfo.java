package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Hall;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


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
	
	public static List<HallInfo> wrap(List<Hall> halls) {

		List<HallInfo> hallInfoList = new ArrayList<HallInfo>();

		for (Hall h : halls) {
			hallInfoList.add(new HallInfo(h,true));
		}

		return hallInfoList;

	}
	
	
    }
