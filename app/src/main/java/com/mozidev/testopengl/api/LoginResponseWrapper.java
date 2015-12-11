package com.mozidev.testopengl.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Igor
 * on 11.12.2015
 */
public class LoginResponseWrapper implements Serializable {

    @SerializedName("isError")
    public boolean isError;

    @SerializedName("errorMsg")
    public String errorMessage;

    @SerializedName("data")
    public LoginResponse data;

}
