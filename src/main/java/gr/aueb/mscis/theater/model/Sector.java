package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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
    @OrderBy( value = "lineNumber ASC, seatNumber ASC")
    private List<Seat> seats = new ArrayList<Seat>();

    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Sector(){

    }

    /**
     * Κατασκευαστής της κλάσσης Sector, δημιουργεί αντικείμενο τύπου Sector
     * @param name το όνομα του τομέα
     */
    public Sector(String name, double priceFactor){
        this.name = name;
        this.priceFactor = priceFactor;
    }

    public int getId() {
        return id;
    }

    /**
     * Επιστρέφει το όνομα του Τομέα
     * @return το όνομα
     */
    public String getName() {
        return name;
    }

    /**
     * Θέτει το όνομα του Τομέα
     * @param name το όνομα
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Επιστρέφει το συντελεστή τιμής του τομέα
     * @return ο συντελεστής τιμής
     */
    public double getPriceFactor() {
        return priceFactor;
    }

    /**
     * Θέτει τον συντελεστή τιμής του τομέα
     * @param priceFactor ο συντελεστής τιμής
     */
    public void setPriceFactor(double priceFactor) {
        this.priceFactor = priceFactor;
    }

    /**
     * Επιστρέφει την αίθουσα του τομέα
     * @return η αίθουσα
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * Θέτει την αίθουσα του τομέα
     * @param hall η αίθουσα
     */
    public void setHall(Hall hall) {
        this.hall = hall;
    }

    /**
     * Επιστρέφει όλες τις θέσεις του τομέα
     * @return οι θέσεις του τομέα
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Επιστρέφει τη θέση που ανήκει σε συγκεκριμένη σειρά και αριθμό
     * @param lineNumber η σειρά που ανήκει η θέση
     * @param seatNumber ο αριθμός της θέσης
     * @return η συγκεριμένη θέση
     */
    public Seat getSeat(int lineNumber, int seatNumber) throws IllegalArgumentException{
        for(Seat seat : seats){
            if(seat.getLineNumber() == lineNumber && seat.getSeatNumber() == seatNumber)
                return seat;
        }
        throw new IllegalArgumentException("seat not found");
    }

    /**
     * Εισάγει μια θέση στον τομέα σε νέα σειρά (υπάρχουσες σειρές +1 )
     */
    public void addLine() {
        Seat seat = new Seat(seats.isEmpty() ? 1 : seats.get(seats.size()-1).getLineNumber()+1,1);
        seat.setSector(this);
        this.seats.add(seat);
    }

    /**
     * Αφαιρεί τις θέσεις μιας σειράς του τομέα, στην περίπτωση που δεν υπάρχουν
     * εισιτήρια για τις θέσεις της, και επαναυπολογίζει τους αριθμούς σειρών
     * των θέσεων του τομέα
     * @param line ο αριθμός σειράς
     * @return true/false αν έγινε η αφαίρεση ή όχι
     */
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

    /**
     * Εισάγει μια θέση στο τέλος της σειράς
     * @param line ο αριθμός σειράς
     */
    public void addSeat(int line) throws IllegalArgumentException {
        int numOfSeats = lineLength(line);
        if(numOfSeats > 0){
            Seat s = new Seat(line, numOfSeats+1);
            s.setSector(this);
            seats.add(s);
        }else{
            throw new IllegalArgumentException("line from sector addSeat: line does not exist");
        }
    }

    /**
     * Αφαιρεί την πρώτη θέση της σειράς που δεν έχει μελλοντικά εισιτήρια
     * και επαναυπολογίζει τους αριθμούς θέσεων.
     * @param line ο αριθμός σειράς
     * @return true/false αν αφαιρέθηκε θέση ή όχι
     */
    public boolean removeSeat(int line){
        int numOfSeats = lineLength(line);
        if(numOfSeats > 1) {
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

    /**
     * Επιστρέφει τις πρώτες numberOfSeats συνεχόμενες ελεύθερες θέσεις του τομέα
     * για μια συγκεκριμένη ημερομηνία
     * @param numberOfSeats ο αριθμός των θέσεων
     * @param date η ημερομηνία
     * @return λίστα με τις συνεχόμενες ελεύθερες θέσεις
     * @throws IllegalArgumentException αν δεν υπάρχουν numberOfSeats συνεχόμενες ελεύθερες θέσεις
     */
    public List<Seat> getFreeSeats(int numberOfSeats, Date date) throws IllegalArgumentException{
        List<Seat> freeSeats = new ArrayList<Seat>();
        ListIterator<Seat> lit = seats.listIterator();
        int freeNum = 0;
        while (lit.hasNext()){
            Seat s = lit.next();
            int line = s.getLineNumber();
            while (!s.isBooked(date) && s.isAvailable() && line == s.getLineNumber() && lit.hasNext()) {
                freeSeats.add(s);
                freeNum++;
                if(freeNum == numberOfSeats) return  freeSeats;
                s = lit.next();
            }
            freeNum = 0;
            freeSeats.removeAll(freeSeats);
        }
        throw new IllegalArgumentException("No free seats found in sequence");
    }

    /**
     * Επιστρέφει λίστα με διαθέσιμες θέσεις σε όλο τον τομέα
     * για συγκεκριμένη ημερομηνία
     * @param date η ημερομηνία
     * @return λίστα με διαθέσιμες θέσεις
     */
    public List<Seat> getFreeSeats(Date date){
        List<Seat> freeSeats = new ArrayList<Seat>();
        ListIterator<Seat> lit = seats.listIterator();
        while (lit.hasNext()){
            Seat s = lit.next();
            if(!s.isBooked(date) && s.isAvailable()) {
                freeSeats.add(s);
            }
        }
        return freeSeats;
    }

    /**
     * Επιστρέφει την διαθεσιμότητα του τομέα
     * @return true/false αν είναι διαθέσιμος ή όχι
     */
    public boolean isAvailable(){
        for (Seat seat : seats) {
            if(seat.isAvailable())
                return true;
        }
        return false;
    }

    /**
     * Θέτει τη διαθεσιμότητα του τομέα, δηλαδή θέτει ότι όλες οι θέσεις του τομέα είναι διαθέσιμες ή όχι
     * @param availability true/false
     */
    public void setAvailability(boolean availability) {
        for (Seat seat : seats) {
            seat.setAvailability(availability);
        }
    }

    /**
     * Επιστρέφει αν ο τομέας έχει μελλοντικές κρατήσεις στις θέσεις του
     * @return true/flase αν έχει κρτατήσεις ή όχι
     */
    public boolean hasBookings(){
        for (Seat seat : seats) {
            if(seat.isBooked())
                return true;
        }
        return false;
    }

    /**
     * Επιστρέφει τον αριθμό θέσεων μιας σειράς
     * @param line ο αριθμός σειράς,
     * @return 0 αν η σειρά δεν υπάρχει αλλιώς τον αριθμό θέσεων
     */
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
