package adv.brand.com.lavanya.model;

import adv.brand.com.lavanya.fragments.PageBaseFragment;

/**
 * Created by maheshb on 22/9/17.
 */

public class ChildFragmentModel {

    public PageBaseFragment fragment;
    public String title;
    public Integer id;

    @Override
    public boolean equals(Object obj) {

        boolean retVal = false;

        if (obj instanceof ChildFragmentModel){
            ChildFragmentModel ptr = (ChildFragmentModel) obj;
            retVal = ptr.id == this.id;
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
