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
    String readyMappingGL = "readyMappingGL";
    String errorMappingGL = "errorMappingGL";
    String mappingUpdateGL = "mappingUpdateGL";
    String mappingStartGL = "mappingStartGL";
    String mappingTimeoutGL = "mappingTimeoutGL";
    String mappingFinishGL = "mappingFinishGL";
}
