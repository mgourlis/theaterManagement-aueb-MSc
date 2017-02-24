package gr.aueb.mscis.theater.model;

import gr.aueb.mscis.theater.service.SerialNumberProvider;

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
