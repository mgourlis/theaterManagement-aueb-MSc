package gr.aueb.mscis.theater.model;

import javax.persistence.*;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 512, nullable = false)
    private String name;

    @Column(name = "roleType", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="play_id", nullable = false)
    private Play play;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="agent_id", nullable = true)
    private Agent agent;

    public Role(){

    }

    public Role(String name, RoleType roleType) {
        this.name = name;
        this.roleType = roleType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }

    public Agent getAgent() {
        return agent;
    }

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
