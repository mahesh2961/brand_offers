package adv.brand.com.lavanya.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import adv.brand.com.lavanya.BrandApp;
import adv.brand.com.lavanya.MainActivity;
import adv.brand.com.lavanya.R;
import adv.brand.com.lavanya.WebviewActivity;
import adv.brand.com.lavanya.customUI.CustomFontTextView;
import adv.brand.com.lavanya.customUI.CustomImageView;
import adv.brand.com.lavanya.model.AppDataHandler;
import adv.brand.com.lavanya.model.OfferModel;
import adv.brand.com.lavanya.utils.CustomVolleyRequestQueue;
import adv.brand.com.lavanya.utils.Utils;

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
    Bitmap downloadedBitmap;
    ImageView imgShare;

    private boolean isBitmapAvailable=false;

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
        isBitmapAvailable=false;
        if(TextUtils.isEmpty(imageUrl)) {
            imageUrl=getArguments().getString(BrandApp.getInstance().getString(R.string.key_img_url),"");
            redirectUrl=getArguments().getString(BrandApp.getInstance().getString(R.string.key_redrct),"");
            title=getArguments().getString(BrandApp.getInstance().getString(R.string.key_title),"");
            desc=getArguments().getString(BrandApp.getInstance().getString(R.string.key_descp),"");

        }

        imgShare=(ImageView)view.findViewById(R.id.share);
/*
        imageUrl="https://www.dropbox.com/s/9pzumm4d8yipbz3/1001.jpg?dl=1";
*/
        brandImg=(CustomImageView)view.findViewById(R.id.offerImage);
        txtTitle=(CustomFontTextView)view.findViewById(R.id.offerTitle);
        txtdesc=(CustomFontTextView)view.findViewById(R.id.offerDescription);
        txtTitle.setText(Utils.formHtml(title));
        txtdesc.setText(Utils.formHtml(desc));




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

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                    }
                    else
                    {
                        startShareIntent();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

       mImageLoader.get(imageUrl, getImageListener(brandImg, R.drawable.home_stub, android.R.drawable.ic_dialog_alert));
       brandImg.setImageUrl(imageUrl, mImageLoader);



    }
    public void startShareIntent() throws Exception
    {
        if(isBitmapAvailable)
        {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, Utils.formHtml(title)+"\n"+Utils.formHtml(desc)+"\n Download App from (google play link)");
            String url= MediaStore.Images.Media.insertImage(getContext().getContentResolver(), downloadedBitmap, "myfile", "new image");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Share"));
        }
        else
        {
            Utils.showToastShort(getActivity(),"Image is not yet downloaded, you can wait or share message");
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, Utils.formHtml(title)+"\n"+Utils.formHtml(desc)+"\n Download App from (google play link)");
                        /*String url= MediaStore.Images.Media.insertImage(getContext().getContentResolver(), downloadedBitmap, "myfile", "new image");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));*/
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "Share"));
        }
    }

    public  ImageLoader.ImageListener getImageListener(final ImageView view,
                                                             final int defaultImageResId, final int errorImageResId) {
        return new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                    downloadedBitmap=response.getBitmap();
                    isBitmapAvailable=true;
                } else if (defaultImageResId != 0) {
                    view.setImageResource(defaultImageResId);
                }
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 2: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        startShareIntent();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "Permission denied,can not share", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }



}
