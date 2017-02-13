package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.*;

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
    @OrderBy("lineNumber asc")
    private List<Line> lines = new ArrayList<Line>();

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

    public List<Line> getLines() {
        return new ArrayList<Line>(lines);
    }

    public void addLine(Line templine){
        Line line = new Line(templine.getLineNumber());
        line.
        this.lines.add(line);
    }

    public boolean isAvailable(Date date){
        boolean av = false;
        for (Line line : lines) {
            if(line.isAvailable(date))
                av = true;
        }
        return av;
    }

    public boolean isAvailable(Date startdate, Date enddate){
        boolean av = false;
        for (Line line : lines) {
            if(line.isAvailable(startdate,enddate))
                av = true;
        }
        return av;
    }
}
