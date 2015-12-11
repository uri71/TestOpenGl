package com.mozidev.testopengl.model;

import com.google.gson.annotations.SerializedName;
import com.mozidev.testopengl.network.JsonField;

/**
 * Created by y.storchak on 11.12.15.
 */
public class DataMappingUpdate {
    @SerializedName(JsonField.v)
    public int v;
    @SerializedName(JsonField.x)
    public float x;
    @SerializedName(JsonField.y)
    public float y;


    public DataMappingUpdate(int v, float x, float y) {
        this.v = v;
        this.x = x;
        this.y = y;
    }
}
