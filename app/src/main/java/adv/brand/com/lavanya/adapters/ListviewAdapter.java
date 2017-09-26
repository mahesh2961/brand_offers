package adv.brand.com.lavanya.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import adv.brand.com.lavanya.BrandApp;
import adv.brand.com.lavanya.R;
import adv.brand.com.lavanya.customUI.CustomFontTextView;
import adv.brand.com.lavanya.model.OfferModel;
import adv.brand.com.lavanya.utils.CustomVolleyRequestQueue;
import adv.brand.com.lavanya.utils.OnItemClickListener;

/**
 * Created by maheshb on 25/9/17.
 */

public class ListviewAdapter extends RecyclerView.Adapter<ListviewAdapter.ViewHolder> {


    List<OfferModel> offerModels;
    private ImageLoader mImageLoader;
    OnItemClickListener listner;


    public ListviewAdapter(List<OfferModel> offerModels, OnItemClickListener listner) {
        this.offerModels = offerModels;
        mImageLoader = CustomVolleyRequestQueue.getInstance(BrandApp.getInstance())
                .getImageLoader();
        this.listner=listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_offer, parent, false);

        return new ViewHolder(itemView);
    }

    public void setList(List<OfferModel> offerModels)
    {
        this.offerModels=offerModels;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final OfferModel model=offerModels.get(position);
        holder.txtTitle.setText(model.getTitle());
        holder.txtDescp.setText(model.getDesc());

        String offerUrl=model.getImgUrl();
        if(!TextUtils.isEmpty(offerUrl)) {
            mImageLoader.get(offerUrl, ImageLoader.getImageListener(holder.imageView, R.drawable.home_stub, android.R.drawable.ic_dialog_alert));
            holder.imageView.setImageUrl(offerUrl, mImageLoader);
        }
        else
        {
            //Todo set default  image
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onItemClick(model);
            }
        });



    }
    @Override
    public int getItemCount() {
        return offerModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        CustomFontTextView txtTitle;
        CustomFontTextView txtDescp;
        NetworkImageView imageView;
        public ViewHolder(View view)
        {
            super(view);
            txtTitle = (CustomFontTextView) view.findViewById(R.id.offerTitle);
            txtDescp = (CustomFontTextView) view.findViewById(R.id.offerDescription);
            imageView = (NetworkImageView) view.findViewById(R.id.brandImage);

        }
    }
}
