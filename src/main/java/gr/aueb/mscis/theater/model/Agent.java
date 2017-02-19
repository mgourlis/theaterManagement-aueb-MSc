package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by Myron on 11/2/2017.
 */
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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH}, mappedBy = "agent")
    private Set<Role> roles = new HashSet<Role>();

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
        this.firstΝame = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.cv = cv;
    }

    /**
     * Accessor method
     * @return επιστρέφει το id του αντικειμένου
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
     * @param lastName Το όνομα του συντελεστή.
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
     * Accessor method
     * @return
     */
    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * Accessor method
     * @return
     */
    public String getCv() {
        return cv;
    }

    /**
     * Transformer Method
     * @param cv
     */
    public void setCv(String cv) {
        this.cv = cv;
    }

    /**
     * Accessor method
     *
     * @return Set με αντικείμενα Role
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     *
     * @param role
     */
    public void addRole(Role role) {
        role.setAgent(this);
        this.roles.add(role);
    }

    /**
     *
     * @param role
     * @return
     */
    public boolean removeRole(Role role){
        role.setAgent(null);
        return this.roles.remove(role);
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agent agent = (Agent) o;

        if (yearOfBirth != agent.yearOfBirth) return false;
        if (!lastName.equals(agent.lastName)) return false;
        return firstName.equals(agent.firstName);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = lastName.hashCode(); //firstName?
        result = 31 * result + yearOfBirth;
        return result;
    }
}
