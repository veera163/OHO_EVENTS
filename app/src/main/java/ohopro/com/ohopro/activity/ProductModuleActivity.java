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
    String url = "http://www.myeventorganiser.in/#/dashboard/products";
    private PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        preferenceUtils = new PreferenceUtils(this);
        webView = (WebView) findViewById(R.id.webview);
        String cookieString = "authorizationData=" + preferenceUtils.getAccessToken() + "; path=/";
        CookieManager.getInstance().setCookie(url, cookieString);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        //webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        //webView.getSettings().setDatabasePath("/data/data/databases/");
        webView.loadUrl(url);
    }
}
