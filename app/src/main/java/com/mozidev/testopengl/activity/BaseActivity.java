package com.mozidev.testopengl.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by y.storchak on 09.12.15.
 */
public class BaseActivity extends AppCompatActivity {
    protected boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
