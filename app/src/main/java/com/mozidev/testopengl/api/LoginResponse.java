package com.mozidev.testopengl.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Igor
 * on 11.12.2015
 */
public class LoginResponse implements Serializable{

    @SerializedName("authToken")
    public String authToken;

    @SerializedName("socketUrl")
    public String socketUrl;

    @SerializedName("newDevice")
    public boolean newDevice;

}
