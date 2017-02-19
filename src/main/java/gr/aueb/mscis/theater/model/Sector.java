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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="hall_id", nullable = false)
    private Hall hall;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sector")
    @OrderBy("lineNumber, seatNumber asc")
    private List<Seat> seats = new ArrayList<Seat>();

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

    public int getId() {
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

    public List<Seat> getSeats() {
        return seats;
    }

    public void addLine() {
        Seat seat = new Seat(seats.isEmpty() ? 1 : seats.get(seats.size()-1).getLineNumber()+1,1);
        seat.setSector(this);
        this.seats.add(seat);
    }

    public boolean removeLine(int line){
        Boolean removeFlag = false;
        int linelength = lineLength(line);
        if(linelength > 0) {
            ListIterator<Seat> lit = seats.listIterator(seats.size());
            Seat seat = null;
            removeFlag = true;
            while (lit.hasPrevious()){
                seat = lit.previous();
                if (seat.getLineNumber() == line){
                    while (seat.getLineNumber() == line && lit.hasPrevious()) {
                        if (seat.isBooked()) removeFlag = false;
                        seat = lit.previous();
                    }
                    if(lit.hasPrevious())
                        lit.next();
                    break;
                }
            }
            while (lit.hasNext() && removeFlag) {
                seat = lit.next();
                if(seat.getLineNumber() == line) {
                    seat.setSector(null);
                    lit.remove();
                } else {
                    seat.setLineNumber(seat.getLineNumber()-1);
                }
            }
        }
        return removeFlag;
    }

    public void addSeat(int line){
        int seatNumber = lineLength(line);
        if(seatNumber > 0){
            Seat s = new Seat(line, seatNumber+1);
            s.setSector(this);
            seats.add(s);
        }else{
            throw new IllegalArgumentException("line from sector addSeat: line does not exist");
        }
    }

    public boolean removeSeat(int line){
        int seatNumber = lineLength(line);
        if(seatNumber > 1) {
            ListIterator<Seat> lit = seats.listIterator();
            Seat s = null;
            while (lit.hasNext()) {
                s = lit.next();
                if (s.getLineNumber() == line) {
                    while (s.isBooked() && lit.hasNext() && s.getLineNumber() == line) {
                        s = lit.next();
                    }
                    if (!lit.hasNext() || s.getLineNumber() != line)
                        return false;
                    else {
                        s.setSector(null);
                        lit.remove();
                        while (lit.hasNext() && s.getLineNumber() == line) {
                            s = lit.next();
                            s.setSeatNumber(s.getSeatNumber()-1);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isAvailable(){
        for (Seat seat : seats) {
            if(seat.isAvailable())
                return true;
        }
        return false;
    }

    public void setAvailability(boolean availability) {
        for (Seat seat : seats) {
            seat.setAvailability(availability);
        }
    }

    public boolean hasBookings(){
        for (Seat seat : seats) {
            if(seat.isBooked())
                return true;
        }
        return false;
    }

    public int lineLength(int line){
        int lineLength = 0;
        ListIterator<Seat> lit = seats.listIterator();
        while (lit.hasNext()){
            Seat s = lit.next();
            if(s.getLineNumber() == line){
                lineLength++;
            }
            if(s.getLineNumber() > line)
                break;
        }
        return lineLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sector sector = (Sector) o;

        if (Double.compare(sector.priceFactor, priceFactor) != 0) return false;
        if (!name.equals(sector.name)) return false;
        return hall.equals(sector.hall);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(priceFactor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + hall.hashCode();
        return result;
    }
}
