package adv.brand.com.lavanya;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import adv.brand.com.lavanya.fragments.OfferFragment;
import adv.brand.com.lavanya.fragments.PageBaseFragment;
import adv.brand.com.lavanya.model.ChildFragmentModel;
import adv.brand.com.lavanya.model.OfferModel;
import adv.brand.com.lavanya.model.ServerOfferResponseModel;

public class MainActivity extends AppCompatActivity {


    ViewPager viewPager;

    ViewPagerAdapter adapter;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        viewPager.setVisibility(View.GONE);
        new GetStringFromUrl().execute("https://drive.google.com/uc?export=download&id=0B9o93WyRkCCSSVlyZmZIdlpla2c");
//        getSetData();
    }

    public void getSetData(String data)
    {
//        String data="{\"offers\":[{\"title\":\"50% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/qhodqgkf1okdbye/1002.jpg?dl=1\"},{\"title\":\"75% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/9pzumm4d8yipbz3/1001.jpg?dl=1\"},{\"title\":\"80% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/lff6wia3lsccqf0/1005.jpg?dl=1\"},{\"title\":\"14% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/zpek8tjalxar9i4/1004.jpg?dl=1\"},{\"title\":\"78% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/nhd7a556xeflhbc/1003.jpg?dl=1\"}]}";

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


    private class GetStringFromUrl extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // show progress dialog when downloading
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String result=null;

            try {
                // Create a URL for the desired page
                URL url = new URL(params[0]); //My text file location
                //First open the connection
                HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
                conn.setConnectTimeout(60000); // timing out in a minute

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                //t=(TextView)findViewById(R.id.TextView1); // ideally do this in onCreate()
                StringBuffer buffer = new StringBuffer();
                String str;
                while ((str = in.readLine()) != null) {
                    buffer.append(str);
                }
                in.close();

                result=buffer.toString();
                Log.d("MyTag","Result::"+result);

            } catch (Exception e) {
                Log.d("MyTag",e.toString());
            }
            return result;}

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // TODO change text view id for yourself
            if(!TextUtils.isEmpty(result))
            {
                viewPager.setVisibility(View.VISIBLE);
                getSetData(result);
            }
          progressBar.setVisibility(View.GONE);
        }
    }
}
