package com.mozidev.testopengl.api;

import com.squareup.okhttp.ResponseBody;

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

    @POST("/api/login")
    Call<ResponseBody> login(@QueryMap Map<String, String> params);

    @GET("/api/check")
    Call<ResponseBody> checkPermissions(@QueryMap Map<String, String> params);

    @GET("/api/list")
    Call<ResponseBody> devList(@QueryMap Map<String, String> params);

}
