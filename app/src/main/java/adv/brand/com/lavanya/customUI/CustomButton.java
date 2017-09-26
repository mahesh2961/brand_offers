package adv.brand.com.lavanya.customUI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import adv.brand.com.lavanya.R;
import adv.brand.com.lavanya.utils.FontCache;

/**
 * Created by maheshb on 26/9/17.
 */

public class CustomButton extends Button {
    public CustomButton(Context context) {
        super(context);
        init(null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontView);
            String fontName = a.getString(R.styleable.CustomFontView_fontName);
            if (fontName!=null) {
                Typeface myTypeface = FontCache.get(fontName,getContext());// Typeface.createFromAsset(getContext().getAssets(), ;
                setTypeface(myTypeface);
            }

				/* Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-ThinItalic.ttf");
				 setTypeface(myTypeface,1);*/
            a.recycle();
        }
    }
}
