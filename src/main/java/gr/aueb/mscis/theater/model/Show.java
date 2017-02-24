package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "canceled", nullable = false)
    private boolean canceled;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="play_id", nullable = false)
    private Play play;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="hall_id", nullable = false)
    private Hall hall;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "show")
    private Set<Ticket> tickets = new HashSet<Ticket>();

    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Show(){

    }

    /**
     * Κατασκευαστής της κλάσσης Show, δημιουργεί αντικείμενο τύπου Show
     * @param date η ημερομηνία της παράστασης
     * @param price η τιμή της παράστασης
     * @param play το θεατρικό έργο
     * @param hall η αίθουσα
     */
    public Show(Date date, double price, Play play, Hall hall) {
        this.date = date;
        this.price = price;
        this.play = play;
        this.hall = hall;
        this.canceled = false;
        this.hall.getShows().add(this);
        this.play.getShows().add(this);
    }

    public int getId() {
        return id;
    }

    /**
     * Επιστρέφει την ημερομηνία της παράστασης
     * @return η ημερομηνία
     */
    public Date getDate() {
        return date;
    }

    /**
     * Θέτει την ημερομηνία της παράστασης
     * @param date η ημερομηνία
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Επιστρέφει την τιμή της παράστασης
     * @return η τιμή
     */
    public double getPrice() {
        return price;
    }

    /**
     * Θέτει την τιμή της παράστασης
     * @param price η τιμή
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Επιστρέφει το θεατρικό έργο της παράστασης
     * @return το θεατρικό έργο
     */
    public Play getPlay() {
        return play;
    }

    /**
     * Θέτει το θεατρικό έργο της παράστασης
     * @param play το θεατρικό έργο
     */
    public void setPlay(Play play) {
        if(!play.equals(this.play) && play != null){
            Iterator<Show> it = this.play.getShows().iterator();
            while (it.hasNext()){
                Show sh = it.next();
                if(sh.getPlay().equals(this.play) && sh.getDate().equals(this.date)){
                    it.remove();
                    this.play = play;
                    play.getShows().add(this);
                    break;
                }
            }
        } else {
            this.play = play;
        }
    }

    /**
     * Επιστρέφει την αίθουσα της παράστασης
     * @return η αίθουσα
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * Θέτει την αίθουσα της παράστασης.
     * @param hall η αίθουσα
     */
    public void setHall(Hall hall) {
        if(!hall.equals(this.hall) && hall != null){
            Iterator<Show> it = this.hall.getShows().iterator();
            while (it.hasNext()){
                Show sh = it.next();
                if(sh.getHall().equals(this.hall) && sh.getDate().equals(this.date)){
                    it.remove();
                    this.hall = hall;
                    hall.getShows().add(this);
                    break;
                }
            }
        }else {
            this.hall = hall;
        }
    }

    /**
     * Ελέγχει αν η παράσταση έχει ακυρωθεί.
     * @return true/false αν έχει ακυρωθεί ή όχι
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * Ακυρώνει την παράσταση
     */
    public void setCanceled() {
        this.canceled = false;
    }

    /**
     * Επιστρέφει το σύνολο των εισητιρίων για τη παράσταση,
     * @return το σύνολο των εισητιρίων
     */
    public Set<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Εισάγει ένα εισητίριο στη παράσταση
     * @param ticket το εισητίριο
     */
    public void addTicket(Ticket ticket){
        ticket.setShow(this);
        this.tickets.add(ticket);
    }

    /**
     * Αφαιρεί ένα εισητίριο από την παράσταση
     * @param ticket το εισητίριο
     * @return truw/false αν αφαιρέθηκε ή όχι
     */
    public boolean removeTicket(Ticket ticket){
        ticket.setShow(null);
        return this.tickets.remove(ticket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (canceled != show.canceled) return false;
        if (!date.equals(show.date)) return false;
        if (!play.equals(show.play)) return false;
        return hall.equals(show.hall);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = date.hashCode();
        result = 31 * result + (canceled ? 1 : 0);
        result = 31 * result + play.hashCode();
        result = 31 * result + hall.hashCode();
        return result;
    }
}
