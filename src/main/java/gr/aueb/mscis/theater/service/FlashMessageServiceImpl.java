package gr.aueb.mscis.theater.service;

import java.util.HashMap;
import java.util.Map;

public class FlashMessageServiceImpl implements FlashMessageService{

    private Map<String, FlashMessageType> messages = new HashMap<String, FlashMessageType>();

    @Override
    public void addMessage(String message,FlashMessageType type) {
        messages.put(message,type);
    }

    @Override
    public Map<String, FlashMessageType> getMessages() {
        return messages;
    }

    @Override
    public void clearAll() {
        messages.clear();
    }

    @Override
    public Map<String,String> toStringMap() {
        Map<String, String> msgstr= new HashMap<String, String>();
        for (Map.Entry<String, FlashMessageType> entry : messages.entrySet())
        {
            msgstr.put(entry.getKey(),entry.getValue().toString());
        }
        return msgstr;
    }
}
