package gr.aueb.mscis.theater.persistence;

import gr.aueb.mscis.theater.model.*;
import gr.aueb.mscis.theater.service.SerialNumberProvider;
import gr.aueb.mscis.theater.service.SerialNumberProviderImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Calendar;

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

        query = em.createNativeQuery("delete from tickets");
        query.executeUpdate();
        query = em.createNativeQuery("delete from shows");
        query.executeUpdate();
        query = em.createNativeQuery("delete from roles");
        query.executeUpdate();
        query = em.createNativeQuery("delete from plays");
        query.executeUpdate();
        query = em.createNativeQuery("delete from seats");
        query.executeUpdate();
        query = em.createNativeQuery("delete from sectors");
        query.executeUpdate();
        query = em.createNativeQuery("delete from halls");
        query.executeUpdate();
        query = em.createNativeQuery("delete from agents");
        query.executeUpdate();
        query = em.createNativeQuery("delete from purchases");
        query.executeUpdate();
        query = em.createNativeQuery("delete from users");
        query.executeUpdate();


        //query = em.createNativeQuery("ALTER SEQUENCE hibernate_sequence RESTART WITH 1");
        //query.executeUpdate();

        tx.commit();

    }


    public void prepareData() {

        eraseData();

        Play amlet = new Play("Amlet", "William Shakespeare");
        Play tgm = new Play("The Glass Menagerie", "Tennessee Williams");

        Agent agent1 = new Agent("Name1","Surname1",1973,"");
        Agent agent2 = new Agent("Name2","Surname2",1953,"");

        Role role1 = new Role("role1", RoleType.Actor);
        Role role2 = new Role("role2", RoleType.Actor);
        Role role3 = new Role("role3", RoleType.Operator);
        Role role4 = new Role("role4", RoleType.Operator);

        amlet.addRole(role1);
        amlet.addRole(role3);

        tgm.addRole(role2);
        tgm.addRole(role4);

        agent1.addRole(role1);
        agent1.addRole(role3);

        agent2.addRole(role2);

        role2.setAgent(agent2);

        Hall hall1 = new Hall("hall1");
        Hall hall2 = new Hall("hall2");
        
        Sector hall1sector1 = new Sector("sector1", 1.5);
        Sector hall1sector2 = new Sector("sector2", 1.2);
        Sector hall1sector3 = new Sector("sector3", 1);

        hall1.addSector(hall1sector1);
        hall1.addSector(hall1sector2);
        hall1.addSector(hall1sector3);

        Sector hall2sector1 = new Sector("sector1", 1.5);
        Sector hall2sector2 = new Sector("sector2", 1.2);
        Sector hall2sector3 = new Sector("sector3", 1);

        hall2.addSector(hall2sector1);
        hall2.addSector(hall2sector2);
        hall2.addSector(hall2sector3);


        hall1sector1.addLine();
        hall1sector1.addSeat(1);
        hall1sector1.addSeat(1);
        hall1sector1.addSeat(1);
        hall1sector1.addLine();
        hall1sector1.addSeat(2);
        hall1sector1.addSeat(2);
        hall1sector1.addSeat(2);
        hall1sector1.addSeat(2);
        hall1sector1.addLine();
        hall1sector1.addSeat(3);
        hall1sector1.addSeat(3);
        hall1sector1.addSeat(3);
        hall1sector1.addSeat(3);
        hall1sector1.addLine();
        hall1sector1.addSeat(4);
        hall1sector1.addSeat(4);
        hall1sector1.addSeat(4);
        hall1sector1.addSeat(4);
        hall1sector1.addSeat(4);

        hall1sector2.addLine();
        hall1sector2.addLine();

        hall1sector3.addLine();
        hall1sector3.addLine();
        hall1sector3.addLine();
        hall1sector3.addLine();
        hall1sector3.addLine();
        hall1sector3.addLine();

        hall2sector1.addLine();
        hall2sector1.addLine();
        hall2sector1.addLine();


        hall2sector2.addLine();
        hall2sector2.addLine();

        hall2sector3.addLine();
        hall2sector3.addLine();
        hall2sector3.addLine();
        hall2sector3.addLine();
        hall2sector3.addLine();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,1);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);

        
        EntityManager em = JPAUtil.getCurrentEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(amlet);
        em.persist(tgm);

        em.persist(agent1);
        em.persist(agent2);

        em.persist(hall1);
        em.persist(hall2);

        Show show1 = new Show(cal.getTime(),50.0,amlet,hall1);

        cal.add(Calendar.DATE,2);

        Show show2 = new Show(cal.getTime(),50.0,amlet,hall1);

        cal.add(Calendar.DATE,2);

        Show show3 = new Show(cal.getTime(),50.0,amlet,hall1);

        SerialNumberProvider serial = new SerialNumberProviderImpl();

        em.persist(show1);
        em.persist(show2);
        em.persist(show3);
        
        tx.commit();
    
    }
}
