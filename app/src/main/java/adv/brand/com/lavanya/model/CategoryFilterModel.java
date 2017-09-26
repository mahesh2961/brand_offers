package adv.brand.com.lavanya.model;

import android.text.TextUtils;

import adv.brand.com.lavanya.utils.Utils;

/**
 * Created by maheshb on 25/9/17.
 */

public class CategoryFilterModel {
    String title;
    boolean isSelected;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof String)
        {
            String values=(String) obj;
            return (Utils.isValid(values) && values.equals(this.title));
        }

        return false;
    }
}
