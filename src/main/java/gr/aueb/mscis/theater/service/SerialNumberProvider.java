package gr.aueb.mscis.theater.service;

public interface SerialNumberProvider {

    public String createUniqueSerial();

    public boolean isValidSerial(String serial);

}
