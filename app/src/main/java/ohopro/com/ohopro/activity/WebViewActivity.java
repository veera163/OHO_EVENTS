package ohopro.com.ohopro.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.utility.LocalStorageJavaScriptInterface;
import ohopro.com.ohopro.utility.PreferenceUtils;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    PreferenceUtils preferenceUtils;
    String url = "http://www.myeventorganiser.in/#/dashboard/products";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        preferenceUtils = new PreferenceUtils(this);
        webView = (WebView) findViewById(R.id.webview);
        String injection = "<html><head><script type='javascript'>LocalStorage.set('ls', 'userInfo', '" + preferenceUtils.getUserResponseJson() + "');window.location.replace('" + url + "');</script></head><body></body></html>";
        String cookieString = "authorizationData=" + preferenceUtils.getAccessToken() + "; path=/";
        CookieManager.getInstance().setCookie(url, cookieString);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDatabasePath("/data/data/" + webView.getContext().getPackageName() + "/databases/");
        LocalStorageJavaScriptInterface scriptInterface =
                new LocalStorageJavaScriptInterface(this);
        scriptInterface.setItem("userInfo", preferenceUtils.getUserResponseJson());
        webView.addJavascriptInterface(new LocalStorageJavaScriptInterface(this), "LocalStorage");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
            }
        });
        webView.loadUrl(url);//, injection, null, "UTF-8", null);
        //webView.loadUrl(url);

    }
}
