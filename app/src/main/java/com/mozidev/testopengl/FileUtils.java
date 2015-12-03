package com.mozidev.testopengl;

import android.os.Environment;

import java.io.File;

/**
 * Created by y.storchak on 03.12.15.
 */
public class FileUtils {

    public static boolean isFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "test.obj");

        if (!file.exists()) {
            return false;
        } else {
            return true;

        }

    }

}
