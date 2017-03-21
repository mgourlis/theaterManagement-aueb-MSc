package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Agent;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by myrgo on 20/3/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AgentInfo {

    @XmlElement(name = "agent")
    private Integer id;
    @XmlElement(name = "agentfirstName")
    private String firstName;
    @XmlElement(name = "agentlastName")
    private String lastName;
    @XmlElement(name = "agentyearOfBirth")
    private int yearOfBirth;
    @XmlElement(name = "agentcv")
    private String cv;

    public AgentInfo(){

    }

    public AgentInfo(Agent agent){
        this.id = agent.getId();
        this.firstName = agent.getFirstName();
        this.lastName = agent.getLastName();
        this.yearOfBirth = agent.getYearOfBirth();
        this.cv = agent.getCv();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public static AgentInfo wrap(Agent a) {
        return new AgentInfo(a);
    }

    public static List<AgentInfo> wrap(List<Agent> agents) {

        List<AgentInfo> agentInfoList = new ArrayList<>();

        for (Agent b : agents) {
            agentInfoList.add(new AgentInfo(b));
        }

        return agentInfoList;

    }

    public Agent getAgent(EntityManager em) {

        Agent agent = null;

        if (id != null) {
            agent = em.find(Agent.class, id);
        } else {
            agent = new Agent();
        }

        agent.setFirstName(firstName);
        agent.setLastName(lastName);
        agent.setYearOfBirth(yearOfBirth);
        agent.setCv(cv);

        return agent;
    }
}
