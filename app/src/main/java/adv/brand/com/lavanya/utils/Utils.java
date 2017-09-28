package adv.brand.com.lavanya.utils;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Mahesh on 25-09-2017.
 */

public class Utils {

    public static  String KEY_IS_FILTER_APPLYIED="isFilter";
    public static  String KEY_IS_TOUR_DISPLAYED ="tourDone";


    public static boolean isValid(List list)
    {
        return list!=null && list.size()>0;
    }

    public static boolean isValid(String str)
    {
        return !TextUtils.isEmpty(str);
    }

    public static void showToastLong(Context context, String message)
    {
        Toast.makeText(context, "tag", Toast.LENGTH_LONG).show();
    }
    public static void showToastShort(Context context, String message)
    {
        Toast.makeText(context, "tag", Toast.LENGTH_SHORT).show();
    }

    public static Spanned formHtml(String str)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {

            return Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT);
        }
        else {

            return (Html.fromHtml(str));

        }
    }
}
