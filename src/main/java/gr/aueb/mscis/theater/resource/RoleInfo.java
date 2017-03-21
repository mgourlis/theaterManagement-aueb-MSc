package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Role;
import gr.aueb.mscis.theater.model.RoleType;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by myrgo on 20/3/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleInfo {

    @XmlElement(name = "roleid")
    private Integer id;
    @XmlElement(name = "rolename")
    private String name;
    @XmlElement(name = "roleType")
    private RoleType roleType;
    @XmlElement(name = "roleagent")
    private AgentInfo agent;

    public RoleInfo(){

    }

    public RoleInfo(Role role){
        this.id = role.getId();
        this.name = role.getName();
        this.roleType = role.getRoleType();
        if(role.getAgent() != null){
            agent = AgentInfo.wrap(role.getAgent());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public AgentInfo getAgent() {
        return agent;
    }

    public void setAgent(AgentInfo agent) {
        this.agent = agent;
    }

    public static RoleInfo wrap(Role b) {
        return new RoleInfo(b);
    }

    public static List<RoleInfo> wrap(Set<Role> roles) {

        List<RoleInfo> roleInfoList = new ArrayList<>();

        for (Role b : roles) {
            roleInfoList.add(new RoleInfo(b));
        }

        return roleInfoList;

    }

    public Role getRole(EntityManager em) {

        Role role = null;

        if (id != null) {
            role = em.find(Role.class, id);
        } else {
            role = new Role();
        }

        role.setName(name);
        role.setRoleType(roleType);
        if(agent != null)
            role.setAgent(agent.getAgent(em));

        return role;
    }
}
