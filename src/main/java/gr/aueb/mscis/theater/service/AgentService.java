package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.Agent;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.*;
import java.util.List;

public class AgentService {

    EntityManager em;
    FlashMessageService flashserv;

    public AgentService(FlashMessageService flashserv) {
        em = JPAUtil.getCurrentEntityManager();
        this.flashserv = flashserv;
    }


    public List<Agent> findAllAgents() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<Agent> results = null;
        results = em.createQuery("select a from Agent a").getResultList();
        if(results.isEmpty()){
            flashserv.addMessage("No agents found", FlashMessageType.Info);
        }
        tx.commit();

        return results;
    }

    public Agent findAgentById(int id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Agent agent = null;
            agent = em.find(Agent.class, id);
            if(agent == null){
                flashserv.addMessage("Agent does not exist",FlashMessageType.Warning);
            }
            tx.commit();
        return agent;
    }

    public List<Agent> searchAgentsNameData(String namedata) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Agent> results = null;
            results = em.createQuery("select a from Agent a where a.firstName like :namedata or a.lastName like :namedata")
                    .setParameter("namedata", "%"+namedata +"%").getResultList();
        if(results.isEmpty()){
            flashserv.addMessage("No agents found", FlashMessageType.Info);
        }
        tx.commit();
        return results;
    }

    public Agent save(Agent agent) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //Validate on same name with existing
        if (agent.getId() != null) {
            try {
                agent = em.merge(agent);
                em.flush();
                em.refresh(agent);
            } catch (PersistenceException ex) {
                tx.rollback();
                return null;
            }
        } else {
            try {
                em.persist(agent);
            } catch (PersistenceException ex) {
                tx.rollback();
                return null;
            }
        }
        tx.commit();
        return agent;
    }

    public boolean delete(int agentId) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Agent agent = em.getReference(Agent.class, agentId);
            em.remove(agent);
            tx.commit();
        } catch (EntityNotFoundException e) {
            tx.rollback();
            return false;
        }

        return true;
    }
}
