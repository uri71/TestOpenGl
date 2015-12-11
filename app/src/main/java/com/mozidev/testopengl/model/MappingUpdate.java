package com.mozidev.testopengl.model;


/**
 * Created by y.storchak on 10.12.15.
 */
public class MappingUpdate {
    private String authToken;
    private String udid;
    private String name;
    private DataMappingUpdate data;
   // private String type = "mapping";


    public MappingUpdate(String authToken, String udid, String name, DataMappingUpdate data) {
        this.authToken = authToken;
        this.udid = udid;
        this.name = name;
        this.data = data;
    }
}
