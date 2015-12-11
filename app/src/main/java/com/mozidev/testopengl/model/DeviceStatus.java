package com.mozidev.testopengl.model;

import com.google.gson.annotations.SerializedName;
import com.mozidev.testopengl.network.JsonField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by y.storchak on 05.08.15.
 */
public class DeviceStatus {

    @SerializedName(JsonField.authToken)
    public String token;
    @SerializedName(JsonField.mappingUdid)
    public String mappingUdid;
    @SerializedName(JsonField.name)
    public String name;
    @SerializedName(JsonField.data)
    public  DataDeviceStatus data;


    public DeviceStatus( String token, String mappingUdid, String name,DataDeviceStatus data) {
        this.token = token;
        this.mappingUdid = mappingUdid;
        this.name = name;
        this.data = data;
    }
}
