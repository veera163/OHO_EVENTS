package ohopro.com.ohopro.utility;

import android.content.Context;
import android.content.SharedPreferences;

import ohopro.com.ohopro.domains.RefreshTokenDomain;
import ohopro.com.ohopro.domains.UserDomain;


/**
 * Created by sai on 03-03-2016.
 */
public class PreferenceUtils {
    Context context;

    int PRIVATE_MODE = 0;


    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;

    private SharedPreferences tempPreferences;
    private SharedPreferences.Editor tempEditor;

    private String KEY_IS_LOGIN = "IsLoggedIn";
    private String KEY_USER_STATE = "userState";
    private String KEY_ACCESSTOKEN = "accessToken";
    private String KEY_REFRESHTOKEN = "refreshToken";
    private String KEY_CUSTOMER_NAME = "customername";
    private String KEY_CUSTOMER_MOBILE = "customermobile";
    private String KEY_CUSTOMER_EMAIL = "customeremail";
    private String KEY_CUSTOMER_TYPE = "customerType";
    private String NEWSESSION = "newsession";
    private String KEY_CUSTOMER_ID = "customerID";
    private String RESPONSEINJSON = "responseinJson";

    public long getExpiresIn() {
        return preferences.getLong(KEY_EXPIRESIN, 0);
    }

    private String KEY_EXPIRESIN = "expiresin";

    public PreferenceUtils(Context context) {

        this.context = context;

        preferences = context.getSharedPreferences(AppConstant.APP_PREF_NAME, PRIVATE_MODE);

        edit = preferences.edit();

        tempPreferences = context.getSharedPreferences(AppConstant.APP_TEMP_PREF_NAME, PRIVATE_MODE);
        tempEditor = preferences.edit();

    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public boolean isActiveUser() {
        return preferences.getBoolean(KEY_USER_STATE, false);
    }

    public void setUserState(boolean status) {
        edit.putBoolean(KEY_USER_STATE, status);
        edit.commit();
    }

    public void saveAccessTokenRefreshToken(String accessToken, String refreshToken, int expires_in) {
        edit.putString(KEY_ACCESSTOKEN, accessToken);
        edit.putString(KEY_REFRESHTOKEN, refreshToken);
        edit.putLong(KEY_EXPIRESIN, expires_in * 1000);
        edit.putBoolean(KEY_IS_LOGIN, true);
        edit.commit();
    }

    public void saveUserDetails(UserDomain userDetDomain) {
        edit.putString(KEY_CUSTOMER_NAME, userDetDomain.getFirstName());
        edit.putString(KEY_CUSTOMER_EMAIL, userDetDomain.getEmailId());
        edit.putString(KEY_CUSTOMER_MOBILE, userDetDomain.getPhoneNumber());
        edit.putString(KEY_CUSTOMER_TYPE, userDetDomain.getType());
        edit.putString(KEY_CUSTOMER_ID, userDetDomain.getId());
        edit.commit();
    }

    public String getUserName() {
        return preferences.getString(KEY_CUSTOMER_NAME, AppConstant.APP_NOT_AVAILABLE);
    }

    public String getUserId() {
        return preferences.getString(KEY_CUSTOMER_ID, AppConstant.APP_NOT_AVAILABLE);
    }

    public String getUserType() {
        return preferences.getString(KEY_CUSTOMER_TYPE, AppConstant.APP_NOT_AVAILABLE);
    }

    public String getUserEmail() {
        return preferences.getString(KEY_CUSTOMER_EMAIL, AppConstant.APP_NOT_AVAILABLE);
    }

    public String getUserMobile() {
        return preferences.getString(KEY_CUSTOMER_MOBILE, AppConstant.APP_NOT_AVAILABLE);
    }

    public String getAccessToken() {
        return preferences.getString(KEY_ACCESSTOKEN, AppConstant.APP_NOT_AVAILABLE);
    }

    public String getRefreshToken() {
        return preferences.getString(KEY_REFRESHTOKEN, AppConstant.APP_NOT_AVAILABLE);
    }


    public void doLogout() {
        edit.clear();
        edit.commit();
       /* mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(context.() *//* FragmentActivity *//*, this *//* OnConnectionFailedListener *//*)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addScope(new Scope(Scopes.EMAIL))
                .build();

        signOut();*/
    }


    public void newSession(boolean state) {
        tempEditor.putBoolean(NEWSESSION, state);
        tempEditor.commit();
    }

    public boolean isNewSession() {
        return tempPreferences.getBoolean(NEWSESSION, false);
    }

    public void saveAccessTokenRefreshToken(RefreshTokenDomain refreshTokenDomain) {
        edit.putString(KEY_ACCESSTOKEN, refreshTokenDomain.getAccess_token());
        edit.putString(KEY_REFRESHTOKEN, refreshTokenDomain.getRefresh_token());
        edit.putLong(KEY_EXPIRESIN, refreshTokenDomain.getExpires_in() * 1000);
        //edit.putBoolean(KEY_IS_LOGIN, true);
        edit.commit();
    }

    public void saveRawAccessTokenRefreshToken(String s) {
        edit.putString("rawauthdata", s);
        edit.commit();
    }

    public String getRawAccessTokenRefreshToken() {
        return preferences.getString("rawauthdata", "NA");
    }

    public void setUserResponseJson(String respInJson) {
        edit.putString(RESPONSEINJSON, respInJson);
        edit.commit();
    }

    public String getUserResponseJson() {
        return preferences.getString(RESPONSEINJSON, "NA");
    }

}
