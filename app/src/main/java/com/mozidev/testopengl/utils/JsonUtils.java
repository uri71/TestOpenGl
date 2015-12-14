package com.mozidev.testopengl.utils;

/**
 * Created by y.storchak on 07.08.15.
 */

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mozidev.testopengl.model.AuthToken;
import com.mozidev.testopengl.model.DataDeviceStatus;
import com.mozidev.testopengl.model.DataMappingInit;
import com.mozidev.testopengl.model.DataMappingUpdate;
import com.mozidev.testopengl.model.DeviceStatus;
import com.mozidev.testopengl.model.MappingFinish;
import com.mozidev.testopengl.model.MappingInit;
import com.mozidev.testopengl.model.MappingUpdate;
import com.mozidev.testopengl.network.Command;
import com.mozidev.testopengl.table.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static final String TAG = "JsonUtils";


    public static Object toJSON(Object object) throws JSONException {
        if (object instanceof Map) {
            JSONObject json = new JSONObject();
            Map map = (Map) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable)object)) {
                json.put(value);
            }
            return json;
        } else {
            return object;
        }
    }


    public static boolean isEmptyObject(JSONObject object) {
        return object.names() == null;
    }


    public static Map<String, Object> getMap(JSONObject object, String key) throws JSONException {
        return toMap(object.getJSONObject(key));
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }


    public static List toList(JSONArray array) throws JSONException {
        if(array == null || array.length() == 0) return  null;
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }


    private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }


    @NonNull
    public static JSONObject getSocketAuthJson(Context context, String token, String targetUdid) throws JSONException {

        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new AuthToken(token, targetUdid));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getSocketAuthJson GSON" + s.toString());
        return js;
    }


    @NonNull
    public static JSONObject getAllDeviceStatusJson(Context context, String token, String targetUdid) throws JSONException {
        List <String> udids = Device.getUdids();
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new DeviceStatus(targetUdid, token, Command.deviceStatus, new DataDeviceStatus(udids)));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getAllDeviceStatusJson GSON" + s.toString());
        return js;
    }

    @NonNull
    public static JSONObject getDeviceStatusJson(Context context, String token, String udid, String targetUdid) throws JSONException {
        List <String> udids = new ArrayList<>();
        udids.add(udid);
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new DeviceStatus(targetUdid, token, Command.deviceStatus, new DataDeviceStatus(udids)));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getDeviceStatusJson GSON" + s.toString());
        return js;
    }


    public static JSONObject getMappingInitJson(Context mContext, String token, String name, String url, String targetUdid) throws JSONException {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new MappingInit(token, targetUdid, name, new DataMappingInit(url)));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getMappingInitJson GSON" + s.toString());
        return js;
    }


    public static JSONObject getMappingUpdateJson(Context mContext, String token, String name, int v, float x, float y, String targetUdid) throws JSONException {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new MappingUpdate(token, targetUdid, name, new DataMappingUpdate(v, x, y)));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getMappingUpdateJson GSON" + s.toString());
        return js;
    }


    public static JSONObject getMappingFinishJson(Context mContext, String token, String name, String targetUdid) throws JSONException{
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new MappingFinish(token, targetUdid, Command.mappingFinishGL));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getMappingFinishJson GSON" + js.toString());
        return js;
    }
}