package com.mozidev.testopengl.network;

/**
 * Created by y.storchak on 30.07.15.
 */
public class SocketEvent {
    public static String authenticationFailed = "authenticationFailed";
    public static String authorizationFailed ="authorizationFailed";
    public static String authenticated = "authenticated";
    public static String command = "command";
    public static String BACK_command = "BACK_command";
    public static String BACK_connect = "BACK_connect";
    public static String authenticate = "authenticate";
    public static String authenticateMapping = "device_connecting";
    public static String status = "status";
    public static String commandFeed = "command";//the value may be changed
    public static final String mappingStart = "mappingStart";
    public static final String mapping_init_return = "mapping_init_return";
    public static final String mapping_updated = "mapping_updated";
    public static final String finished_mapping = "finished_mapping";
    public static final String mapping_timeout = "mapping_timeout";
    public static final String start_mapping = "start_mapping";
    public static final String ready_mapping = "ready_mapping";
    public static final String error_mapping = "error_mapping";
    public static final String deviceStatus = "deviceStatus";
}
