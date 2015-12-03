package com.mozidev.testopengl;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by y.storchak on 30.11.15.
 */
public class ObjParser {

    private static final String TAG = "ObjParser";


    public Base3DObject parse() {
         File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "test.obj");
        if(!file.exists()){
            Log.e(TAG, "file "+ file.getPath() + " not exist");
        }

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
                } else if(line.contains("vn ")){
                    object.addVn(line);
                } else if(line.contains("f ")){
                    object.setOrder(line);
                    object.addF(line);
                }
                Log.d(TAG, line);
            }
            br.close();
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return object;

    }

}
