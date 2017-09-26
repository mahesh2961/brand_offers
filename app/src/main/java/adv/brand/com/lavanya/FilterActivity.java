package adv.brand.com.lavanya;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adv.brand.com.lavanya.adapters.FilterAdapter;
import adv.brand.com.lavanya.customUI.cutomPagerLibrary.CustomFontEditText;
import adv.brand.com.lavanya.model.AppDataHandler;
import adv.brand.com.lavanya.model.CategoryFilterModel;
import adv.brand.com.lavanya.utils.BaseActivity;
import adv.brand.com.lavanya.utils.DBHelper;
import adv.brand.com.lavanya.utils.PrefHandler;
import adv.brand.com.lavanya.utils.Utils;

/**
 * Created by maheshb on 25/9/17.
 */

public class FilterActivity extends BaseActivity
{
    List<String> categoryList;
    CustomFontEditText editText;
    RecyclerView recyclerView;
    FilterAdapter adapter;

    PrefHandler prefHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.filer_list_screen);
        super.onCreate(savedInstanceState);
        prefHandler= new PrefHandler(BrandApp.getInstance());
        editText=(CustomFontEditText)findViewById(R.id.editSearch);
        recyclerView=(RecyclerView)findViewById(R.id.filterListview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        categoryList= AppDataHandler.getInstance().getCategoryList();


        List<CategoryFilterModel> modelList= new ArrayList<>();
        List<String> selectedFilter=DBHelper.getInstance(BrandApp.getInstance()).getFilter();
        for (String s:categoryList) {
            CategoryFilterModel model= new CategoryFilterModel();
            model.setTitle(s);
            modelList.add(model);
        }


        if(Utils.isValid(selectedFilter)) {
            for (int i = 0; i <selectedFilter.size() ; i++) {
                if (categoryList.contains(selectedFilter.get(i)))
                modelList.get(categoryList.indexOf(selectedFilter.get(i))).setSelected(true);
            }
        }

        Collections.sort(modelList, new Comparator<CategoryFilterModel>() {
            @Override
            public int compare(CategoryFilterModel abc1, CategoryFilterModel abc2) {
                return Boolean.compare(abc2.isSelected(),abc1.isSelected());
            }
        });

        /*categoryList.add("dsssdsdsdssddsdsdsdsdsdsdsdsds");
        categoryList.add("jndqewonfwonwewefwf");
        categoryList.add("jndqewonfwonwewefwf");
        categoryList.add("jndqewonfwonwewefwf");
        categoryList.add("jndqewonfwonwewefwf");
        categoryList.add("jndqewonfwonwewefwf");
        categoryList.add("jndqewonfwonwewefwf");
        categoryList.add("jndqewonfwonwewefwf");
        categoryList.add("jndqewonfwonwewefwf");
        categoryList.add("jndqewonfwonwewefwf");*/
        if(modelList!=null && modelList.size()>0) {
            adapter = new FilterAdapter(modelList);
            recyclerView.setAdapter(adapter);
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(adapter!=null)
                    {
                        adapter.getFilter().filter(s);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_save && adapter!=null)
        {
            List<String> filterCategories=adapter.getSelectedCategories();

            if(Utils.isValid(filterCategories))
            {
                prefHandler.putBoolean(Utils.KEY_IS_FILTER_APPLYIED,true);
                AppDataHandler.getInstance().setSelectedFilter(filterCategories);
            }
            else
            {
                prefHandler.putBoolean(Utils.KEY_IS_FILTER_APPLYIED,true);
                AppDataHandler.getInstance().removeFilter();
            }

//            AppDataHandler.getInstance().setFilteredOffers(DBHelper.getInstance(BrandApp.getInstance()).getOffersByCategories(filterCategories));
            Intent intent= new Intent("catchange");
            LocalBroadcastManager.getInstance(BrandApp.getInstance()).sendBroadcast(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
