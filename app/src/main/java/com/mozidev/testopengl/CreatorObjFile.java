package com.mozidev.testopengl;

import android.content.Context;

import com.mozidev.testopengl.opengl.BaseObject;
import com.mozidev.testopengl.utils.FileUtils;

/**
 * Created by y.storchak on 04.12.15.
 */
public class CreatorObjFile {

    public CreatorObjFile() {
    }

    public void create(Context context, BaseObject object){
        StringBuilder builder = new StringBuilder();
        for(Float[] point:object.points){
            builder.append("v " + point[0] + " " + point[1] + " " + point[2] + "\n");
        }
        FileUtils.convertStringToFile(context, builder.toString());

    }
}
