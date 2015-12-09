package com.mozidev.testopengl.opengl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by y.storchak on 30.11.15.
 */
public class Figure {

    public List<Float> vertex;
    public List<float[]> norm;
    public List<float[]> texture;
    public String orderString;
    public List<Integer> order;


    public Figure() {
        vertex = new ArrayList<>();
        norm = new ArrayList<>();
        texture = new ArrayList<>();
        order = new ArrayList<>();
    }
}
