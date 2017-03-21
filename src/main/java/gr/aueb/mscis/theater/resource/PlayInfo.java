package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Role;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Myron on 9/2/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayInfo {

    @XmlElement(name = "playid")
    private Integer id;
    @XmlElement(name = "playtitle")
    private String title;
    @XmlElement(name = "playdescription")
    private String description;
    @XmlElement(name = "playroles")
    private List<RoleInfo> roles;

    public PlayInfo() {

    }

    public PlayInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public PlayInfo(Play play, Boolean alldata) {
        this.id = play.getId();
        this.title = play.getTitle();
        this.description = play.getDescription();
        if(alldata)
            this.roles = RoleInfo.wrap(play.getRoles());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RoleInfo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleInfo> roles) {
        this.roles = roles;
    }

    public static PlayInfo wrap(Play b, Boolean alldata) {
        return new PlayInfo(b,alldata);
    }

    public static List<PlayInfo> wrap(List<Play> plays, Boolean alldata) {

        List<PlayInfo> playInfoList = new ArrayList<>();

        for (Play b : plays) {
            playInfoList.add(new PlayInfo(b,alldata));
        }

        return playInfoList;

    }

    public Play getPlay(EntityManager em) {

        Play play = null;

        if (id != null) {
            play = em.find(Play.class, id);
        } else {
            play = new Play();
        }

        play.setTitle(title);
        play.setDescription(description);
        Set<Role> inforoles = new HashSet<Role>();
        for(RoleInfo r : roles){
            Role role = r.getRole(em);
            role.setPlay(play);
            inforoles.add(role);
        }
        play.getRoles().clear();
        play.getRoles().addAll(inforoles);


        return play;
    }
}

