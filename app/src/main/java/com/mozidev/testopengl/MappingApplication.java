package com.mozidev.testopengl;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;

import com.activeandroid.ActiveAndroid;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by y.storchak on 09.12.15.
 */
public class MappingApplication extends com.activeandroid.app.Application{
    private final String TAG = "DisplayMapper";
    private static MappingApplication instatnce;
    private TypefaceCollection mTypefaceCollection;


    public static MappingApplication getInstance() {
        if(instatnce == null) instatnce = new MappingApplication();
        return instatnce;
    }


    @Override
    public void onCreate() {
        super.onCreate();
       // ActiveAndroid.initialize(this, BuildConfig.DEBUG);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        mTypefaceCollection = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(),
                        "font/akkurat-light.otf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(),
                        "font/akkurat-bold.otf"))
                .create();
        TypefaceHelper.init(mTypefaceCollection);
    }
}
