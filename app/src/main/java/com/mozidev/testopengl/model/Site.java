package com.mozidev.testopengl.model;

import java.io.Serializable;

/**
 * Created by y.storchak on 04.12.15.
 */
public class Site implements Serializable{

    public String name;
    public String udid;
    public String organisation;
    public String created;
    public boolean status;
    public boolean isFile;


    public Site(String name, String udid, String organisation, boolean status, boolean isFile, String time) {
        this.name = name;
        this.udid = udid;
        this.organisation = organisation;
        this.status = status;
        this.isFile = isFile;
        created = time;
    }


    public Site() {
    }
}
