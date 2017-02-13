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

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="play_id", nullable = false)
    private Play play;

    @ManyToOne(optional = true, fetch=FetchType.LAZY)
    @JoinColumn(name="agent_id", nullable = true)
    private Agent agent;

    public Role(String name, RoleType roleType) {
        this.name = name;
        this.roleType = roleType;
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
}
