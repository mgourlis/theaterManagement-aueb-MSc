package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.model.Show;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Myron on 9/2/2017.
 */
@XmlRootElement
public class HallInfo {

    private Integer id;
    private String name;
    
    
    private List<SectorInfo> sectors;
    //private List<Show> shows = new ArrayList<ShowInfo>();    
    
    public HallInfo() {

	}
    
//	public HallInfo(Integer id, String name, List<SectorInfo> sectors, List<Show> shows) {
//		this.id = id;
//		this.name = name;
//		this.sectors = sectors;
//		this.shows = shows;
//	}

	public HallInfo(Hall hall) {
		this.id = hall.getId();
		this.name = hall.getName();
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

	@XmlElement(name="hallSectors")
	public List<SectorInfo> getsectors() {
		return sectors;
	}
	
	public void setSectors(List<SectorInfo> sectors) {
		this.sectors = sectors;
	}
	
	public static List<HallInfo> wrap(List<Hall> halls) {

		List<HallInfo> hallInfoList = new ArrayList<HallInfo>();

		for (Hall h : halls) {
			hallInfoList.add(new HallInfo(h));
		}

		return hallInfoList;

	}
	
	
    }
