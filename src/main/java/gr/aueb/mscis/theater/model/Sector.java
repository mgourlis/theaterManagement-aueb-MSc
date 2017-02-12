package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "sectors")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 512, nullable = false)
    private String name;

    @Column(name = "priceFactor", nullable = false)
    private double priceFactor;

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="hall_id", nullable = false)
    private Hall hall;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sector")
    private Set<Line> lines = new HashSet<Line>();

    /**
     *
     */
    public Sector(){

    }

    /**
     *
     * @param name
     */
    public Sector(String name, double priceFactor){
        super();
        this.name = name;
        this.priceFactor = priceFactor;
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
    public double getPriceFactor() {
        return priceFactor;
    }

    public void setPriceFactor(double priceFactor) {
        this.priceFactor = priceFactor;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Set<Line> getLines() {
        return new HashSet<Line>(lines);
    }

    public void setLines(Set<Line> lines) {
        this.lines = new HashSet<Line>(lines);
    }

}
