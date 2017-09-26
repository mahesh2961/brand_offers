package adv.brand.com.lavanya;

import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by maheshb on 22/9/17.
 */

public class BrandApp extends MultiDexApplication {

    static BrandApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();

        ImageLoader.getInstance().init(config);


    }

    public static BrandApp getInstance()
    {
        return mInstance;
    }
}
