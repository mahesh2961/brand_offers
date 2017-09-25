package adv.brand.com.lavanya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import adv.brand.com.lavanya.fragments.OfferFragment;
import adv.brand.com.lavanya.fragments.PageBaseFragment;
import adv.brand.com.lavanya.model.AppDataHandler;
import adv.brand.com.lavanya.model.OfferModel;
import adv.brand.com.lavanya.utils.BaseActivity;

/**
 * Created by maheshb on 25/9/17.
 */

public class OfferDetailsActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_details_activity);

        OfferModel offerModel=AppDataHandler.getInstance().getCurrentOffer();

        if(offerModel!=null) {
            PageBaseFragment fragment = new OfferFragment();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.key_img_url),offerModel.getImgUrl());
            bundle.putString(getString(R.string.key_descp), offerModel.getDesc());
            bundle.putString(getString(R.string.key_title), offerModel.getTitle());
            bundle.putString(getString(R.string.key_redrct), offerModel.getRedirect());
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, fragment);
            fragmentTransaction.commit();
        }
        else
        {
            finish();
            return;
        }
    }
}
