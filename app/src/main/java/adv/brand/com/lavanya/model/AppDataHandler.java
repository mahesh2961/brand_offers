package adv.brand.com.lavanya.model;

/**
 * Created by Mahesh on 24-09-2017.
 */

public class AppDataHandler {

    private static AppDataHandler instance;

    ServerOfferResponseModel model;

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


}
