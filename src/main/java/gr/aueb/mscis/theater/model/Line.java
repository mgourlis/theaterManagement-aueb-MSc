package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "lines")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "lineNumber", nullable = false)
    private int lineNumber;

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="sector_id", nullable = false)
    private Sector sector;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "line")
    private Set<Seat> seats = new HashSet<Seat>();

    /**
     *
     */
    public Line(){

    }

    /**
     *
     * @param lineNumber
     */
    public Line(int lineNumber) {
        super();
        this.lineNumber = lineNumber;
    }

    /**
     *
     * @return
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     *
     * @param lineNumber
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }


    public Set<Seat> getSeats() {
        return new HashSet<Seat>(seats);
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = new HashSet<Seat>(seats);
    }
}
