package gr.aueb.mscis.theater.useCases;

import gr.aueb.mscis.theater.service.PaymentProvider;

/**
 * Created by Myron on 24/2/2017.
 */
public class PaymentProviderStub implements PaymentProvider {
    @Override
    public boolean makePayment(String cardId, int endMonth, int endYear, int cvv, String ownerName) {
        if(cardId != null || !cardId.equals(""))
            return true;
        else return false;
    }
}
