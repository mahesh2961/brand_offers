package adv.brand.com.lavanya.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import adv.brand.com.lavanya.BrandApp;
import adv.brand.com.lavanya.R;
import adv.brand.com.lavanya.WebviewActivity;
import adv.brand.com.lavanya.customUI.CustomFontTextView;
import adv.brand.com.lavanya.customUI.CustomImageView;
import adv.brand.com.lavanya.model.AppDataHandler;
import adv.brand.com.lavanya.model.OfferModel;
import adv.brand.com.lavanya.utils.CustomVolleyRequestQueue;

/**
 * Created by maheshb on 22/9/17.
 */

public class OfferFragment extends PageBaseFragment {


    String imageUrl;
    String redirectUrl;
    String title;
    String desc;

    CustomImageView brandImg;
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

/*
        imageUrl="https://www.dropbox.com/s/9pzumm4d8yipbz3/1001.jpg?dl=1";
*/
        brandImg=(CustomImageView)view.findViewById(R.id.offerImage);
        txtTitle=(CustomFontTextView)view.findViewById(R.id.offerTitle);
        txtdesc=(CustomFontTextView)view.findViewById(R.id.offerDescription);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {

            txtTitle.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT));
            txtdesc.setText(Html.fromHtml(desc, Html.FROM_HTML_MODE_COMPACT));
        }
        else {

            txtTitle.setText(Html.fromHtml(title));
            txtdesc.setText(Html.fromHtml(desc));

        }



        brandImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OfferModel offerModel= new OfferModel();
                offerModel.setTitle(title);
                offerModel.setDesc(desc);
                offerModel.setImgUrl(imageUrl);
                offerModel.setRedirect(redirectUrl);
                AppDataHandler.getInstance().setCurrentOffer(offerModel);
                Intent intent= new Intent(getContext(), WebviewActivity.class);
                startActivity(intent);

            }
        });

       mImageLoader.get(imageUrl, ImageLoader.getImageListener(brandImg, R.drawable.home_stub, android.R.drawable.ic_dialog_alert));
       brandImg.setImageUrl(imageUrl, mImageLoader);

    }



}
