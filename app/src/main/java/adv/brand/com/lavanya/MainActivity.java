package adv.brand.com.lavanya;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import adv.brand.com.lavanya.customUI.CustomDialog;
import adv.brand.com.lavanya.fragments.ListViewFragment;
import adv.brand.com.lavanya.fragments.OfferFragment;
import adv.brand.com.lavanya.fragments.PageBaseFragment;
import adv.brand.com.lavanya.model.AppDataHandler;
import adv.brand.com.lavanya.model.ChildFragmentModel;
import adv.brand.com.lavanya.model.CustomerInfo;
import adv.brand.com.lavanya.model.OfferModel;
import adv.brand.com.lavanya.model.ServerOfferResponseModel;
import adv.brand.com.lavanya.utils.BaseActivity;
import adv.brand.com.lavanya.utils.PrefHandler;
import adv.brand.com.lavanya.utils.Utils;

public class MainActivity extends BaseActivity {



    ViewPager viewPager;

    ViewPagerAdapter adapter;

    ProgressBar progressBar;

    PrefHandler prefHandler;

    ListViewFragment fragmentListview;

    FrameLayout frameContainer;

    private static String KEY_IS_LISTVIEW="isListView";

    CategoryListener receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.layout);
        super.onCreate(savedInstanceState);

        toolbar.inflateMenu(R.menu.home_screen_menu);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        frameContainer=(FrameLayout)findViewById(R.id.frameContainer);
        prefHandler= new PrefHandler(BrandApp.getInstance());

        refreshData();
        receiver= new CategoryListener();

        IntentFilter intentFilter= new IntentFilter("catchange");
        LocalBroadcastManager.getInstance(BrandApp.getInstance()).registerReceiver(receiver,intentFilter);

        displayShowcaseView();
//        displayTour();









//        getSetData();
    }

    public void displayShowcaseView()
    {
        if (!prefHandler.getBoolean(Utils.KEY_IS_TOUR_DISPLAYED)) {
            final Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("KK", "Posting");
                    displayTour();
                   /* displayTour();
                    handler.removeCallbacks(this);
                    handler.removeCallbacksAndMessages(null);*/
                }
            },1*1000);
        }
    }


    public void displayTour()
    {
        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_right);


        // Tell our droid buddy where we want him to appear
        final Display display = getWindowManager().getDefaultDisplay();
        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
        droidTarget.offset((display.getWidth() / 2)+150, display.getHeight() / 2);


        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
