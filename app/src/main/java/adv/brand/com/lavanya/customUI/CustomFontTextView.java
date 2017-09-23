package adv.brand.com.lavanya.customUI;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import adv.brand.com.lavanya.R;
import adv.brand.com.lavanya.utils.FontCache;

public class CustomFontTextView extends TextView {

		public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			init(attrs);
		}
		
		public CustomFontTextView(Context context, AttributeSet attrs) {
			super(context, attrs);
			init(attrs);
			
		}
		
		public CustomFontTextView(Context context) {
			super(context);
			init(null);
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

