package gr.aueb.mscis.theater.service;

import java.util.Map;

public interface EmailProvider {

    void sendEmail(String email, String message);

    Map<String,String> getEmails();
}
