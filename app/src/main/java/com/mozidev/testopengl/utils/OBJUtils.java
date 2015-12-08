package com.mozidev.testopengl.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.mozidev.testopengl.model.Base3DObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by y.storchak on 07.12.15.
 */
public class OBJUtils {

    private static final String TAG = "OBJUtils";


    public static void create(Context context, Base3DObject object){
        StringBuilder builder = new StringBuilder();
        builder.append(object.comment + "\n");
        for(Float[] point:object.points){
            builder.append("v " + point[0] + " " + point[1] + " " + point[2] + "\n");
        }
        builder.append(object.getParseredFile());
        FileUtils.convertStringToFile(context, builder.toString());

    }


    public Base3DObject parse() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "test.obj");
        if(!file.exists()){
            Log.e(TAG, "file " + file.getPath() + " not exist");
        }
        StringBuilder builder = new StringBuilder();
        Base3DObject object = new Base3DObject();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("# ")) {
                    object.addComment(line);
                } else if(line.contains("v ")){
                    object.addV(line);
                } else if (line.contains("vt ")) {
                    object.addVt(line);
                    builder.append(line + "\n");
                } else if(line.contains("vn ")){
                    object.addVn(line);
                    builder.append(line + "\n");
                } else if(line.contains("f ")){
                    object.setOrder(line + "\n");
                    object.addF(line);
                    builder.append(line + "\n");
                }
                Log.d(TAG, line);
            }
            br.close();
            object.setParseredFile(builder.toString());
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return object;

    }
}
