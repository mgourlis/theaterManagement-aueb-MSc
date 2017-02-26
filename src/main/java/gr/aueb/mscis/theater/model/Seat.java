package gr.aueb.mscis.theater.model;


import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    /**
     * Αφαιρεί τις συνδέσεις της θέσης με τα εισιτήρια έτσι ώστε να αφαιρεθεί.
     */
    @PreRemove
    private void removeAssociationsWithChilds() {
        for (Ticket ticket : tickets) {
            if(ticket.getSeat() != null)
            	ticket.setSeat(null);
        }
    }

    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Seat(){

    }

    /**
     * Κατασκευαστής της κλάσσης Seat, δημιουργεί αντικείμενο τύπου seat
     * @param lineNumber ο αριθμός σειράς της θέσης
     * @param seatNumber ο αριθμός της θέσης.
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
     *Επιστρέφει τον αριθμό της θέσης
     * @return ο αριθμός της θέσης
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Θέτει τον αριθμό της θέσης
     * @param seatNumber ο αριθμός της θέσης
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Επιστρέφει τον αριθμό σειράς της θέσης
     * @return ο αριθμός σειράς
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Θέτει τον αριθμό σειράς της θέσης
     * @param lineNumber ο αριθμός σειράς
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Επιστρέφει τον τομέα της θέσης
     * @return ο τομέας
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * Θέτει τον τομέα της θέσης
     * @param sector ο τομέας
     */
    public void setSector(Sector sector) {
        this.sector = sector;
    }

    /**
     * Ελέγχει αν υπάρχουν μελλοντικά αγορασμένα εισιτήρια για τη θέση
     * @return true/false αν υπάρχουν εισιτήρια ή όχι
     */
    public boolean isBooked(){
        Date currentDate = Calendar.getInstance().getTime();
        for(Ticket ticket : tickets){
            if(ticket.getShow().getDate().after(currentDate) || ticket.getShow().getDate().equals(currentDate))
                if(ticket.isActive()) return true;
        }
        return false;
    }

    /**
     *Επιστρέφει αν υπάρχουν αγορασμένα εισιτήρια για τη θέση σε
     * συγκεκριμένη ημερομηνία
     * @param date η ημερομηνία ελέγχου
     * @return true/false αν υπάρχουν εισητίρια ή όχι
     */
    public boolean isBooked(Date date){
        for(Ticket ticket : tickets){
            if(ticket.getShow().getDate().equals(date))
                return true;
        }
        return false;
    }

    /**
     * Επιστρέφει το σύνολο των εισιτηρίων για τη θέση
     * @return το σύνολο των εισιτηρίων
     */
    public Set<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Εισάγει ένα εισιτήριο στην θέση.
     * @param ticket το εισιτήριο
     */
    public void addTicket(Ticket ticket){
        ticket.getSeat().removeTicket(ticket);
        ticket.setSeat(this);
        tickets.add(ticket);
    }

    /**
     * Αφαιρεί ένα εισητίριο από την θέση
     * @param ticket το εισητίριο
     * @return true/false αν διαγράφηκε το εισητίριο ή όχι
     */
    public boolean removeTicket(Ticket ticket){
        boolean delete = tickets.remove(ticket);
        if(delete)
            ticket.setSeat(null);
        return delete;
    }

    /**
     *Επιστρέφει την διαθεσιμότητα της θέσης.
     * @return true/false αν είναι διαθέσιμη ή όχι
     */
    public boolean isAvailable(){
        return availability;
    }

    /**
     *Θέτει την διαθεσιμότητα της θέσης
     * @param availability η διαθεσιμότητα true/false
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
