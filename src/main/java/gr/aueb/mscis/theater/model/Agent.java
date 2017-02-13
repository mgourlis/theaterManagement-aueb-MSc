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
        this.roles.add(role);
    }

    public boolean removeRole(Role role){
        return this.roles.remove(role);
    }
}