//                        TapTarget.forToolbarNavigationIcon(toolbar, "This is the back button", sassyDesc).id(1),
                        // Likewise, this tap target will target the search button
                        TapTarget.forToolbarMenuItem(toolbar, R.id.action_refresh, "Refresh for new updated from store")
                                .dimColor(R.color.overlay)
                                .outerCircleColor(R.color.overlay)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(R.color.black_alpha_70)
                                .id(1),

                        TapTarget.forToolbarMenuItem(toolbar, R.id.action_call, "Ease to connect with store, JUST Call!")
                                .dimColor(R.color.overlay)
                                .outerCircleColor(R.color.overlay)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(R.color.black_alpha_70)
                                .id(2),

                        TapTarget.forToolbarMenuItem(toolbar, R.id.action_filter, "Filter Store updates as required.")
                                .dimColor(R.color.overlay)
                                .outerCircleColor(R.color.overlay)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(R.color.black_alpha_70)
                                .id(3),

                        // You can also target the overflow button in your toolbar
                        TapTarget.forToolbarOverflow(toolbar, "Explore different features")
                                .dimColor(R.color.overlay)
                                .outerCircleColor(R.color.overlay)
                                .transparentTarget(true)
                                .textColor(R.color.black_alpha_70)
                                .id(4),

                        TapTarget.forBounds(droidTarget, "Scroll right to see store updates")
                                .cancelable(true)
                                .icon(droid)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(false)
                                .textColor(R.color.black_alpha_70)
                                .id(5)
                        // This tap target will target our droid buddy at the given target rect
                       /* TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
                                .cancelable(false)
                                .icon(droid)
                                .id(4)*/
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
//                        ((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                       /* final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Uh oh")
                                .setMessage("You canceled the sequence")
                                .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });*/
                    }
                });

        sequence.continueOnCancel(true);
        sequence.start();
        prefHandler.putBoolean(Utils.KEY_IS_TOUR_DISPLAYED,true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(prefHandler.getBoolean(KEY_IS_LISTVIEW))
        {
            menu.findItem(R.id.action_view_change).setTitle("View Details");
        }
        else
            menu.findItem(R.id.action_view_change).setTitle("View List");



        return super.onPrepareOptionsMenu(menu);

    }

    public void refreshData() {
        new GetStringFromUrl().execute("https://drive.google.com/uc?export=download&id=0B9o93WyRkCCSSVlyZmZIdlpla2c");

    }

    public void getSetData(String data) {
//        String data="{\"offers\":[{\"title\":\"50% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/qhodqgkf1okdbye/1002.jpg?dl=1\"},{\"title\":\"75% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/9pzumm4d8yipbz3/1001.jpg?dl=1\"},{\"title\":\"80% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/lff6wia3lsccqf0/1005.jpg?dl=1\"},{\"title\":\"14% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/zpek8tjalxar9i4/1004.jpg?dl=1\"},{\"title\":\"78% Off\",\"desc\":\"dsdsdsdsd dssbsn kadknac lkkac kndsadk kdnakdnadk lnasdsnnda kasdnkdsnasa\",\"redirect\":\"http://jsonparseronline.com/\",\"imgUrl\":\"https://www.dropbox.com/s/nhd7a556xeflhbc/1003.jpg?dl=1\"}]}";

        Gson gson = new Gson();

        ServerOfferResponseModel responseModel = gson.fromJson(data, ServerOfferResponseModel.class);

        if (responseModel != null && responseModel.getOffers() != null && !responseModel.getOffers().isEmpty())
        {
            AppDataHandler.getInstance().reset();
            AppDataHandler.getInstance().setAppData(responseModel);
            AppDataHandler.getInstance().saveOffers();
            setView();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_screen_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_refresh) {
            refreshData();
        } else if (item.getItemId() == R.id.action_about) {
            if (AppDataHandler.getInstance().getCustomerInfo() != null) {
                Intent intent = new Intent(this, StoreProfileActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Data not available.", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.action_call) {
            CustomerInfo customerInfo = AppDataHandler.getInstance().getCustomerInfo();
            if (customerInfo != null && !TextUtils.isEmpty(customerInfo.getPhn())) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details
                }
                else
                {
                    displayCallConfirmationDialog();
                }
//                startActivity(surf);
            }
        }
        else if(item.getItemId()==R.id.action_view_change)
        {
            if(AppDataHandler.getInstance().getOffers()!=null && AppDataHandler.getInstance().getOffers().size()>0) {
                prefHandler.putBoolean(KEY_IS_LISTVIEW, !prefHandler.getBoolean(KEY_IS_LISTVIEW));
                setView();
            }
        }
        else if(item.getItemId()==R.id.action_filter)
        {
            Intent intent= new Intent(this,FilterActivity.class);
            startActivity(intent);
        }

            return super.onOptionsItemSelected(item);
    }


    public void setView()
    {
        if(prefHandler.getBoolean(KEY_IS_LISTVIEW))
        {
            List<OfferModel> offerModels= AppDataHandler.getInstance().getOffers();

            if (Utils.isValid(offerModels)) {
                if(fragmentListview==null)
                {
                    fragmentListview= new ListViewFragment();
                    fragmentListview.setOfferModels(offerModels);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction =
                            fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameContainer, fragmentListview);
                    fragmentTransaction.commit();
                }
                else
                {
                    fragmentListview.refereshWithNewData(offerModels);
                }
                viewPager.setVisibility(View.GONE);
                frameContainer.setVisibility(View.VISIBLE);
            }
        }
        else
        {

            List<OfferModel> offerModels= AppDataHandler.getInstance().getOffers();

            if (Utils.isValid(offerModels)) {
                List<ChildFragmentModel> fragments = new ArrayList<>();
                for (int i = 0; i < offerModels.size(); i++) {

                    OfferModel offer = offerModels.get(i);
                    ChildFragmentModel model = new ChildFragmentModel();
                    PageBaseFragment fragment = new OfferFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string.key_img_url), offer.getImgUrl());
                    bundle.putString(getString(R.string.key_descp), offer.getDesc());

                    bundle.putString(getString(R.string.key_title), offer.getTitle());
                    bundle.putString(getString(R.string.key_redrct), offer.getRedirect());
                    fragment.setArguments(bundle);
                    model.fragment = fragment;
                    model.id = i;
                    model.title = offer.getTitle();
                    fragments.add(model);

                if (adapter==null) {
                    adapter = new ViewPagerAdapter(getSupportFragmentManager());
                    adapter.setList(fragments);
                    viewPager.setAdapter(adapter);
                }
                else
                {
                    adapter.setList(fragments);
                    adapter.notifyDataSetChanged();
                }
            }
                viewPager.setVisibility(View.VISIBLE);
                frameContainer.setVisibility(View.GONE);
            }
        }
    }

    public  void makeCall()
    {
        String number = AppDataHandler.getInstance().getCustomerInfo().getPhn();
        Uri call = Uri.parse("tel:" + number);
        Intent surf = new Intent(Intent.ACTION_CALL, call);
        startActivity(surf);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    displayCallConfirmationDialog();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied,can not initiate call", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void displayCallConfirmationDialog()
    {


        final CustomDialog dialog= new CustomDialog(this);
        dialog.setTitle("Call");
        dialog.setMessage("This will make a call to store, do you wish to proceed?");
        dialog.setPositiveListner(new CustomDialog.onPositveClickerListener() {
            @Override
            public void onClick() {

                dialog.dismiss();
                makeCall();
            }
        });

        dialog.setNegativeListner(new CustomDialog.onNegativeClickListener() {
            @Override
            public void onClick() {
                dialog.dismiss();
            }
        });
        dialog.show();

       /* AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Call")
                .setMessage("This will make a call to store, do you wish to proceed?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        makeCall();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();*/
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
            viewPager.setVisibility(View.GONE);
            frameContainer.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... params) {

            String result=null;

            try {
                // Create a URL for the desired page
                URL url = new URL(params[0]); //My text file location
                //First open the connection
                HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
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
//                viewPager.setVisibility(View.VISIBLE);
                getSetData(result);
            }
          progressBar.setVisibility(View.GONE);
        }
    }


    class CategoryListener extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            setView();
        }
    }



}
