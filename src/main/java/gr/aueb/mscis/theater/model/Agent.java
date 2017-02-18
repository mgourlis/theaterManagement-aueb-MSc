package gr.aueb.mscis.theater.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/** ============ZK==============
 * Zarodimos Github Myron Push
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

    public Agent(){

    }

    public Agent(String name, int yearOfBirth, String cv) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.cv = cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        role.setAgent(this);
        this.roles.add(role);
    }

    public boolean removeRole(Role role){
        role.setAgent(null);
        return this.roles.remove(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agent agent = (Agent) o;

        if (yearOfBirth != agent.yearOfBirth) return false;
        return name.equals(agent.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + yearOfBirth;
        return result;
    }
}
