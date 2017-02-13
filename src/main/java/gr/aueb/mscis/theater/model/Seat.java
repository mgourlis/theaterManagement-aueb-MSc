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

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    @Column(name = "line", nullable = false)
    private int line;

    @Column(name = "sector", length = 255, nullable = false)
    private int line;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seat")
    private Set<Availability> availabilities = new HashSet<Availability>();

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
    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
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

    /**
     *
     * @param seatNumber
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     *
     * @param date
     */
    public void makeUnavailable(Date date){
        if(isAvailable(date))
            this.availabilities.add(new Availability(date));
    }

    /**
     *
     * @param date
     */
    public void makeAvailable(Date date){
        if(!isAvailable(date))
            this.availabilities.remove(new Availability(date));
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
     * @param date
     * @return boolean
     */
    public boolean isAvailable(Date date){
        Availability a = new Availability(date);
        return availabilities.contains(a);
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return boo
     */
    public boolean isAvailable(Date startDate, Date endDate){
        if(endDate.before(startDate)) throw new IllegalArgumentException("startdate is after enddate");

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        for (Date date = start.getTime(); start.compareTo(end) > 0; start.add(Calendar.DATE, 1), date = start.getTime()) {
            if(!isAvailable(date)) return false;
        }

        return true;
    }
}
