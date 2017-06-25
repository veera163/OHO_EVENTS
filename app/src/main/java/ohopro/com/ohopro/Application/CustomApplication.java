package ohopro.com.ohopro.Application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import ohopro.com.ohopro.services.AccessTokenService;

/**
 * Created by sai on 21-12-2016.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    @Override
    public void onTerminate() {
        stopService(new Intent(this, AccessTokenService.class));
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
