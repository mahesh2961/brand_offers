package adv.brand.com.lavanya.model;

import java.util.List;

/**
 * Created by maheshb on 22/9/17.
 */

public class ServerOfferResponseModel {

    List<OfferModel> offers;

    public List<OfferModel> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferModel> offers) {
        this.offers = offers;
    }
}
