package gr.aueb.mscis.theater.service;

/**
 * Created by Myron on 19/2/2017.
 */
public interface EmailProvider {

    void sendEmail(String email, String message);
}
