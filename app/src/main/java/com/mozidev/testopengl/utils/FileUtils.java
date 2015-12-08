package com.mozidev.testopengl.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by y.storchak on 03.12.15.
 */
public class FileUtils {

    private static final String TAG = "FileUtils";


    public static boolean isFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "test.obj");

        if (!file.exists()) {
            return false;
        } else {
            return true;

        }

    }

    public static void convertStringToFile(Context context,  String data) {
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File outDir = new File(root.getAbsolutePath() + File.separator + "mapped_file");
         Writer writer;
        if (!outDir.isDirectory()) {
            outDir.mkdir();
        }
        try {
            if (!outDir.isDirectory()) {
                throw new IOException(
                        "Unable to create directory EZ_time_tracker. Maybe the SD card is mounted?");
            }
            String name = "mapped_file_"+ System.currentTimeMillis()+".obj";
            File outputFile = new File(outDir, name);
            writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(data);
            Toast.makeText(context.getApplicationContext(),
                    "File successfully saved to: " + outputFile.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();
            writer.close();
        } catch (IOException e) {
            Log.w(TAG, e.getMessage(), e);
            Toast.makeText(context, e.getMessage() + " Unable to write to external storage.",
                    Toast.LENGTH_LONG).show();
        }

    }

}
