package ohopro.com.ohopro.views;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by sai on 13-12-2016.
 */

public class CustomProgressLoader {

    Context context;
    ProgressDialog progressDialog;


    public CustomProgressLoader(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..Please Wait");
    }

    public void showProgressDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
