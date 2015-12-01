package com.mozidev.testopengl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by y.storchak on 30.11.15.
 */
public class Figure {

    List<Float> vertex;
    List<float[]> norm;
    List<float[]> texture;


    public Figure() {
        vertex = new ArrayList<>();
        norm = new ArrayList<>();
        texture = new ArrayList<>();
    }
}
