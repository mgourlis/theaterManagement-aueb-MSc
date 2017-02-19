package gr.aueb.mscis.theater.model;

import gr.aueb.mscis.theater.service.SerialNumberProvider;

import java.util.UUID;

/**
 * Created by Myron on 19/2/2017.
 */
public class SerialNumberProviderStub implements SerialNumberProvider {

    @Override
    public String createUniqueSerial() {
        return "111";
    }

    @Override
    public boolean isValidSerial(String serial) {
        if(serial.equals("111"))
            return true;
        else
            return false;
    }
}
