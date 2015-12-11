package com.mozidev.testopengl.model;

import com.google.gson.annotations.SerializedName;
import com.mozidev.testopengl.network.JsonField;

/**
 * Created by y.storchak on 11.12.15.
 */
public class DataMappingInit {

    public DataMappingInit(String objFileUrl) {
        this.objFileUrl = objFileUrl;
    }


    @SerializedName(JsonField.objFileUrl)
    public String objFileUrl;

}
