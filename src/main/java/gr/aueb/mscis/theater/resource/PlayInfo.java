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
    private Integer year;
    private String writer;

    public PlayInfo() {

    }

    public PlayInfo(String title, int year, String writer) {
        super();
        this.title = title;
        this.year = year;
        this.writer = writer;
    }

    public PlayInfo(Play play) {
        this.id = play.getId();
        this.title = play.getTitle();
        this.year = play.getYear();
        this.writer = play.getWriter();
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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

    public Play getMovie(EntityManager em) {

        Play play = null;

        if (id != null) {
            play = em.find(Play.class, id);
        } else {
            play = new Play();
        }

        play.setTitle(title);
        play.setYear(year);
        play.setWriter(writer);


        return play;
    }
}

