package gr.aueb.mscis.theater.service;

import java.util.HashMap;
import java.util.Map;

public class EmailProviderImpl implements EmailProvider {

    private Map<String,String> emails = new HashMap<String, String>();

    @Override
    public void sendEmail(String email, String message) {
        emails.put(email, message);
    }

    @Override
    public Map<String,String> getEmails(){
        return emails;
    }
}