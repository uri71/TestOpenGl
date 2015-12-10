package com.mozidev.testopengl.network;

/**
 * Created by y.storchak on 30.07.15.
 */
public interface SocketEvent {
    String authenticationFailed = "authenticationFailed";
    String authorizationFailed ="authorizationFailed";
    String authenticated = "authenticated";
    String command = "command";
    String BACK_command = "BACK_command";
    String BACK_connect = "BACK_connect";
    String authenticate = "authenticate";
    String authenticateMapping = "device_connecting";
    String status = "status";
    String commandFeed = "command";//the value may be changed
    String mappingStart = "mappingStart";
    String mapping_init_return = "mapping_init_return";
    String mapping_updated = "mapping_updated";
    String finished_mapping = "finished_mapping";
    String mapping_timeout = "mapping_timeout";
    String start_mapping = "start_mapping";
    String ready_mapping = "ready_mapping";
    String error_mapping = "error_mapping";
    String deviceStatus = "deviceStatus";
}
