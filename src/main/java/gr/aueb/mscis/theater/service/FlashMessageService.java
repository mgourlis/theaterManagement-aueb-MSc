package gr.aueb.mscis.theater.service;

import java.util.Map;

public interface FlashMessageService {

    public void addMessage(String message,FlashMessageType type);

    public Map<String,FlashMessageType> getMessages();

    public void clearAll();

}
