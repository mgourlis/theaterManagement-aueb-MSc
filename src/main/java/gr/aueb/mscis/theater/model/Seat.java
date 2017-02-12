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

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="line_id", nullable = false)
    private Line line;

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

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
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
        endDate = startDate.after(endDate) ? startDate : endDate;

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
