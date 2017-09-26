package adv.brand.com.lavanya.customUI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import adv.brand.com.lavanya.R;

/**
 * Created by maheshb on 26/9/17.
 */

public class CustomDialog {

    Dialog mdialog;
    Context context;
    CustomFontTextView txtTitle,txtMessage;
    CustomButton btnPositive,btnNegative;
    onPositveClickerListener positiveListner;
    onNegativeClickListener negativeListner;

    public onPositveClickerListener getPositiveListner() {
        return positiveListner;
    }

    public void setPositiveListner(onPositveClickerListener positiveListner) {
        this.positiveListner = positiveListner;
    }

    public onNegativeClickListener getNegativeListner() {
        return negativeListner;
    }

    public void setNegativeListner(onNegativeClickListener negativeListner) {
        this.negativeListner = negativeListner;
    }

    public CustomDialog(Context context) {
        this.context = context;
        mdialog = new Dialog(context,android.R.style.Theme_Dialog);
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

       /* LayoutInflater inflate = LayoutInflater.from(context);
        View alertView = inflate.inflate(R.layout.custom_dialog, null);*/
         mdialog.setContentView(R.layout.custom_dialog);
        txtTitle=(CustomFontTextView)mdialog.findViewById(R.id.dialogTitle);
        txtMessage=(CustomFontTextView)mdialog.findViewById(R.id.dialogMessage);
        btnPositive=(CustomButton)mdialog.findViewById(R.id.btnPositive);
        btnNegative=(CustomButton)mdialog.findViewById(R.id.btnNegative);

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(positiveListner!=null)
                    positiveListner.onClick();
            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(negativeListner!=null)
                    negativeListner.onClick();
            }
        });




    }




    public void setTitle(String title)
    {
        txtTitle.setText(title);
    }

    public void setMessage(String message)
    {
        txtMessage.setText(message);

    }


    public interface onPositveClickerListener
    {
        public void onClick();
    }


    public interface onNegativeClickListener
    {
        public void onClick();
    }

    public void dismiss()
    {
        try {
            mdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show()
    {
        try {
            mdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
