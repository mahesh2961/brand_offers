package adv.brand.com.lavanya.model;

import java.util.List;

/**
 * Created by maheshb on 22/9/17.
 */

public class ServerOfferResponseModel {

    List<OfferModel> offers;

    CustomerInfo customInfo;

    List<String> filters;

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public CustomerInfo getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(CustomerInfo customInfo) {
        this.customInfo = customInfo;
    }

    public List<OfferModel> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferModel> offers) {
        this.offers = offers;
    }
}
