package ohopro.com.ohopro.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;

import org.json.JSONObject;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.utility.PreferenceUtils;

public class ProductModuleActivity extends AppCompatActivity {

    WebView webView;
    //String cookie_url = "http://192.168.0.107:9000/";
    String cookie_url = "http://www.myeventorganiser.in/";
//    String url = "http://192.168.0.107:9000/#/configAppUserInfo";
    String url = "http://www.myeventorganiser.in/#/configAppUserInfo";
    private PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        preferenceUtils = new PreferenceUtils(this);
        webView = (WebView) findViewById(R.id.webview);
        CookieManager cookieManager = CookieManager.getInstance();
        JSONObject cookieObject = new JSONObject();
        String newCookieString = "";
        try {
            cookieObject.put("token", preferenceUtils.getAccessToken());
            cookieObject.put("userName", preferenceUtils.getUserEmail());
            cookieObject.put("refreshToken", preferenceUtils.getRefreshToken());
            cookieObject.put("useRefreshTokens", true);
            newCookieString = cookieObject.toString();
        }catch(Exception e ){
        }
        String cookieString = "authorizationData=" + newCookieString + "; path=/";
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(cookie_url, cookieString);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl(url+"?userId="+preferenceUtils.getUserEmail());

    }
}
