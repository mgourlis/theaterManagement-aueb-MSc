package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.service.EmailProvider;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Myron on 24/2/2017.
 */
public class EmailProviderStub implements EmailProvider {

    private Map<String,String> emails = new HashMap<String, String>();

    @Override
    public void sendEmail(String email, String message) {
        emails.put(email, message);
    }

    public Map<String,String> getEmails(){
        return emails;
    }
}
