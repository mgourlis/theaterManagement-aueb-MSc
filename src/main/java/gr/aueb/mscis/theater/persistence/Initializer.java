package gr.aueb.mscis.theater.persistence;

import gr.aueb.mscis.theater.model.Sector;
import gr.aueb.mscis.theater.model.Play;
import gr.aueb.mscis.theater.model.Role;
import gr.aueb.mscis.theater.model.Hall;
import gr.aueb.mscis.theater.model.Seat;
import gr.aueb.mscis.theater.model.RoleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class Initializer  {


    /**
     * Remove all data from database.
     * The functionality must be executed within the bounds of a transaction
     */
    public void  eraseData() {
        EntityManager em = JPAUtil.getCurrentEntityManager();
       
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = null;

        query = em.createNativeQuery("delete from plays");
        query.executeUpdate();

        
        query = em.createNativeQuery("ALTER SEQUENCE hibernate_sequence RESTART WITH 1");
        query.executeUpdate();
        
        tx.commit();
        
    }
    

    public void prepareData() {

        eraseData();                      

        Play amlet = new Play("Amlet", "William Shakespeare");
        Play tgm = new Play("The Glass Menagerie", "Tennessee Williams");

        Role role1 = new Role("role1", Actor);
        Role role2 = new Role("role2", Actor);
        Role role3 = new Role("role3", Operator);
        Role role4 = new Role("role4", Operator);
        
        Hall hall1 = new Hall("hall1");
        Hall hall2 = new Hall("hall2");
        
        Sector sector1 = new Sector("sector1", 1.5);
        Sector sector2 = new Sector("sector2", 1.2);
        Sector sector3 = new Sector("sector3", 1);

        Seat seat1 = new Seat(1, 1);
        Seat seat2 = new Seat(1, 2);
        Seat seat3 = new Seat(1, 3);
        
//        Ticket ticket = new Ticket();
        
        EntityManager em = JPAUtil.getCurrentEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        em.persist(amlet);
        em.persist(tgm);
        
        em.persist(hall1);
        em.persist(hall2);
        
        em.persist(sector1);
        em.persist(sector2);
        em.persist(sector3);
        
        em.persist(seat1);
        em.persist(seat2);
        em.persist(seat3);

        em.persist(role1);
        em.persist(role2);
        em.persist(role3);
        em.persist(role4);
        
        tx.commit();
    
    }
}
