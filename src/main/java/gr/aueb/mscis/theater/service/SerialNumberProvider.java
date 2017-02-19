package gr.aueb.mscis.theater.service;

/**
 * Created by Myron on 19/2/2017.
 */
public interface SerialNumberProvider {

    public String createUniqueSerial();

    public boolean isValidSerial(String serial);

}
