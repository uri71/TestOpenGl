package com.mozidev.testopengl;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by y.storchak on 30.11.15.
 */
public class Base3DObject {

    private static final String TAG = "Base3DObject";
    List<Float[]> vertex;
    List<float[]> texture;
    List<float[]> norm;
    List<Figure> face;
    String comment;
    private String order;


    public Base3DObject() {
        this.vertex = new ArrayList<>();
        this.texture = new ArrayList<>();
        this.norm = new ArrayList<>();
        this.face = new ArrayList<>();
    }


    public void addV(String line) {
        String[] split = line.split(" ");
        Float[] v = new Float[split.length - 1];
        for (int i = 1; i < split.length; i++) {
            v[i - 1] = Float.parseFloat(split[i]);
        }
        vertex.add(v);
    }


    public void addVt(String line) {
        String[] split = line.split(" ");
        float[] vt = new float[split.length - 1];
        for (int i = 1; i < split.length; i++) {
            vt[i - 1] = Float.parseFloat(split[i]);
        }
        texture.add(vt);
    }


    public void addVn(String line) {
        String[] split = line.split(" ");
        float[] vn = new float[split.length - 1];
        for (int i = 1; i < split.length; i++) {
            vn[i - 1] = Float.parseFloat(split[i]);
        }
        norm.add(vn);
    }


    public void addF(String line) {
        order = line;
        String[] group = line.split(" ");
        Figure f = new Figure();
        for (int i = 1; i < group.length; i++) {
            String[] elem = group[i].split("/");
            int p = Integer.valueOf(elem[0]);
            int n = Integer.valueOf(elem[1]);
            int t = Integer.valueOf(elem[2]);
            Float[] array = vertex.get(p - 1);
            f.vertex.addAll(Arrays.asList(array));
            f.norm.add(norm.get(n - 1));
            f.texture.add(texture.get(t - 1));
            Log.d(TAG, f.vertex.toString());
        }
        Log.d(TAG, f.toString());
        face.add(f);
    }


    public void reset(){
        if(!TextUtils.isEmpty(order)){
            face.clear();
            addF(order);
        }
    }


    public void addComment(String line) {
        this.comment = line;
    }
}
