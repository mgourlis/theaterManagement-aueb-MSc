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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sector_id", nullable = false)
    private Sector sector;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST ,
            CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH}, mappedBy = "seat")
    private Set<Ticket> tickets = new HashSet<Ticket>();

    @PreRemove
    private void removeAssociationsWithChilds() {
        for (Ticket ticket : tickets) {
            ticket.setSeat(null);
        }
    }

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
        this.availability = true;
    }

    public int getId() {
        return id;
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

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public boolean isBooked(){
        Date currentDate = Calendar.getInstance().getTime();
        for(Ticket ticket : tickets){
            if(ticket.getShow().getDate().after(currentDate) || ticket.getShow().getDate().equals(currentDate))
                if(ticket.isActive()) return true;
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

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket){
        ticket.getSeat().removeTicket(ticket);
        ticket.setSeat(this);
        tickets.add(ticket);
    }

    public boolean removeTicket(Ticket ticket){
        boolean delete = tickets.remove(ticket);
        if(delete)
            ticket.setSeat(null);
        return delete;
    }

    /**
     *
     * @return boolean
     */
    public boolean isAvailable(){
        return availability;
    }

    /**
     *
     * @param availability
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (lineNumber != seat.lineNumber) return false;
        if (seatNumber != seat.seatNumber) return false;
        return sector.equals(seat.sector);
    }

    @Override
    public int hashCode() {
        int result = lineNumber;
        result = 31 * result + seatNumber;
        result = 31 * result + sector.hashCode();
        return result;
    }


}
