package com.mozidev.testopengl.network;

/**
 * Created by y.storchak on 30.07.15.
 */
public interface Command {
    String requestPhoto = "requestPhoto";
    String playVideo = "playVideo";
    String report = "report";
    String command = "command";
    String logFile = "logFile";
    String statistics = "statistics";
    String showImage = "showImage";
    String showImageFinish = "showImageFinish";
    String mappingInit = "mappingInit";
    String mapping_init_return = "mapping_init_return";
    String mapping_updated = "mapping_updated";
    String mapping_finished = "finished_mapping";
    String mapping_timeout = "mapping_timeout";
    String disconnectDevice = "disconnectDevice";
    String projectorUpdate = "projectorUpdate";

    //this command for sending ReguestContainer from DownloadFragment to ConnectService. For socket connection don't used
    String downloadVideo = "downloadVideo";
    String playDownloadedVideo = "playDownloadedVideo";
    String downloadingVideoFail = "downloadingVideoFail";


    String rebootDevice = "rebootDevice"; //change it!
    String restartApp = "restartApp"; //change it!
    String reconnect = "reconnect";
    String restartKioskService = "restartKioskService";
    String restartSocketService = "restartSocketService";
    String deviceStatus = "deviceStatus";
    String readyMapping = "readyMapping";
    String errorMapping = "errorMapping";
}
