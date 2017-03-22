package gr.aueb.mscis.theater.service;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class AuthenticateService {

    EntityManager em;
    FlashMessageService flashserv;
    SerialNumberProvider serialprov;
    UserService userv;

    public AuthenticateService(FlashMessageService flashserv,SerialNumberProvider serialprov) {
        em = JPAUtil.getCurrentEntityManager();
        this.flashserv = flashserv;
        this.serialprov = serialprov;
        userv = new UserService(flashserv);

    }

    public User login(String usermail, String password){
        User user = userv.findUserByEmail(usermail);
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(user != null){
            if(user.getPassword().equals(password)){
                user.setToken(serialprov.createUniqueSerial());
                em.merge(user);
                em.flush();
                em.refresh(user);
            }else{
                user = null;
            }
        }
        tx.commit();
        return user;
    }

    public User isAuthenticated(int userId, String token){
        User user = userv.findUserById(userId);;
        if(user != null)
            if(user.getToken() != null)
                return user.getToken().equals(token) ? user : null;
        return null;
    }

    public User logout(int userId, String token){
        User user = userv.findUserById(userId);
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(user != null){
            if(isAuthenticated(userId,token) != null){
                user.setToken(null);
                em.merge(user);
                em.flush();
                em.refresh(user);
                tx.commit();
                return user;
            }
        }
        tx.commit();
        return null;
    }
}
