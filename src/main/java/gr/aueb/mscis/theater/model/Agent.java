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
    private int id;

    @Column(name = "name", length = 512, nullable = false)
    private String name;

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
     * @param name το όνομα του Συντελεστή
     * @param yearOfBirth η ημερομηνία γέννησης του συντελεστή
     * @param cv το βιογραφικό του συντελεστή (μέχρι 5000 χαρακτήρες)
     */
    public Agent(String name, int yearOfBirth, String cv) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.cv = cv;
    }

    /**
     * Accessor method
     * @return επιστρέφει το id του αντικειμένου
     */
    public int getId() {
        return id;
    }

    /**
     * Transformer Method
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Accessor method
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Transformer Method
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
        return name.equals(agent.name);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + yearOfBirth;
        return result;
    }
}
