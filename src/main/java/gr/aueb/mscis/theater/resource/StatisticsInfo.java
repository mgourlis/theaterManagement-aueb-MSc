package gr.aueb.mscis.theater.resource;


import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.theater.model.Show;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticsInfo {

    @XmlElement(name="income")
    private double income;
    @XmlElement(name="completeness")
    private double completeness;
    
    public StatisticsInfo() {

    }
    public StatisticsInfo(double income, double completeness) {
        this.income = income;
        this.completeness = completeness;
    }
    
    public void getTheaterIncome(Date startDate, Date endDate, List<Show> shows) {

        for (int i=0; i<shows.size(); i++) {
            if ((shows.get(i).getDate().after(startDate) &&
                 shows.get(i).getDate().before(endDate)) ||
                shows.get(i).getDate().equals(startDate)||
                shows.get(i).getDate().equals(endDate)) {

            	income += shows.get(i).totalAmount();
            }
        }
    }

    public void getTheaterCompleteness(Date startDate, Date endDate, List<Show> shows) {

    	double totalNumOfTickets = 0.0;
    	double totalNumOfseats = 0.0;

    	//find all tickets
        for (int i=0; i<shows.size(); i++) {
        	totalNumOfTickets += shows.get(i).getTickets().size();
        }
        
        //find all seats
        totalNumOfseats = (double) (shows.get(0).getHall().getTotalNumOfSeats()) * (double) shows.size();

        completeness = totalNumOfTickets/totalNumOfseats;        
    }
}