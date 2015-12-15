package com.mozidev.testopengl.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.mozidev.testopengl.opengl.BaseObject;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by y.storchak on 07.12.15.
 */
public class OBJUtils {

    private static final String TAG = "OBJUtils";


    public static void create(Context context, BaseObject object){
        StringBuilder builder = new StringBuilder();
        for(Float[] point:object.points){
            builder.append("v " + point[0] + " " + point[1] + " " + point[2] + "\n");
        }
        FileUtils.convertStringToFile(context, builder.toString());

    }


    public static BaseObject parse() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "test.obj");
        if(!file.exists()){
            Log.e(TAG, "file " + file.getPath() + " not exist");
        }
        BaseObject object = new BaseObject();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                if(line.contains("v ")){
                    object.addV(line);
                } else if(line.contains("f ")){
                    object.setOrder(line + "\n");
                    object.addF(line);
                }
                Log.d(TAG, line);
            }
            br.close();
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            Log.d("Parsing", JsonUtils.objectToJson(object).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }
}
