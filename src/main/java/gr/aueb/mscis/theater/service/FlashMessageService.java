package gr.aueb.mscis.theater.service;

import java.util.Map;

/**
 * Created by Myron on 24/2/2017.
 */
public interface FlashMessageService {

    public void addMessage(String message,FlashMessageType type);

    public Map<String,FlashMessageType> getMessages();

    public void clearAll();

}
