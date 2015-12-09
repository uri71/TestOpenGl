package com.mozidev.testopengl;

import android.os.Environment;
import android.util.Log;

import com.mozidev.testopengl.opengl.Base3DObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
            object.setParsedFile(builder.toString());
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return object;

    }

}
