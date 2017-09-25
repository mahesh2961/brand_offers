package adv.brand.com.lavanya.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import adv.brand.com.lavanya.OfferDetailsActivity;
import adv.brand.com.lavanya.R;
import adv.brand.com.lavanya.adapters.ListviewAdapter;
import adv.brand.com.lavanya.model.AppDataHandler;
import adv.brand.com.lavanya.model.OfferModel;
import adv.brand.com.lavanya.utils.OnItemClickListener;

/**
 * Created by maheshb on 25/9/17.
 */

public class ListViewFragment extends PageBaseFragment implements OnItemClickListener {


    RecyclerView recyclerView;
    ListviewAdapter adapter;
    List<OfferModel> offerModels;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.list_view_fragment, container, false);
        initializeView(view);
        return view;
    }

    public void setOfferModels(List<OfferModel> offerModels) {
        this.offerModels = offerModels;
    }

    public void initializeView(View view)
    {
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
//        List<OfferModel> offerModels=offerModels;
        if(offerModels!=null && offerModels.size()>0)
        {
            adapter= new ListviewAdapter(offerModels,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(OfferModel model) {

        Intent intent= new Intent(getContext(), OfferDetailsActivity.class);
        AppDataHandler.getInstance().setCurrentOffer(model);
        startActivity(intent);
    }
}
