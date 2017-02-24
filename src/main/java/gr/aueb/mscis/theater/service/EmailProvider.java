package gr.aueb.mscis.theater.service;

public interface EmailProvider {

    void sendEmail(String email, String message);
}
