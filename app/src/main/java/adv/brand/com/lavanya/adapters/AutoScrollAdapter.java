package adv.brand.com.lavanya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.github.demono.adapter.InfinitePagerAdapter;

import java.util.List;

import adv.brand.com.lavanya.BrandApp;
import adv.brand.com.lavanya.R;
import adv.brand.com.lavanya.utils.CustomVolleyRequestQueue;

/**
 * Created by Mahesh on 24-09-2017.
 */

public class AutoScrollAdapter extends InfinitePagerAdapter {

    private List<String> data;
    LayoutInflater vi;
    private ImageLoader mImageLoader;


    public AutoScrollAdapter(List<String> data, Context context) {
        this.data = data;
        vi = LayoutInflater.from(context);
        mImageLoader = CustomVolleyRequestQueue.getInstance(BrandApp.getInstance())
                .getImageLoader();

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup container) {
        VieHolder holder=null;
        if (convertView==null)
        {
            convertView= vi.inflate(R.layout.row_profile_pics, null);
            holder= new VieHolder();
            holder.imgview=(NetworkImageView)convertView.findViewById(R.id.profilePics);
            convertView.setTag(holder);
        }
        else
        {
            holder=(VieHolder)convertView.getTag();
        }

        mImageLoader.get(data.get(position), ImageLoader.getImageListener(holder.imgview, R.drawable.home_stub, android.R.drawable.ic_dialog_alert));
        holder.imgview.setImageUrl(data.get(position), mImageLoader);

        return convertView;
    }



    class VieHolder
    {
        NetworkImageView imgview;
    }
}
