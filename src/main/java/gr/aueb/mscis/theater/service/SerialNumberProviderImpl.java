package gr.aueb.mscis.theater.service;

import java.util.UUID;

public class SerialNumberProviderImpl implements SerialNumberProvider {

    @Override
    public String createUniqueSerial() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean isValidSerial(String serial) {
        try{
            UUID.fromString(serial);
        }catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }
}