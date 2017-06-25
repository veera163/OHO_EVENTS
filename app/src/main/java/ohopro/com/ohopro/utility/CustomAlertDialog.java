package ohopro.com.ohopro.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import ohopro.com.ohopro.R;

/**
 * Created by sai on 11-12-2016.
 */

public class CustomAlertDialog extends DialogFragment {

    public static String MESSAGE = "message";
    public static String TITTLE = "titttle";
    private String message;
    private String tittle = "Alert";

    TextView tv_tittle, tv_message;
    DialogController dialogController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(TITTLE))
                tittle = getArguments().getString(TITTLE);

            message = getArguments().getString(MESSAGE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.alertdialog, null);
        initViewController(view);
        tv_message.setText(message);
        tv_tittle.setText(tittle);
        builder.setView(view);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogController.clickonPositive(getTag());
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogController.clickonNegative(getTag());

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogController) {
            this.dialogController = (DialogController) context;
        }
    }

    private void initViewController(View view) {
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        tv_tittle = (TextView) view.findViewById(R.id.tv_tittle);
    }

    public interface DialogController {
        void clickonPositive(String tag);

        void clickonNegative(String tag);
    }
}
