package gr.aueb.mscis.theater.model;

import javax.persistence.*;

/**
 * Created by Myron on 11/2/2017.
 */
@Entity
@Table(name = "sectors")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 512, nullable = false)
    private String name;

    @Column(name = "priceFactor", nullable = false)
    private int priceFactor;

    public Sector(){

    }

    public Sector(String name){
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceFactor() {
        return priceFactor;
    }

    public void setPriceFactor(int priceFactor) {
        this.priceFactor = priceFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sector sector = (Sector) o;

        if (priceFactor != sector.priceFactor) return false;
        return name != null ? name.equals(sector.name) : sector.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + priceFactor;
        return result;
    }
}
