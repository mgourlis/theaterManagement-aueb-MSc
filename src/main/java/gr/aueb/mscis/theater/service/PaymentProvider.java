package gr.aueb.mscis.theater.service;

public interface PaymentProvider {

    public boolean makePayment(String cardId, int endMonth, int endYear, int cvv, String ownerName, double amount);

}
