package com.mozidev.testopengl.model;


/**
 * Created by y.storchak on 10.12.15.
 */
public class MappingInit {
    private String authToken;
    private String udid;
    private String name;
    private DataMappingInit data;
   // private String type = "mapping";


    public MappingInit(String authToken,  String udid, String name, DataMappingInit data) {
        this.authToken = authToken;
        this.udid = udid;
        this.name = name;
        this.data = data;
    }
}
