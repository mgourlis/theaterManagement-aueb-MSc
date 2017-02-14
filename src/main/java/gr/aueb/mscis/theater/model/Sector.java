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

    public void addLine() {
        this.seats.add(new Seat(seats.size()+1,1));
    }

    public boolean removeLine(){
        if(seats.isEmpty())
            return false;
        int size = seats.size();
        ListIterator<Seat> lit = seats.listIterator(size);
        Seat s = seats.get(size - 1);
        int lineNumber = s.getLineNumber();
        boolean removeFlag = true;
        while (lit.hasPrevious()){
            s = lit.previous();
            if(s.isBooked())
                removeFlag = false;
            if(s.getLineNumber() != lineNumber && removeFlag)
                break;
            if(s.getLineNumber() != lineNumber && !removeFlag){
                lineNumber = s.getLineNumber();
                removeFlag = true;
            }
        }
        while (lit.hasNext() && removeFlag){
            s = lit.next();
            if(s.getLineNumber() == lineNumber)
                lit.remove();
            else
                s.setLineNumber(s.getLineNumber()-1);
        }
        return removeFlag;
    }

    public void addSeat(int line){
        int seatNumber = lineLength(line);
        if(seatNumber != 0){
            seats.add(new Seat(line, seatNumber+1));
        }else{
            throw new IllegalArgumentException("line from sector addSeat");
        }
    }

    public boolean removeSeat(int line){
        int seatNumber = lineLength(line);
        if(seatNumber != 0){

        }else{
            throw new IllegalArgumentException("line from sector addSeat");
        }
    }

    public boolean isAvailable(){
        boolean av = false;
        for (Seat seat : seats) {
            if(seat.isAvailable())
                return true;
        }
        return false;
    }

    private int lineLength(int line){
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

}
