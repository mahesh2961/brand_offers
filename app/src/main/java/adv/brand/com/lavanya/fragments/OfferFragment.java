package adv.brand.com.lavanya.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import adv.brand.com.lavanya.BrandApp;
import adv.brand.com.lavanya.R;
import adv.brand.com.lavanya.customUI.CustomFontTextView;
import adv.brand.com.lavanya.utils.CustomVolleyRequestQueue;

/**
 * Created by maheshb on 22/9/17.
 */

public class OfferFragment extends PageBaseFragment {


    String imageUrl;
    String redirectUrl;
    String title;
    String desc;

    NetworkImageView brandImg;
    CustomFontTextView txtTitle,txtdesc;

    private ImageLoader mImageLoader;

    DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .build();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageLoader = CustomVolleyRequestQueue.getInstance(BrandApp.getInstance())
                .getImageLoader();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.brand_fragment, container, false);
        initializeView(view);
        return view;
    }

    public void initializeView(View view)
    {
        if(TextUtils.isEmpty(imageUrl)) {
            imageUrl=getArguments().getString(BrandApp.getInstance().getString(R.string.key_img_url),"");
            redirectUrl=getArguments().getString(BrandApp.getInstance().getString(R.string.key_redrct),"");
            title=getArguments().getString(BrandApp.getInstance().getString(R.string.key_title),"");
            desc=getArguments().getString(BrandApp.getInstance().getString(R.string.key_descp),"");

        }

        imageUrl="https://www.dropbox.com/s/9pzumm4d8yipbz3/1001.jpg?dl=1";
        brandImg=(NetworkImageView)view.findViewById(R.id.offerImage);
        txtTitle=(CustomFontTextView)view.findViewById(R.id.offerTitle);
        txtdesc=(CustomFontTextView)view.findViewById(R.id.offerDescription);

        txtTitle.setText(title);
        txtdesc.setText(desc);

        /*com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(imageUrl,new ImageLoadingListener(){
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.e("tag", "onLoadingStarted");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.e("tag", "onLoadingFailed failReason::"+failReason.getCause());
            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.e("tag", "onLoadingComplete");
                brandImg.setImageBitmap(loadedImage);
            }
            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.e("tag", "onLoadingCancelled");
            }
        });*/
        mImageLoader.get(imageUrl, ImageLoader.getImageListener(brandImg, R.drawable.home_stub, android.R.drawable.ic_dialog_alert));
        brandImg.setImageUrl(imageUrl, mImageLoader);



    }
}
