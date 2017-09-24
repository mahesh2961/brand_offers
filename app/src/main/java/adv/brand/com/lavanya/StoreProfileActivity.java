package adv.brand.com.lavanya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.github.demono.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import adv.brand.com.lavanya.adapters.AutoScrollAdapter;
import adv.brand.com.lavanya.customUI.CustomFontTextView;
import adv.brand.com.lavanya.model.AppDataHandler;
import adv.brand.com.lavanya.utils.BaseActivity;

/**
 * Created by Mahesh on 24-09-2017.
 */

public class StoreProfileActivity extends BaseActivity {

AutoScrollViewPager viewPager;
    AutoScrollAdapter adapter;
    CustomFontTextView txtAbout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_profile_activity);
        viewPager=(AutoScrollViewPager)findViewById(R.id.viewPager);
        txtAbout=(CustomFontTextView)findViewById(R.id.txtAboutDetails);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.ic_back));

//        toolbar.setContentInsetsAbsolute(toolbar.getContentInsetLeft(), 200);

        List<String> urls;
        if(AppDataHandler.getInstance().getCustomerInfo()!=null) {
            urls = AppDataHandler.getInstance().getCustomerInfo().getImages();
        }
        else
        {
            finish();
            return;
        }


//        String demoImage="https://www.dropbox.com/s/da8yh3sbrh5gvv3/banner.jpg?dl=1";

      /*  urls.add(demoImage);
        urls.add(demoImage);
        urls.add(demoImage);*/
        if(urls!=null &&!urls.isEmpty()) {
            adapter = new AutoScrollAdapter(urls, this);
            viewPager.setAdapter(adapter);
            viewPager.startAutoScroll();
        }
        else
        {
            viewPager.setVisibility(View.GONE);
        }

        String about=AppDataHandler.getInstance().getCustomerInfo().getAbout();
        if(!TextUtils.isEmpty(about))
        {
            txtAbout.setText(about);
        }
        else
        {
            txtAbout.setText("No Details Available");
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
