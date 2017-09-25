package adv.brand.com.lavanya.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import adv.brand.com.lavanya.R;
import adv.brand.com.lavanya.customUI.CustomFontTextView;
import adv.brand.com.lavanya.model.CategoryFilterModel;

/**
 * Created by maheshb on 25/9/17.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    List<CategoryFilterModel> categoryList;
    List<CategoryFilterModel> currentList;
    CategoryFilter filter;

    public FilterAdapter(List<CategoryFilterModel> categoryList) {
        this.categoryList = categoryList;
        this.currentList=categoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_filter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        holder.textView.setText(currentList.get(position).getTitle());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentList.get(position).setSelected(isChecked);
            }
        });
        //Todo Handle Check change listner
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder  extends RecyclerView.ViewHolder
    {
        CheckBox checkBox;
        CustomFontTextView textView;
        public ViewHolder(View view)
        {
            super(view);
            textView = (CustomFontTextView) view.findViewById(R.id.filterTitle);
            checkBox = (CheckBox) view.findViewById(R.id.filterCheckbox);

        }
    }

    public CategoryFilter getFilter()
    {
        if (filter==null)
            filter= new CategoryFilter();
        return filter;
    }


    public class CategoryFilter extends Filter
    {
        FilterResults filterResults = new FilterResults();
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<CategoryFilterModel> filteredList;
            if(!TextUtils.isEmpty(constraint))
            {
                filteredList= new ArrayList<>();
                for (CategoryFilterModel s:categoryList)
                {
                    if(s.getTitle().contains(constraint))
                    filteredList.add(s);
                }

                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
            }
            else
            {
                filterResults.values = categoryList;
                filterResults.count = categoryList.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            currentList=(List<CategoryFilterModel>)results.values;
            notifyDataSetChanged();
        }
    }
}
