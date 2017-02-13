package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Play;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Myron on 9/2/2017.
 */
@XmlRootElement
public class PlayInfo {

    private Integer id;
    private String title;
    private String description;

    public PlayInfo() {

    }

    public PlayInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public PlayInfo(Play play) {
        this.id = play.getId();
        this.title = play.getTitle();
        this.description = play.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public static PlayInfo wrap(Play b) {
        return new PlayInfo(b);
    }

    public static List<PlayInfo> wrap(List<Play> plays) {

        List<PlayInfo> playInfoList = new ArrayList<>();

        for (Play b : plays) {
            playInfoList.add(new PlayInfo(b));
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


        return play;
    }
}

