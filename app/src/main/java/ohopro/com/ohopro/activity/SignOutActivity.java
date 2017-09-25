package ohopro.com.ohopro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import ohopro.com.ohopro.R;
import ohopro.com.ohopro.services.AccessTokenService;
import ohopro.com.ohopro.utility.PreferenceUtils;

public class SignOutActivity extends AppCompatActivity {

    private WebView webView;
    private PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webview);
        preferenceUtils = new PreferenceUtils(this);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.addJavascriptInterface(new SignOutWebView(), "Android1");
        //webView.getSettings().setDatabasePath("/data/data/databases/");
        webView.loadUrl("file:///android_asset/remove_userOperations.html");
    }

    private void gotoLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

        Intent service = new Intent(this, AccessTokenService.class);
        stopService(service);
        preferenceUtils.doLogout();
    }

    public class SignOutWebView {
        @JavascriptInterface
        void signOut() {
            Toast.makeText(SignOutActivity.this, "clear ALl", Toast.LENGTH_SHORT).show();
            gotoLoginScreen();
        }
        @JavascriptInterface
        void showToast(String s) {
            Toast.makeText(SignOutActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
