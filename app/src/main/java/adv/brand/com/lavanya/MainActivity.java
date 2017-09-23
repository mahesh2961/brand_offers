package adv.brand.com.lavanya;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import adv.brand.com.lavanya.fragments.OfferFragment;
import adv.brand.com.lavanya.fragments.PageBaseFragment;
import adv.brand.com.lavanya.model.ChildFragmentModel;
import adv.brand.com.lavanya.model.OfferModel;
import adv.brand.com.lavanya.model.ServerOfferResponseModel;

public class MainActivity extends AppCompatActivity {


    ViewPager viewPager;

    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        getSetData();
    }

    public void getSetData()
    {
        String data="{\"offers\":[{\"title\":\"50% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://drive.google.com/open?id=0B9o93WyRkCCSSngxNGZJYnBFeFE\"},{\"title\":\"75% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://drive.google.com/open?id=0B9o93WyRkCCSTjRMZl9rOGdIWlE\"},{\"title\":\"80% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://drive.google.com/open?id=0B9o93WyRkCCSZFBWWmxFemtJWlk\"},{\"title\":\"14% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://drive.google.com/open?id=0B9o93WyRkCCSeUd4MXFIdU15VTA\"},{\"title\":\"78% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://drive.google.com/open?id=0B9o93WyRkCCSU0xOV3R0WkR1NUk\"}]}";

        Gson gson= new Gson();

        ServerOfferResponseModel responseModel=gson.fromJson(data,ServerOfferResponseModel.class);

        if(responseModel!=null && responseModel.getOffers()!=null && !responseModel.getOffers().isEmpty())
        {
            List<ChildFragmentModel> fragments= new ArrayList<>();
            for (int i = 0; i <responseModel.getOffers().size() ; i++) {

                OfferModel offer= responseModel.getOffers().get(i);
                ChildFragmentModel model= new ChildFragmentModel();
                PageBaseFragment fragment= new OfferFragment();
                Bundle bundle= new Bundle();
                bundle.putString(getString(R.string.key_img_url),offer.getImgUrl());
                bundle.putString(getString(R.string.key_descp),offer.getDesc());


                bundle.putString(getString(R.string.key_title),offer.getTitle());
                bundle.putString(getString(R.string.key_redrct),offer.getRedirect());
                fragment.setArguments(bundle);
                model.fragment=fragment;
                model.id=i;
                model.title=offer.getTitle();
                fragments.add(model);
            }


            adapter= new ViewPagerAdapter(getSupportFragmentManager());
            adapter.setList(fragments);
            viewPager.setAdapter(adapter);
        }


    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

       /* if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }*/
    }



    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        List<ChildFragmentModel> mFragmentList = new ArrayList<>();
        FragmentManager manager;
        private long baseId = 0;


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            this.manager=manager;
        }

        public void setList(List<ChildFragmentModel> fragmentList)
        {
            this.mFragmentList=fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            try {
                return mFragmentList.get(position).fragment;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(ChildFragmentModel fragmentModel) {
            mFragmentList.add(fragmentModel);
        }


        @Override
        public int getItemPosition(Object object) {
            int index = mFragmentList.indexOf (object);

            if (index == -1)
                return POSITION_NONE;
            else
                return index;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentList.get(position).title;
        }
    }
}
