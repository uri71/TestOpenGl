package com.mozidev.testopengl.network;

/**
 * Created by y.storchak on 09.12.15.
 */
public class BusEvent {
    public String command;
    public int id;
    public float x;
    public float y;


    public BusEvent(String command) {
        this.command = command;
    }


    public BusEvent(String command, int id, float x, float y) {
        this.command = command;
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
