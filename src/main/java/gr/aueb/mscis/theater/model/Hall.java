package gr.aueb.mscis.theater.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 512, nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "hall")
    private Set<Sector> sectors = new HashSet<Sector>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "hall")
    private Set<Show> shows = new HashSet<Show>();

    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Hall(){

    }

    /**
     *Κατασκευαστής της κλάσσης Hall, δημιουργεί αντικείμενο τύπου Hall
     * @param name το όνομα της Αίθουσας
     */
    public Hall(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    /**
     *Επιστρέφει το όνομα της Αίθουσας
     * @return το όνομα
     */
    public String getName() {
        return name;
    }

    /**
     *Θέτει το όνομα της Αίθουσας
     * @param name το όνομα
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *Επιστρέφει τον Τομέα της Αίθουσας με όρισμα το όνομά του
     * @param sectorName
     * @return ο αντικείμενο τύπου Τομέα
     * @throws IllegalArgumentException
     */
    public Sector getSectorByName(String sectorName) throws IllegalArgumentException {
        for(Sector sector1 : sectors){
            if(sector1.getName().equals(sectorName)){
                return sector1;
            }
        }
        throw new IllegalArgumentException("No sector found");
    }

    /**
     * Επιστρέφει σύνολο με τους Τομείς της Αίθουσας
     * @return σύνολο Τομέων
     */
    public Set<Sector> getSectors() {
        return sectors;
    }

    /**
     * Επιστρέφει το σύνολο των θέσεων από όλους τους τομείς
     * @return σύνολο θέσεων
     */
    public Integer getTotalNumOfSeats() {
        List<Sector> sectorList = new ArrayList<Sector>(sectors);
        Integer totalNumOfSeats = 0;
        
        for (int i=0; i<sectorList.size(); i++)
        	totalNumOfSeats += sectorList.get(i).getNumOfSeats();

        return totalNumOfSeats;
    }

    
    /**
     * Εισάγει Τομέα στην αίθουσα
     * @param tempSector ο Τομέας
     */
    public void addSector(Sector tempSector){
        tempSector.setHall(this);
        this.sectors.add(tempSector);
    }

    /**
     * Αφαιρεί Τομέα από τους Τομείς της Αίθουσας
     * @param tempSector ο Τομέας
     * @return true/false αν αφαιρέθεικε ή όχι
     */
    public boolean removeSector(Sector tempSector){
        return this.sectors.remove(tempSector);
    }
    
    /**
     * Ελέγχει αν υπάρχει διαθέσιμος τομέας στην αίθουσα
     * @return true/false αν υπάρχει διαθέσιμος τομέας στην αίθουσα ή όχι
     */
    public boolean isAvailable(){
        for (Sector sector : sectors) {
            if(sector.isAvailable())
                return true;
        }
        return false;
    }

    /**
     * Θέτει την διαθεσιμότητα της Αίθουσας
     * @param availability true/false
     */
    public void setAvailability(boolean availability) {
        for (Sector sector: sectors ) {
            sector.setAvailability(availability);
        }
    }

    /**
     * Επιστρέφει τις Παραστάσεις που έχουν συνδεθεί με την Αίθουσα
     * @return σύνολο παραστάσεων
     */
    public Set<Show> getShows() {
        return shows;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;

        return name.equals(hall.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
