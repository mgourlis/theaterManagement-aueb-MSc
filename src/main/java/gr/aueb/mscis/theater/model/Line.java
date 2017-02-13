package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.*;

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
    @OrderBy("seatNumber asc")
    private List<Seat> seats = new ArrayList<Seat>();

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

    public List<Seat> getSeats() {
        return new ArrayList<Seat>(seats);
    }

    public void addSeat(){
        Seat s = new Seat(this.seats.size()+1);
    }

    public void removeSeat(Seat seat){
        ListIterator<Seat> lit = this.seats.listIterator();
        while (lit.hasNext()){
            if(lit.next().equals((seat))) {
                reorderSeats(seat);
                lit.remove();
            }
        }
    }

    public boolean isAvailable(Date date){
        boolean av = false;
        for (Seat seat : seats) {
            if(seat.isAvailable(date))
                av = true;
        }
        return av;
    }

    public boolean isAvailable(Date startdate, Date enddate){
        boolean av = false;
        for (Seat seat : seats) {
            if(seat.isAvailable(startdate,enddate))
                av = true;
        }
        return av;
    }

    private void reorderSeats(Seat seat){
        ListIterator<Seat> lit = this.seats.listIterator(this.seats.indexOf(seat));
        int seatNumber = seat.getSeatNumber();
        while (lit.hasNext()){
            lit.next().setSeatNumber(seatNumber);
            seatNumber++;
        }
    }
}
