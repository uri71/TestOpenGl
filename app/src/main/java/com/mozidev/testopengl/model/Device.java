package com.mozidev.testopengl.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Igor
 * on 09.12.2015
 */
@Table(name = "Device")
public class Device extends Model {

    @Column(name = "site")
    private String site;

    @Column(name = "organisation")
    private String organisation;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_status")
    private String fileStatus;

    @Column(name = "status")
    private String status;

    @Column(name = "udid")
    private String udid;

    @Column(name = "created_at")
    private long created_at;

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String uuid) {
        this.udid = uuid;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public Device(String site, String organisation, String fileUrl, String status, String udid, long created_at, String fileStatus) {
        setSite(site);
        setOrganisation(organisation);
        setFileUrl(fileUrl);
        setStatus(status);
        setUdid(udid);
        setCreated_at(created_at);
        setFileStatus(fileStatus);
    }


    public Device() {
    }


    public static void clear(){
        new Delete().from(WorkLog.class).execute();
    }

    public static List<Device> getAll(){
        List<Device> list;
        try {
            From from = new Select().from(Device.class);
            if (from.exists()) list = from.execute();
            else return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

}

