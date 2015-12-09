package com.mozidev.testopengl.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by y.storchak on 09.12.15.
 */
public class BaseFragment extends Fragment {
    protected boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
