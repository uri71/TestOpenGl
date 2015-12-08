package com.mozidev.testopengl.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by y.storchak on 30.11.15.
 */
public class Base3DObject implements Cloneable {

    private static final String TAG = "Base3DObject";
    public List<Float[]> points;
    public List<float[]> texture;
    public List<float[]> norm;
    public List<Figure> face;
    public List<String> order;
    public String comment;
    public int selectedId = -1;
    public int changedFigureId = -1;
    private String parseredFile;




    public Base3DObject(final Base3DObject object) {


        points = object.points;
        norm = object.norm;
        comment = object.comment;
        texture = object.texture;
        order = object.order;
        selectedId = object.selectedId;
        recalculateFigure();
    }


    public int getSelectedId() {
        return selectedId;
    }


    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }


    public void clearSelected() {
        selectedId = -1;
    }


    public Base3DObject() {
        this.points = new ArrayList<>();
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
        points.add(v);
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
        String[] group = line.split(" ");
        Figure f = new Figure();
        f.orderString = line;
        for (int i = 1; i < group.length; i++) {
            String[] elem = group[i].split("/");
            int p = Integer.valueOf(elem[0]);
            //int n = Integer.valueOf(elem[1]);
            //int t = Integer.valueOf(elem[2]);
            Float[] array = points.get(p - 1);
            f.vertex.addAll(Arrays.asList(array));
            //f.norm.add(norm.get(n - 1));
            //f.texture.add(texture.get(t - 1));
            f.order.add(p);

            Log.d(TAG, f.vertex.toString());
        }
        Log.d(TAG, f.toString());
        face.add(f);
    }


    public void recalculateFigure() {
        if (face == null) {
            face = new ArrayList<>();
        }
        resetF();
    }


    private void resetF() {
        if (selectedId >= 0) {
            for (Figure f : face) {
                if (f.order.contains(selectedId+1)) {
                    f.vertex.clear();
                    for (int i : f.order) {
                        f.vertex.addAll(Arrays.asList(points.get(i - 1)));
                        Log.d(TAG, "reset figure selected id = " + selectedId + " order = " + f.orderString);
                        changedFigureId = face.indexOf(f);
                    }
                }
            }
        } else {
            for (Figure f : face) {
                f.vertex.clear();
                for (int i : f.order) {
                    f.vertex.addAll(Arrays.asList(points.get(i - 1)));
                }
            }
        }
    }


    public void setOrder(String line) {
        if (order == null) {
            order = new ArrayList<>();
        }
        order.add(line);
    }


    public void addComment(String line) {
        this.comment = line;
    }


    @Override
    public Base3DObject clone() {
        try {
            final Base3DObject result = (Base3DObject) super.clone();
            // copy fields that need to be copied here!
            return result;
        }
        catch (final CloneNotSupportedException ex) {
            throw new AssertionError();
        }
    }


    public void setParseredFile(String s) {
        parseredFile = s;
    }


    public String getParseredFile() {
        return parseredFile;
    }


}
