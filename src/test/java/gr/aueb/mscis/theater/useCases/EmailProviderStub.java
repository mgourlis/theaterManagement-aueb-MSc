package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.service.EmailProvider;
import java.util.HashMap;
import java.util.Map;

public class EmailProviderStub implements EmailProvider {

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
