package com.mozidev.testopengl.api;

import org.json.JSONObject;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by Igor
 * on 09.12.2015
 */
public interface ApiConnection {

    @POST("/login")
    Call<JSONObject> login(@QueryMap Map<String, String> params);

    @GET("/check")
    Call<JSONObject> checkPermissions(@QueryMap Map<String, String> params);

    @GET("/list")
    Call<JSONObject> devList(@QueryMap Map<String, String> params);

}
