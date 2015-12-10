package com.mozidev.testopengl.model;

/**
 * Created by y.storchak on 04.08.15.
 */
public class AuthToken {
    private String authToken;
    private String mappingUdid;


    public AuthToken(String authToken, String udid) {
        this.authToken = authToken;
        this.mappingUdid = udid;
    }
}
