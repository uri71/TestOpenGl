package com.mozidev.testopengl.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import com.mozidev.testopengl.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.mozidev.testopengl.Constants.*;

/**
 * Created by Igor
 * on 09.12.2015
 */
public class ApiHelper {

    private static SharedPreferences sp;

    private static ApiConnection connection = new Retrofit.Builder()
            .baseUrl(Constants.BASE_API_URL)
            .build()
            .create(ApiConnection.class);

    public static void login(Callback<JSONObject> callback, Context context){
        sp = context.getSharedPreferences("DM", Context.MODE_PRIVATE);
        String udid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Map<String, String> params = new HashMap<>();
        params.put(LOGIN_KEY, sp.getString("login", ""));
        params.put(PASSWORD_KEY, sp.getString("password", ""));
        params.put(UDID_KEY, udid);
        connection.login(params).enqueue(callback);

    }

    public static void checkPermissions(Callback<JSONObject> callback, Context context){
        sp = context.getSharedPreferences("DM", Context.MODE_PRIVATE);
        String udid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Map<String, String> params = new HashMap<>();
        params.put(UDID_KEY, udid);
        params.put(TOKEN_KEY, sp.getString("token", ""));
        connection.checkPermissions(params).enqueue(callback);
    }

    public static void getDevList(Callback<JSONObject> callback, Context context){
        String udid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Map<String, String> params = new HashMap<>();
        params.put(UDID_KEY, udid);
        params.put(TOKEN_KEY, sp.getString("token", ""));
        connection.devList(params).enqueue(callback);
    }

}
