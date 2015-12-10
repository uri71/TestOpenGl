package com.mozidev.testopengl.network;

/**
 * Created by y.storchak on 30.07.15.
 */
public class Command {
    public static final String requestPhoto = "requestPhoto";
    public static final String playVideo = "playVideo";
    public static final String report = "report";
    public static final String command = "command";
    public static final String logFile = "logFile";
    public static final String statistics = "statistics";
    public static final String showImage = "showImage";
    public static final String showImageFinish = "showImageFinish";
    public static final String mappingInit = "mappingInit";
    public static final String mapping_init_return = "mapping_init_return";
    public static final String mapping_updated = "mapping_updated";
    public static final String mapping_finished = "finished_mapping";
    public static final String mapping_timeout = "mapping_timeout";
    public static final String disconnectDevice = "disconnectDevice";
    public static final String projectorUpdate = "projectorUpdate";

    //this command for sending ReguestContainer from DownloadFragment to ConnectService. For socket connection don't used
    public static final String downloadVideo = "downloadVideo";
    public static final String playDownloadedVideo = "playDownloadedVideo";
    public static final String downloadingVideoFail = "downloadingVideoFail";


    public static final String rebootDevice = "rebootDevice"; //change it!
    public static final String restartApp = "restartApp"; //change it!
    public static final String reconnect = "reconnect";
    public static final String restartKioskService = "restartKioskService";
    public static final String restartSocketService = "restartSocketService";
    public static final String deviceStatus = "deviceStatus";
    public static final String readyMapping = "readyMapping";
    public static final String errorMapping = "errorMapping";
}
