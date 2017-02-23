package gr.aueb.mscis.theater.service;

/**
 * Created by Myron on 24/2/2017.
 */
public interface PaymentProvider {

    public boolean makePayment(String cardId, int endMonth, int endYear, int cvv, String ownerName);

}
