package ohopro.com.ohopro.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.utility.PreferenceUtils;
import ohopro.com.ohopro.utility.WebAppInterface;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    PreferenceUtils preferenceUtils;
    String url = "http://192.168.0.102:9000/#/dashboard/items";

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        preferenceUtils = new PreferenceUtils(this);
        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        webView.getSettings().setDatabasePath("/data/data/databases/");
        webView.loadUrl("file:///android_asset/sq1.html");
    }

    public class Test
    {
        void callingLocalStorage(String value)
        {
            Toast.makeText(WebViewActivity.this, value, Toast.LENGTH_SHORT).show();
        }
    }
}
