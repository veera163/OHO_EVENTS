package ohopro.com.ohopro.utility;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import ohopro.com.ohopro.activity.ProductModuleActivity;

/**
 * Created by kiran on 9/24/2017.
 */

public class WebAppInterface {

    Context mContext;
    PreferenceUtils preferenceUtils;

    /**
     * Instantiate the interface and set the context
     */
    public WebAppInterface(Context c) {
        mContext = c;
        preferenceUtils = new PreferenceUtils(c);
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getValue() {
        return preferenceUtils.getUserResponseJson();
    }
    @JavascriptInterface
    public void onFinish(){
        Toast.makeText(mContext, "finish", Toast.LENGTH_SHORT).show();
        // webView.loadUrl("file:///android_asset/sq2.html");
        Intent intent = new Intent(mContext, ProductModuleActivity.class);
        mContext.startActivity(intent);
    }
}
