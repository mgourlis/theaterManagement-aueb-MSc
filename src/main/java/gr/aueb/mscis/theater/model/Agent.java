package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Entity
@Table(name = "agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="firstName", length=50, nullable = false)
    private String firstName;
 
    @Column(name="lastName", length=50, nullable = false)
    private String lastName;
    
    @Column(name = "yearOfBirth", nullable = false)
    private int yearOfBirth;

    @Column(name = "cv", length = 5000, nullable = true)
    private String cv;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH}, mappedBy = "agent")
    private Set<Role> roles = new HashSet<Role>();

    /**
     * Μέθοδος για αποσύνδεση με την συσχετιζόμενη οντότητα ρολων
     */
    @PreRemove
    private void removeAssociationsWithChilds() {
        for (Role role : roles) {
            role.setAgent(null);
        }
    }

    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Agent(){

    }

    /**
     * Κατασκευαστής της κλάσσης Agent, δημιουργεί αντικείμενο τύπου Agent
     * @param firstName Το όνομα συντελεστή
     * @param lastName Το επώνυμο του συντελεστή
     * @param yearOfBirth η ημερομηνία γέννησης του συντελεστή
     * @param cv το βιογραφικό του συντελεστή (μέχρι 5000 χαρακτήρες)
     */
    public Agent(String firstName,
		    	 String lastName,
		    	 int yearOfBirth,
		    	 String cv) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.cv = cv;
    }

    /**
     * Επιστρέφει το Id του αντικειμένου
     * @return το Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Επιστρέφει το όνομα του συντελεστή.
     * @return Το όνομα του συντελεστή.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Θέτει το όνομα του συντελεστή.
     * @param firstName Το όνομα του συντελεστή.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Επιστρέφει το επώνυμο του συντελεστή.
     * @return Το επώνυμο του συντελεστή.
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Θέτει το επώνυμο του συντελεστή.
     * @param lastName Το επώνυμο του συντελεστή.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Επιστρέφει την ημερομηνία γέννησης του Συντελεστή
     * @return Date η ημερωμηνία γέννησης
     */
    public int getYearOfBirth() {
        return yearOfBirth;
    }

    /**
     * Θέτει την ημερομηνία γέννησης του Συντελεστή
     * @param yearOfBirth η ημερομηνία γέννησης
     */
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * Επιστρέφει το βιογραφικό του Συντελεστή
     * @return το βιογραφικό
     */
    public String getCv() {
        return cv;
    }

    /**
     * Θέτει το βιογραφικό τπυ Συντελεστή
     * @param cv το βιογραφικό
     */
    public void setCv(String cv) {
        this.cv = cv;
    }

    /**
     * Επιστρέφει σύνολο με τους ρόλους του Συντελεστή σε θεατρικά έργα
     * @return Set με αντικείμενα Role
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     *Εισάγει έναν ρόλο στους ρόλους του Συντελεστή χρεισιμοποιόντας την μέθοδο add
     * του HashSet και ενώνει τον ρόλο με τον Συντελεστή.
     * @param role ο ρόλος
     */
    public void addRole(Role role) {
        role.setAgent(this);
        this.roles.add(role);
    }

    /**
     *Αφαιρεί ένα ρόλο από τους ρόλους του Συντελεστή χρεισιμοποιόντας την μέθοδο remove
     * του HashSet και αποσυνδέει τον ρόλο με τον Συντελεστή.
     * @param role ο ρόλος που α αφαιρεθεί
     * @return
     */
    public boolean removeRole(Role role){
        Boolean delete = this.roles.remove(role);
        if(delete) {
            Iterator<Role> it = role.getPlay().getRoles().iterator();
            while (it.hasNext()) {
                Role r = it.next();
                if (r.equals(role))
                    r.setAgent(null);
            }
        }
        return delete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agent agent = (Agent) o;

        if (yearOfBirth != agent.yearOfBirth) return false;
        if (!lastName.equals(agent.lastName)) return false;
        return firstName.equals(agent.firstName);
    }

    @Override
    public int hashCode() {
        int result = lastName.hashCode(); //firstName?
        result = 31 * result + yearOfBirth;
        result = 31 * result + firstName.hashCode();
        return result;
    }
}
