package ohopro.com.ohopro.fragmentDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import ohopro.com.ohopro.R;


/**
 * Created by sai on 01-12-2016.
 */

public class ChangePasswordDialog extends DialogFragment {
    ChangePasswordListener changePasswordListener;
    EditText edt_old_pwd, edt_new_pwd, edt_cnf_pwd;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChangePasswordListener)
            changePasswordListener = (ChangePasswordListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder changePwdBuilder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_changepwd, null);
        initViewController(view);
        changePwdBuilder.setView(view);
        changePwdBuilder.setCancelable(false);
        changePwdBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changePasswordListener.clickOnUpdate(edt_old_pwd.getText().toString(),
                        edt_new_pwd.getText().toString(),
                        edt_cnf_pwd.getText().toString());
            }
        });
        changePwdBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changePasswordListener.clickOnCancel();
            }
        });
        return changePwdBuilder.create();
    }

    private void initViewController(View view) {
        edt_cnf_pwd = (EditText) view.findViewById(R.id.edt_cnf_pwd);
        edt_old_pwd = (EditText) view.findViewById(R.id.edt_old_pwd);
        edt_new_pwd = (EditText) view.findViewById(R.id.edt_new_pwd);
    }

    public interface ChangePasswordListener {
        void clickOnCancel();

        void clickOnUpdate(String oldNewPwd, String newPwd, String cnfmPwd);
    }
}
