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
package com.mozidev.testopengl.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mozidev.testopengl.CreatorObjFile;
import com.mozidev.testopengl.R;
import com.mozidev.testopengl.model.Base3DObject;
import com.mozidev.testopengl.service.SocketService;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView implements View.OnClickListener {

    private static final String TAG = "MyGLSurfaceView";
    private static final float SHIFT_STEP = 0.005f;
    private MyGLRenderer mRenderer = null;
    private Base3DObject mObjects;
    private float res_x;
    private float res_y;
    private double DISTANCE;
    private boolean isSelected;
    private int areaMoving = 350;
    private int areaTouch = 250;
    private long time;
    private long DELAY = 100;


    public MyGLSurfaceView(Context context, AttributeSet atribs) {
        super(context, atribs);
    }


    public void init(Base3DObject object) {

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


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        res_x = getWidth();
        res_y = getHeight();

        DISTANCE = /*ScreenUtils.dpToPx(48) */(isSelected ? areaMoving : areaTouch) / res_x;
        final float x = e.getX();
        final float y = e.getY();

        // Log.d(TAG, "onTouchEvent x = " + x + ";  y = " + y + " action = " + e.getAction());


        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                boolean selected = checkTouchEvent(x, y);
                if (isSelected && !selected) {
                    isSelected = false;
                    queueEvent(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "MotionEvent.ACTION_DOWN hide marker");
                            mRenderer.setLine(false, 0);
                            requestRender();
                        }
                    });
                    return true;
                }
                if (!isSelected && selected) {
                    isSelected = true;
                    queueEvent(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "MotionEvent.ACTION_DOWN show marker");
                            mRenderer.setLine(true, mObjects.selectedId);
                            requestRender();
                        }
                    });
                    //return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSelected) {

                    if (time == 0) {
                        time = System.currentTimeMillis();
                    } else if (System.currentTimeMillis() - time > DELAY) {
                        time = System.currentTimeMillis();
                        checkTouchEvent(x, y);
                        Log.d(TAG, "MotionEvent.ACTION_MOVE movie objects");
                        queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                mRenderer.resetObject(mObjects);
                                requestRender();
                            }
                        });
                    }
                }
                break;
        }

        return true;
    }


    private boolean checkTouchEvent(float x, float y) {
        float dx = -(x - res_x / 2) / (res_x / 2);
        float dy = ((res_y / 2) - y) / (res_y / 2);
        dx = dx * (res_x / res_y);
        if (isSelected) {
            int id = mObjects.selectedId;
            if (id < 0) {
                return false;
            }
            Float[] selectedVertex = mObjects.points.get(id);
            if (Math.hypot(selectedVertex[0] - dx, selectedVertex[1] - dy) < DISTANCE) {
                Float[] newVertex = new Float[] {
                        dx, dy, 0f
                };
                mObjects.points.set(id, newVertex);
                mObjects.recalculateFigure();
                Log.d(TAG, "checkTouchEvent isselected = " + isSelected + "idMin = " + id);
                return true;
            } else {
                mObjects.clearSelected();
                Log.d(TAG, "checkTouchEvent clearSelected isselected = " + isSelected);
                return false;
            }

        } else {

            Log.d(TAG, "dx = " + dx + " dy = " + dy);
            double minHypot = -1;
            int idMin = -1;

            for (Float[] vertex : mObjects.points) {
                double hypot = Math.hypot(vertex[0] - dx, vertex[1] - dy);
                //Log.d(TAG, "hypot = " + hypot + "distance = " + DISTANCE);

                if (hypot < DISTANCE) {
                    if (minHypot <= 0) {
                        minHypot = hypot;
                        idMin = mObjects.points.indexOf(vertex);
                        // Log.d(TAG, "Nearby Point = " + Arrays.toString(nearbyPoint));
                    } else if (minHypot > hypot) {
                        minHypot = hypot;
                        idMin = mObjects.points.indexOf(vertex);
                        //Log.d(TAG, "Nearby Point = " + Arrays.toString(nearbyPoint));
                    }

                    //Log.d(TAG, "MIN = " + minHypot);
                }

            }
            if (idMin >= 0) {
                Float[] newVertex = new Float[] {
                        dx, dy, 0f
                };

                if (isSelected) {
                    //mObjects.vertex.set(idMin, newVertex);
                    //mObjects.recalculateFigure();
                } else {
                    mObjects.setSelectedId(idMin);
                }
                Log.d(TAG, "checkTouchEvent idMin = " + idMin);
            } else {
                mObjects.clearSelected();
                Log.d(TAG, "checkTouchEvent clearSelected");
            }
            return idMin >= 0;
        }
    }


    @Override
    public void onClick(View v) {


        float dx = 0;
        float dy = 0;
        switch (v.getId()) {
            case R.id.button_up:
                dy += SHIFT_STEP;
                Log.d("OnClickListener", "button up clicked!");
                break;
            case R.id.button_down:
                dy -= SHIFT_STEP;
                Log.d("OnClickListener", "button down clicked!");
                break;
            case R.id.button_left:
                dx += SHIFT_STEP;
                Log.d("OnClickListener", "button left clicked!");
                break;
            case R.id.button_right:
                dx -= SHIFT_STEP;
                Log.d("OnClickListener", "button right clicked!");
                break;
            case R.id.button_save:
                Log.d("OnClickListener", "button right clicked!");
                showDialog();
                return;
        }
        if (!isSelected) {
            if (dx + dy != 0) {
                Toast.makeText(getContext(), "Select a point please", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        int selectedId = mObjects.selectedId;

        Float[] newVertex = mObjects.points.get(selectedId);
        newVertex[0] += dx;
        newVertex[1] += dy;
        mObjects.points.set(selectedId, newVertex);
        mObjects.recalculateFigure();
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mRenderer.resetObject(mObjects);
                requestRender();
            }
        });
        Log.d(TAG, "checkTouchEvent isselected = " + isSelected + "idMin = " + selectedId);
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                saveMapping();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setMessage("Are you sure to save changes and finish mapping?").show();
    }


    private void saveMapping() {
       // mRenderer.stop();
        getContext().stopService(new Intent(this.getContext(), SocketService.class));
        new CreatorObjFile().create(getContext(), mObjects);

    }


    @Override
    public void onPause() {
        super.onPause();
        mRenderer.stop();
    }
}
