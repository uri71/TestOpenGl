package com.mozidev.testopengl.utils;

/**
 * Created by y.storchak on 07.08.15.
 */

import android.content.Context;
import android.os.Environment;
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
import com.mozidev.testopengl.opengl.BaseObject;
import com.mozidev.testopengl.opengl.Figure;
import com.mozidev.testopengl.table.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        Log.d(TAG, "getSocketAuthJson GSON" + s);
        return js;
    }


    @NonNull
    public static JSONObject getAllDeviceStatusJson(Context context, String token, String targetUdid) throws JSONException {
        List <String> udids = Device.getUdids();
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new DeviceStatus(targetUdid, token, Command.deviceStatus, new DataDeviceStatus(udids)));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getAllDeviceStatusJson GSON" + s);
        return js;
    }

    @NonNull
    public static JSONObject getDeviceStatusJson(Context context, String token, String udid, String targetUdid) throws JSONException {
        List <String> udids = new ArrayList<>();
        udids.add(udid);
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new DeviceStatus(targetUdid, token, Command.deviceStatus, new DataDeviceStatus(udids)));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getDeviceStatusJson GSON" + s);
        return js;
    }


    public static JSONObject getMappingInitJson(Context mContext, String token, String name, String url, String targetUdid) throws JSONException {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new MappingInit(token, targetUdid, name, new DataMappingInit(url)));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getMappingInitJson GSON" + s);
        return js;
    }


    public static JSONObject getMappingUpdateJson(Context mContext, String token, String name, int v, float x, float y, String targetUdid) throws JSONException {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new MappingUpdate(token, targetUdid, name, new DataMappingUpdate(v, x, y)));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getMappingUpdateJson GSON" + s);
        return js;
    }


    public static JSONObject getMappingFinishJson(Context mContext, String token, String name, String targetUdid) throws JSONException{
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new MappingFinish(token, targetUdid, Command.mappingFinishGL));
        JSONObject js =  new JSONObject(s);
        Log.d(TAG, "getMappingFinishJson GSON" + js.toString());
        return js;

    }

    public static JSONObject objectToJson(BaseObject object) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonResolution = new JSONArray();
        JSONObject jsonPoints = new JSONObject();
        JSONObject jsonPrimitives = new JSONObject();
        jsonResolution.put(1920);
        jsonResolution.put(1080);
        jsonObject.put("output_camera_resolution", jsonResolution);
        int i = 0;
        for (Figure figure : object.face){
            JSONArray array = new JSONArray();
            for (int x : figure.order) {
                 array.put(x);
            }
            jsonPrimitives.put(String.valueOf(i), array);
            i++;
        }
        jsonObject.put("prims", jsonPrimitives);
        i = 0;
        for (Float[] point : object.points){
            JSONArray array = new JSONArray();
            int j = 0;
            for (double y : point) {
                if (j != 2) {
                    array.put(y);
                    j++;
                }
            }
            jsonPoints.put(String.valueOf(i), array);
            i++;
        }
        jsonObject.put("points", jsonPoints);

        return jsonObject;
    }

    public static BaseObject jsonToObject(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "map.json");
        if(!file.exists()){
            Log.e(TAG, "file " + file.getPath() + " not exist");
        }
        BaseObject object = new BaseObject();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                builder.append(line);
                Log.d(TAG, line);
            }
            br.close();

            JSONObject json = new JSONObject(builder.toString());
            if (json.has("points")){
                JSONObject obj = (JSONObject) json.opt("points");
                for (int i = 0; i < obj.length(); i++ ){
                    JSONArray array;
                    if (obj.has(String.valueOf(i))){
                        array = (JSONArray) obj.get(String.valueOf(i));
                        ArrayList<Float> floats = new ArrayList<>();
                        for (int g = 0; g < array.length(); g++) {
                            if (!array.isNull(g)) {
                                floats.add((Float)(((Double) array.getDouble(g)).floatValue()));
                            }
                        }
                        floats.add((float) 0);
                        Float[] a = new Float[floats.size()];
                        floats.toArray(a);
                        object.points.add(a);
                    } else break;
                }
            }
            if (json.has("prims")){
                JSONObject obj = (JSONObject) json.opt("prims");
                for (int i = 0; i < obj.length(); i++ ){
                    JSONArray array;
                    if (obj.has(String.valueOf(i))){
                        array = (JSONArray) obj.get(String.valueOf(i));
                        Figure f = new Figure();
                        for (int g = 0; g < array.length(); g++) {
                            f.order.add(array.getInt(g));
                            Float[] arr = object.points.get(array.getInt(g));
                            f.vertex.addAll(Arrays.asList(arr));
                        }
                        object.face.add(f);
                    } else break;
                }
            }
//            if (json.has("output_camera_resolution")){
//                JSONArray array = (JSONArray) json.opt("output_camera_resolution");
//            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

}