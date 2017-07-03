package ohopro.com.ohopro.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ohopro.com.ohopro.activity.LoginActivity;
import ohopro.com.ohopro.appserviceurl.ServiceURL;
import ohopro.com.ohopro.domains.RefreshTokenDomain;
import ohopro.com.ohopro.utility.AppConstant;
import ohopro.com.ohopro.utility.LoggerUtils;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.webaccess.HttpHelper;

public class AccessTokenService extends Service {
    int i = 0;
    HttpHelper httpHelper = new HttpHelper();
    private PreferenceUtils preferenceUtils;

    public AccessTokenService() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableCode);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        runnableCode = new Runnable() {
            @Override
            public void run() {
                try {
                    PreferenceUtils preferenceUtils;
                    preferenceUtils = new PreferenceUtils(AccessTokenService.this);
                    StringBuilder urlBuilder = new StringBuilder();
                    urlBuilder.append(ServiceURL.HOST_URL);
                    urlBuilder.append("oauth/token?grant_type=refresh_token&refresh_token=");
                    urlBuilder.append(preferenceUtils.getRefreshToken());
                    urlBuilder.append("&client_secret=123456&client_id=clientapp");

                    new UpdateAccessToken().execute(urlBuilder.toString());
                } finally {
                    PreferenceUtils preferenceUtils;
                    preferenceUtils = new PreferenceUtils(AccessTokenService.this);
                    handler.postDelayed(runnableCode, (long) (preferenceUtils.getExpiresIn() * 0.75));
                    //handler.postDelayed(runnableCode, 10000);

                    LoggerUtils.info("refresh time", String.valueOf(preferenceUtils.getExpiresIn() * 0.75));
                }
            }
        };
        callAsynchronousTask();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Handler handler;
    Runnable runnableCode;

    public void callAsynchronousTask() {

        //updateAccessToken.execute("http://139.162.42.96:9900/api/Countries");

        runnableCode.run();
    }

    public class UpdateAccessToken extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            preferenceUtils = new PreferenceUtils(AccessTokenService.this);
            LoggerUtils.info("service data" + i++, s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("access_token")) {
                    RefreshTokenDomain refreshTokenDomain = new Gson().fromJson(s, RefreshTokenDomain.class);
                    preferenceUtils.saveAccessTokenRefreshToken(refreshTokenDomain);
                    AppConstant.accessUpdated = true;
                    if (AppConstant.updated != null)
                        AppConstant.updated.isUpdated();
                } else {
                    gotoLogin();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            return httpHelper.sendPOSTRequest(params[0], new String(), "Basic Y2xpZW50YXBwOjEyMzQ1Ng==");
        }
    }

    private void gotoLogin() {
        preferenceUtils.doLogout();
        onDestroy();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public interface Updated {
        void isUpdated();
    }
}
