package com.mozidev.testopengl.model;

import com.google.gson.annotations.SerializedName;
import com.mozidev.testopengl.network.JsonField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by y.storchak on 11.12.15.
 */
public class DataDeviceStatus {

    public DataDeviceStatus(List<String> udids) {
        this.udids = udids;
    }


    @SerializedName(JsonField.udidList)
    public List<String> udids = new ArrayList<>();
}
