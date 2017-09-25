package adv.brand.com.lavanya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

import adv.brand.com.lavanya.adapters.FilterAdapter;
import adv.brand.com.lavanya.customUI.cutomPagerLibrary.CustomFontEditText;
import adv.brand.com.lavanya.model.AppDataHandler;
import adv.brand.com.lavanya.model.CategoryFilterModel;
import adv.brand.com.lavanya.utils.BaseActivity;

/**
 * Created by maheshb on 25/9/17.
 */

public class FilterActivity extends BaseActivity
{
    List<String> categoryList;
    CustomFontEditText editText;
    RecyclerView recyclerView;
    FilterAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filer_list_screen);
        editText=(CustomFontEditText)findViewById(R.id.editSearch);
        recyclerView=(RecyclerView)findViewById(R.id.filterListview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        categoryList= AppDataHandler.getInstance().getCategoryList();


        List<CategoryFilterModel> modelList= new ArrayList<>();
        for (String s:categoryList) {

            CategoryFilterModel model= new CategoryFilterModel();
            model.setTitle(s);
            modelList.add(model);
        }
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
}
