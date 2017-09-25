package adv.brand.com.lavanya.customUI.cutomPagerLibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

import adv.brand.com.lavanya.R;


public class CustomFontEditText extends EditText implements OnTouchListener {
	private Context context;
	public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			this.context=context;
			init(attrs);
		}
		
		public CustomFontEditText(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.context=context;

			init(attrs);
		}
		
		public CustomFontEditText(Context context) {
			super(context);
			this.context=context;

			init(null);
		}
		
		private void init(AttributeSet attrs) {
			if (attrs!=null) {
				 TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontView);
				 
				 String fontName = a.getString(R.styleable.CustomFontView_fontName);
				 if (fontName!=null) {
					 Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
					 setTypeface(myTypeface);
				 }
				 
				 a.recycle();
				 
				setOnTouchListener(this);
			}
		}
		
		@Override
		public Editable getText() {
			// TODO Auto-generated method stub
			return super.getText();
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			//setBlackBackground();
			return false;
		}
		
		/*public void setBlackBackground()
		{
		setBackgroundResource(R.drawable.edittext_bottom_black);
		setPadding(context.getResources().getDimensionPixelSize(R.dimen.activity_margin_5dp), context.getResources().getDimensionPixelSize(R.dimen.activity_margin_10dp), 0, context.getResources().getDimensionPixelSize(R.dimen.activity_margin_5dp));
		//setPadding(5, 2, 0, 2);
		*//*setHintTextColor(getResources().getColor(R.color.text_color_white));
		setTextColor(getResources().getColor(R.color.text_color_white));*//*
		invalidate();
		}

		public void setRedBackground()
		{

			setBackgroundResource(R.drawable.errort_text_underline);
			setPadding(context.getResources().getDimensionPixelSize(R.dimen.activity_margin_5dp), context.getResources().getDimensionPixelSize(R.dimen.activity_margin_10dp), 0, context.getResources().getDimensionPixelSize(R.dimen.activity_margin_5dp));
			invalidate();
		}*/
		
		
	}

