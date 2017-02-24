package gr.aueb.mscis.theater.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 512, nullable = false)
    private String name;

    @Column(name = "roleType", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="play_id", nullable = false)
    private Play play;

    @ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.MERGE, CascadeType.DETACH} )
    @JoinColumn(name="agent_id", nullable = true)
    private Agent agent;

    @PreRemove
    private void removeAssociationWithParent() {
       if(agent != null)agent.removeRole(this);
    }

    /**
     *Προκαθορισμένος κατασκευαστής.
     */
    public Role(){

    }

    /**
     * Κατασκευαστής της κλάσσης Role, δημιουργεί αντικείμενο τύπου role
     * @param name το όνομα του ρόλου
     * @param roleType ο τύπος του ρόλου
     */
    public Role(String name, RoleType roleType) {
        this.name = name;
        this.roleType = roleType;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Επιστρέφει το όνομα του ρόλου
     * @return το όνομα
     */
    public String getName() {
        return name;
    }

    /**
     * Θέτει το όνομα του ρόλου
     * @param name το όνομα
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Επιστρέφει τον τύπο του ρόλου
     * @return ο τύπος
     */
    public RoleType getRoleType() {
        return roleType;
    }

    /**
     * Θέτει τον τύπο του ρόλου
     * @param roleType ο τύπος
     */
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    /**
     * Επιστρέφει το θεατρικό έργο του ρόλου
     * @return το θεατρικό έργο
     */
    public Play getPlay() {
        return play;
    }

    /**
     * Θέτει το θεατρικό έργο του ρόλου
     * @param play το θεατρικό έργο
     */
    public void setPlay(Play play) {
        this.play = play;
    }

    /**
     * Επιστρέφει τον Συντελεστη του ρόλου
     * @return ο συντελεστής
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * Θέτει τον συντελεστή του ρόλου
     * @param agent ο συντελεστής
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!name.equals(role.name)) return false;
        if (roleType != role.roleType) return false;
        return play != null ? play.equals(role.play) : role.play == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + roleType.hashCode();
        result = 31 * result + (play != null ? play.hashCode() : 0);
        return result;
    }
}
