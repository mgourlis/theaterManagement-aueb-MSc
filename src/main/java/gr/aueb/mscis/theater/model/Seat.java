package gr.aueb.mscis.theater.model;

import javax.persistence.*;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    public Seat(){

    }

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
