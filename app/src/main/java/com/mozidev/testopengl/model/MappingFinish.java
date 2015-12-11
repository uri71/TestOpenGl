package com.mozidev.testopengl.model;


/**
 * Created by y.storchak on 10.12.15.
 */
public class MappingFinish {
    private String authToken;
    private String udid;
    private String name;
   // private String type = "mapping";


    public MappingFinish(String authToken, String udid, String name) {
        this.authToken = authToken;
        this.udid = udid;
        this.name = name;
    }
}
