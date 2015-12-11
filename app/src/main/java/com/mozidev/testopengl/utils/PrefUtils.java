package com.mozidev.testopengl.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.mozidev.testopengl.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by y.storchak on 14.07.15.
 */
public class PrefUtils {


    public static void setFirstRun(Context context, boolean b){
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().putBoolean(Constants.PREFS_FIRST_RUN, b).commit();
    }


    public static boolean isFirstRun (Context context){
        return context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getBoolean(Constants.PREFS_FIRST_RUN, true);
    }

    public static void setToken(Context context, String token){
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().putString(Constants.PREFS_TOKEN, token).commit();
    }


    public static String getToken (Context context){
        return context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.PREFS_TOKEN, "");
    }


    public static void setSocketUrl(Context context, String url){
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().putString(Constants.PREFS_SOCKET_URL, url).commit();
    }


    public static String getSocketUrl (Context context){
        return context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.PREFS_SOCKET_URL, "");
    }


    public static void setWIFIStrength(Context context, int signalStrangth) {
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().putInt(Constants.PREFS_WIFI_STRENGTH, signalStrangth).commit();
    }

    public static int getWIFIStrength(Context context) {
        return context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getInt(Constants.PREFS_WIFI_STRENGTH, 0);
    }


    public static void saveJson(Context context, JSONObject jsonObject) {
        String strJson = jsonObject.toString();
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().putString(Constants.PREFS_JSON, strJson).commit();
    }

    public static void clearJson(Context context) {
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().remove(Constants.PREFS_JSON).commit();
    }


    public static JSONObject getJson(Context context) {
        String strJson = context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.PREFS_JSON, "");
        try {
            return new JSONObject(strJson);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void saveDeviceId(Context context, int device_id) {
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().putInt(Constants.PREFS_DEVICE_ID, device_id).commit();
    }


    public static int getDeviceId(Context context) {
        return context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getInt(Constants.PREFS_DEVICE_ID, 0);
    }




    public static void setDeviceName(Context context, String devCustomName) {
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().putString(Constants.PREFS_DEVICE_NAME, devCustomName).commit();
    }


    public static String getDeviceName(Context context) {
        return context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.PREFS_DEVICE_NAME, "");
    }


    public static void setHost(Context context, String host) {
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().putString(Constants.PREFS_HOST, host).commit();
    }


    public static void setPort(Context context, int port) {
        context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .edit().putInt(Constants.PREFS_PORT, port).commit();
    }


    public static int getPort(Context context) {
        return context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getInt(Constants.PREFS_PORT, 0);
    }


    public static String getHost(Context context) {
        return context.getApplicationContext().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.PREFS_HOST, "");
    }





}
