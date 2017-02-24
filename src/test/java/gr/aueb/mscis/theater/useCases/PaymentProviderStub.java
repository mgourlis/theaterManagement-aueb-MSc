package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.service.PaymentProvider;

public class PaymentProviderStub implements PaymentProvider {
    @Override
    public boolean makePayment(String cardId, int endMonth, int endYear, int cvv, String ownerName, double amount) {
        if(cardId != null || !cardId.equals(""))
            return true;
        else return false;
    }
}
