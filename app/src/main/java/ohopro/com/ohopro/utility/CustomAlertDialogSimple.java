package ohopro.com.ohopro.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import ohopro.com.ohopro.R;


/**
 * Created by sai on 9/19/2016.
 */
public class CustomAlertDialogSimple {
    Context context;
    Dialog alertDialogBuilder;
    LinearLayout ll_parent;


    public interface OnDismisslistener {
        void onDismiss();
    }

    public CustomAlertDialogSimple(Context context) {
        this.context = context;

        alertDialogBuilder = new Dialog(context);
        alertDialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);

        alertDialogBuilder.setContentView(R.layout.dialog_alert);
        alertDialogBuilder.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialogBuilder.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.transparent));


        ll_parent = (LinearLayout) alertDialogBuilder.findViewById(R.id.ll_parent);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_parent.getLayoutParams();
        layoutParams.setMargins(100, 0, 100, 0);
    }

    public CustomAlertDialogSimple(Context context, final OnDismisslistener onDismisslistener) {
        this.context = context;

        alertDialogBuilder = new Dialog(context);
        alertDialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);

        alertDialogBuilder.setContentView(R.layout.dialog_alert);
        alertDialogBuilder.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialogBuilder.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.transparent));


        ll_parent = (LinearLayout) alertDialogBuilder.findViewById(R.id.ll_parent);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_parent.getLayoutParams();
        layoutParams.setMargins(100, 0, 100, 0);

        alertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onDismisslistener.onDismiss();
            }
        });
    }

    public void showAlertDialog(String tittle, String message) {
        ((TextView) alertDialogBuilder.findViewById(R.id.tv_type_of_field)).setText(tittle);
        ((TextView) alertDialogBuilder.findViewById(R.id.tv_dialog_text)).setText(message);
        alertDialogBuilder.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder.dismiss();
            }
        });

        alertDialogBuilder.show();
    }

    public void showAlertDialog(String message) {
        alertDialogBuilder.findViewById(R.id.tv_type_of_field).setVisibility(View.GONE);
        ((TextView) alertDialogBuilder.findViewById(R.id.tv_dialog_text)).setText(message);
        alertDialogBuilder.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    public void showAlertDialog(String tittle,
                                String message,
                                String buttonText,
                                float tittleSize,
                                float messageSize,
                                float buttonSize,
                                int backGround
    ) {
        TextView tv_type_of_field, tv_dialog_text, tv_ok;
        LinearLayout ll_parent;

        tv_dialog_text = (TextView) alertDialogBuilder.findViewById(R.id.tv_dialog_text);
        tv_type_of_field = (TextView) alertDialogBuilder.findViewById(R.id.tv_type_of_field);
        tv_ok = (TextView) alertDialogBuilder.findViewById(R.id.tv_ok);
        ll_parent = (LinearLayout) alertDialogBuilder.findViewById(R.id.ll_parent);

        tv_dialog_text.setText(message);
        tv_ok.setText(buttonText);
        tv_type_of_field.setText(tittle);

        tv_ok.setTextSize(buttonSize);
        tv_type_of_field.setTextSize(tittleSize);
        tv_dialog_text.setTextSize(messageSize);
        ll_parent.setBackgroundColor(ContextCompat.getColor(context, backGround));
        tv_ok.setAllCaps(false);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder.dismiss();
            }
        });
        alertDialogBuilder.show();
    }
}
