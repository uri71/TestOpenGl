package com.mozidev.testopengl.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor
 * on 14.12.2015
 */
public class ScreenUtils {

    public static List<Integer> getScreenSizePixels(Context context) {
        return PrefUtils.getScreenSize(context);
    }

    public static void setScreenSize(Activity activity){
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        List<Integer> size = new ArrayList<>();
        size.add(point.x);
        size.add(point.y);
        PrefUtils.setScreenSize(activity, size);
    }

}
