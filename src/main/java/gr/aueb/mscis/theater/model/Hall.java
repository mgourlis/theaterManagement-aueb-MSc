package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

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
        super();
        this.name = name;
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
        return new HashSet<Sector>(sectors);
    }

    public void addSector(Sector tempSector){
        Sector sector = new Sector(tempSector.getName(), tempSector.getPriceFactor());
        sector.setLines(tempSector.getLines());
        sectors.add(sector);
    }

    public boolean removeSector(Sector tempSector){
        return this.sectors.remove(tempSector);
    }

}
