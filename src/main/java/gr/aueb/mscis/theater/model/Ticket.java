package gr.aueb.mscis.theater.model;

import javax.persistence.*;

/**
 * Created by Myron on 12/2/2017.
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "serial", length = 255, nullable = false)
    private String serial;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "moneyReturn", nullable = false)
    private boolean moneyReturn;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="show_id", nullable = false)
    private Show show;

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name="seat_id", nullable = false)
    private Seat seat;
}
