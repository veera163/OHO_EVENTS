package ohopro.com.ohopro.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.services.AccessTokenService;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.ConnectionDetector;
import ohopro.com.ohopro.utility.PreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    Timer timer;
    PreferenceUtils preferenceUtils;
    ConnectionDetector networkconn;
    Context mcontext;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        mcontext = SplashActivity.this;
        preferenceUtils = new PreferenceUtils(mcontext);

        networkconn = new ConnectionDetector(mcontext);

        timer = new Timer();

    }

    private void updatingAccessToken() {
        Intent intent = new Intent(this, AccessTokenService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (networkconn.isConnectedToInternet()) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent registeractivity;
                    if (preferenceUtils.isActiveUser()) {
                        updatingAccessToken();
                        registeractivity = new Intent(SplashActivity.this, DashBoardActivity.class);
                    } else {
                        registeractivity = new Intent(SplashActivity.this, LoginActivity.class);
                    }

                    startActivity(registeractivity);
                    finish();
                }
            }, 3000);
        } else {
            //getCustomToast(AppConstant.CHECK_NETWORK_CONN);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    showAlertDialog(SplashActivity.this, "INTERNET CONNECTION", AppConstant.CHECK_NETWORK_CONN, false);
                }
            });


        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        System.gc();
    }

    /**
     * Function to display simple Alert Dialog
     *
     * @param context - application context
     * @param title   - alert dialog title
     * @param message - alert message
     * @param status  - success/failure (used to set icon)
     *                - pass null if you don't want icon
     */
    private void showAlertDialog(Context context, String title, String message,
                                 Boolean status) {
        alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        //disable touch outside dialog.
        alertDialog.setCanceledOnTouchOutside(false);

        if (status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.mipmap.ic_launcher : R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Setting", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                open_device_setting_screen();


            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void open_device_setting_screen() {

        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
