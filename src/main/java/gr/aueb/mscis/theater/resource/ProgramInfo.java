package gr.aueb.mscis.theater.resource;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class ProgramInfo {

    private Date startdate;
    private Date enddate;
    private int playId;
    private int hallid;
    private double price;

    public ProgramInfo() {

    }

    public ProgramInfo(Date startdate, Date enddate, int playId, int hallid, double price) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.playId = playId;
        this.hallid = hallid;
        this.price = price;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }

    public int getHallid() {
        return hallid;
    }

    public void setHallid(int hallid) {
        this.hallid = hallid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
