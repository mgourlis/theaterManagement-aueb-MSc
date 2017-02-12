package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "availabilities")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="seat_id", nullable = false)
    private Seat seat;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "play")
    private Set<Role> roles = new HashSet<Role>();

    /**
     *
     */
    public Availability(){

    }

    /**
     *
     * @param date
     */
    public Availability(Date date){
        this.date = date;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Availability that = (Availability) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return seat != null ? seat.equals(that.seat) : that.seat == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        return result;
    }
}
