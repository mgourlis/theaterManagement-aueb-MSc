package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.*;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.PurchaseService;
import gr.aueb.mscis.theater.service.SerialNumberProviderImpl;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NewPurchaseInfo {

    @XmlElement(name="show")
    private Integer showId;
    @XmlElement(name="user")
    private Integer userId;
    @XmlElementWrapper(name = "seatIdswrapper")
    @XmlElement(name = "seats")
    private List<Integer> seatIds;

    public NewPurchaseInfo() {

    }
    
    public NewPurchaseInfo(Integer showId, Integer userId, List<Integer> seatIds) {
        this.showId = showId;
        this.userId = userId;
        this.seatIds = seatIds;
    } 
    
    public Integer getShowId() {
        return showId;
    }
	
    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
    }

    public List<Seat> getSeats(EntityManager em) {

        List<Seat> seatList = new ArrayList<Seat>();
        Seat seat = new Seat();
    
        for (Integer sId : seatIds) {
            seat = em.getReference(Seat.class, sId);
            seatList.add(seat);
        }

        return seatList;
    }

    public Purchase createNewPurchase(EntityManager em) {

        FlashMessageService flashserv = new FlashMessageServiceImpl();
        PurchaseService purchaseService = new PurchaseService(flashserv);
    	PurchaseInfo purchaseInfo = null;
        Purchase purchase;

        List<Seat> seats = getSeats(em);
        Show show = em.getReference(Show.class, showId);
        User user = em.getReference(User.class, userId);

        purchase = new Purchase(new Date(), "internet", seats.size());
        purchase.setUser(user);
        
        for (Seat s : seats) {
            purchase.setTicket(new Ticket(show, s, new SerialNumberProviderImpl()));
        }

        purchase = purchaseService.save(purchase);
        
        return purchase;
    }
}