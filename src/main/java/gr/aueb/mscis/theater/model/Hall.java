package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 512, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "hall")
    private Set<Sector> sectors = new HashSet<Sector>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "hall")
    private Set<Show> shows = new HashSet<Show>();

    /**
     *
     */
    public Hall(){

    }

    /**
     *
     * @param name
     */
    public Hall(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public Set<Sector> getSectors() {
        return sectors;
    }

    public void addSector(Sector tempSector){
        tempSector.setHall(this);
        this.sectors.add(tempSector);
    }

    public boolean removeSector(Sector tempSector){
        return this.sectors.remove(tempSector);
    }
    
    public boolean isAvailable(){
        for (Sector sector : sectors) {
            if(sector.isAvailable())
                return true;
        }
        return false;
    }

    public void setAvailability(boolean availability) {
        for (Sector sector: sectors ) {
            sector.setAvailability(availability);
        }
    }

    public Set<Show> getShows() {
        return shows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hall hall = (Hall) o;

        return name.equals(hall.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
