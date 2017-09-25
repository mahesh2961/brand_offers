package adv.brand.com.lavanya.model;

import java.util.List;

import adv.brand.com.lavanya.BrandApp;
import adv.brand.com.lavanya.utils.DBHelper;

/**
 * Created by Mahesh on 24-09-2017.
 */

public class AppDataHandler {

    private static AppDataHandler instance;

    ServerOfferResponseModel model;

    OfferModel currentOffer;

    public List<String> getCategoryList() {
        if(model!=null)
            return model.getFilters();
        return null;
    }


    public OfferModel getCurrentOffer() {
        return currentOffer;
    }

    public void setCurrentOffer(OfferModel currentOffer) {
        this.currentOffer = currentOffer;
    }

    private AppDataHandler() {
    }

    public static AppDataHandler getInstance()
    {
        if(instance==null)
            instance= new AppDataHandler();

        return instance;
    }

    public void reset()
    {
        model=null;
    }

    public void setAppData(ServerOfferResponseModel model)
    {
        this.model=model;
    }

    public CustomerInfo getCustomerInfo()
    {
        if (model!=null)
        {
            return model.getCustomInfo();
        }

        return null;
    }

    public List<OfferModel> getOffers()
    {
        if(model!=null)
            return model.getOffers();
        return null;
    }

    public void  saveOffers()
    {
        if(model!=null)
            new InsertOffersThread(model.getOffers()).start();
    }


    class InsertOffersThread extends Thread
    {

        List<OfferModel> offerModels;

        public InsertOffersThread(List<OfferModel> offerModels) {
            this.offerModels = offerModels;
        }

        @Override
        public void run() {
            super.run();

            DBHelper.getInstance(BrandApp.getInstance()).insertOffers(offerModels);
        }
    }


}
