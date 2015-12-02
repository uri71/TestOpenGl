/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mozidev.testopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.rits.cloning.Cloner;

import java.util.Arrays;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private static final String TAG = "MyGLSurfaceView";
    private final MyGLRenderer mRenderer;
    private Base3DObject mObjects;
    private float res_x;
    private float res_y;
    private double DISTANCE;
    private boolean isMoving;
    private int areaMoving = 350;
    private int areaTouch = 250;
    private long time;
    private long DELAY = 100;


    public MyGLSurfaceView(Context context, Base3DObject object) {
        super(context);


        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        mRenderer.setObject(object);
        mObjects = object;
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    //private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;


   /* @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        res_x = getWidth();
        res_y = getHeight();
        DISTANCE = 150 / res_x;
        float x = e.getX();
        float y = e.getY();
        checkTouchEvent(x, y);
        Log.d(TAG, "onTouchEvent x = " + x + ";  y = " + y + " action = " + e.getAction());

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        mRenderer.resetObject(mObjects);
                        requestRender();
                    }
                });
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }*/


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        res_x = getWidth();
        res_y = getHeight();

        DISTANCE = /*ScreenUtils.dpToPx(48) */(isMoving ? areaMoving : areaTouch) / res_x;
        float x = e.getX();
        float y = e.getY();
        boolean selected = checkTouchEvent(x, y);
        Log.d(TAG, "onTouchEvent x = " + x + ";  y = " + y + " action = " + e.getAction());
        //Log.d(TAG, "Checked point: " + Arrays.toString(checkedPoint));
        /*float dx = (x - res_x / 2) / (res_x / 2);
        float dy = ((res_y / 2) - y) / (res_y / 2);
        dx = dx * (res_x / res_y);
        final Float [] test = new Float[]{
                -dx, dy, 0f
        };
        queueEvent(new Runnable() {
            @Override
            public void run() {
                //Base3DObject copy = new Base3DObject(mObjects);
                mRenderer.test(test);
                requestRender();
            }
        });*/

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isMoving) {
                    if (time == 0) {
                        time = System.currentTimeMillis();
                    } else if (System.currentTimeMillis() - time > DELAY) {
                        time = System.currentTimeMillis();
                        queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                //Base3DObject copy = new Base3DObject(mObjects);
                                mRenderer.resetObject(mObjects.clone());
                                requestRender();
                            }
                        });
                    }
                }
                break;

            case MotionEvent.ACTION_DOWN:
                if (selected && !isMoving) {
                    isMoving = true;
                    queueEvent(new Runnable() {
                        @Override
                        public void run() {
                           // Base3DObject copy = new Base3DObject(mObjects);
                            /*mRenderer.resetObject(mObjects.clone());
                            requestRender();*/
                        }
                    });

                } else if (!selected) {
                    isMoving = false;
                }
                break;
        }

        return true;
    }




   /* private void checkTouchEvent(float x, float y) {
        float dx = (x - res_x / 2) / (res_x / 2);
        float dy = ((res_y / 2) - y) / (res_y / 2);
        dx = dx * (res_x / res_y);
        Log.d(TAG, "dx = " + dx + " dy = " + dy);
        double minHypot = 0;
        Float[] nearbyPoint;

        for (Float[] vertex : mObjects.vertex) {
            double hypot = Math.hypot(vertex[0] - dx, vertex[1] - dy);
            Log.d(TAG, "hypot = " + hypot + "distance = " + DISTANCE);

            if (hypot < DISTANCE) {
                if (minHypot == 0) {
                    minHypot = hypot;
                    nearbyPoint = vertex;
                } else if (minHypot > hypot) {
                    minHypot = hypot;
                    nearbyPoint = vertex;
                }
                Log.d(TAG, "MIN = " + minHypot);
            }
        }
    }*/


    private boolean checkTouchEvent(float x, float y) {
        float dx = -(x - res_x / 2) / (res_x / 2);
        float dy = ((res_y / 2) - y) / (res_y / 2);
        dx = dx * (res_x / res_y);
        Log.d(TAG, "dx = " + dx + " dy = " + dy);
        double minHypot = -1;
        Float[] nearbyPoint = null;
        int idMin = -1;

        for (Float[] vertex : mObjects.vertex) {
            double hypot = Math.hypot(vertex[0] - dx, vertex[1] - dy);
            //Log.d(TAG, "hypot = " + hypot + "distance = " + DISTANCE);

            if (hypot < DISTANCE) {
                if (minHypot <= 0) {
                    minHypot = hypot;
                    nearbyPoint = vertex;
                    idMin = mObjects.vertex.indexOf(vertex);
                    Log.d(TAG, "Nearby Point = " + Arrays.toString(nearbyPoint));
                } else if (minHypot > hypot) {
                    minHypot = hypot;
                    nearbyPoint = vertex;
                    idMin = mObjects.vertex.indexOf(vertex);
                    Log.d(TAG, "Nearby Point = " + Arrays.toString(nearbyPoint));
                }

                Log.d(TAG, "MIN = " + minHypot);
            }
            if (idMin >= 0) {
                Float[] newVertex = new Float[] {
                        dx, dy, 0f
                };
                mObjects.vertex.set(idMin, newVertex);
                mObjects.setSelectedId(idMin);
                mObjects.recalculateFigure();
                Log.d(TAG, "checkTouchEvent idMin = " + idMin + " newVertex = " + Arrays.toString(mObjects.vertex.get(idMin)));
            }
        }
        return idMin >= 0;
    }
}
