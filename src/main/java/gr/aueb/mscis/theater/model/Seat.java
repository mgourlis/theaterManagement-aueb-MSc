package gr.aueb.mscis.theater.model;


import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "lineNumber", nullable = false)
    private int lineNumber;

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    @Column(name = "availability", nullable = false)
    private boolean availability;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "seat")
    private Set<Ticket> tickets = new HashSet<Ticket>();

    /**
     *
     */
    public Seat(){

    }

    /**
     *
     * @param seatNumber
     */
    public Seat(int lineNumber, int seatNumber) {
        this.lineNumber = lineNumber;
        this.seatNumber = seatNumber;
        this.availability =true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return int seatNumber
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public boolean isBooked(){
        Date currentDate = Calendar.getInstance().getTime();
        for(Ticket ticket : tickets){
            if(ticket.getShow().getDate().after(currentDate) || ticket.getShow().getDate().equals(currentDate))
                return true;
        }
        return false;
    }

    /**
     *
     * @param date
     * @return
     */
    public boolean isBooked(Date date){
        for(Ticket ticket : tickets){
            if(ticket.getShow().getDate().equals(date))
                return true;
        }
        return false;
    }



    /**
     *
     * @return boolean
     */
    public boolean isAvailable(){
        return availability;
    }

}
