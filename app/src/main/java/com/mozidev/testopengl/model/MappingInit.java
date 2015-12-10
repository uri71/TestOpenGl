package com.mozidev.testopengl.model;


/**
 * Created by y.storchak on 10.12.15.
 */
public class MappingInit {
    private String authToken;
    private String mappingUdid;
    private String udid;
    private String url;
   // private String type = "mapping";


    public MappingInit(String authToken, String mappingUdid, String udid, String url) {
        this.authToken = authToken;
        this.mappingUdid = mappingUdid;
        this.udid = udid;
        this.url = url;
    }
}
