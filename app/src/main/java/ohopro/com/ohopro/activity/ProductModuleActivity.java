package ohopro.com.ohopro.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.utility.PreferenceUtils;

public class ProductModuleActivity extends AppCompatActivity {

    WebView webView;
    String cookie_url = "http://192.168.0.107:9000/";
    String url = "http://192.168.0.107:9000/#/configAppUserInfo";
//    String url = "http://www.myeventorganiser.in/#/configAppUserInfo";
    private PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // String authData="%7B%22token%22%3A%22703df75b-c032-40ef-b9ce-cfe2f430ad94%22%2C%22userName%22%3A%22kiran%22%2C%22refreshToken%22%3A%22a248560e-85e4-45df-8774-28e8ce7051f3%22%2C%22useRefreshTokens%22%3Atrue%2C%22scope%22%3A%22read%20write%22%7D";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        preferenceUtils = new PreferenceUtils(this);
        webView = (WebView) findViewById(R.id.webview);
        String cookieString = "authorizationData=" + preferenceUtils.getRawAccessTokenRefreshToken() + "; path=/";
        CookieManager cookieManager = CookieManager.getInstance();

        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(cookie_url, cookieString);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        //webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        //webView.getSettings().setDatabasePath("/data/data/databases/");
        // TODO --- pass the logged in user here.
        webView.loadUrl(url+"?userId="+preferenceUtils.getUserName());
    }
}
