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
    @SerializedName(JsonField.udidList)
    public List<String> udids = new ArrayList<>();


    public DeviceStatus( String token, String mappingUdid, List<String> udids) {
        this.token = token;
        this.mappingUdid = mappingUdid;
        this.udids = udids;
    }
}
