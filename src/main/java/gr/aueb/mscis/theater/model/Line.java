package gr.aueb.mscis.theater.model;

import javax.persistence.*;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "lines")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "lineNumber", nullable = false)
    private int lineNumber;

    public Line(){

    }

    public Line(int lineNumber) {
        super();
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }


}
