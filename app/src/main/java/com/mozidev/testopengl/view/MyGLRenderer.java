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

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.google.common.collect.ImmutableList;
import com.mozidev.testopengl.model.Base3DObject;
import com.mozidev.testopengl.model.Figure;
import com.mozidev.testopengl.model.Line;
import com.mozidev.testopengl.model.Marker;
import com.mozidev.testopengl.model.Square;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 * <li>{@link GLSurfaceView.Renderer#onSurfaceCreated}</li>
 * <li>{@link GLSurfaceView.Renderer#onDrawFrame}</li>
 * <li>{@link GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    //private Triangle mTriangle;
    private List<Square> mSquare;
    private List<Marker> mMarker;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

    private float mAngle;
    private Base3DObject m3DObject;
    private Line mLines;
    private float ratio;


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // mTriangle = new Triangle();
        // if(mSquare == null)mSquare   = new ArrayList<>();
        createFigure();
        createMarkers();
    }


    @Override
    public void onDrawFrame(GL10 unused) {
        Log.d(TAG, "onDrawFrame");
        float[] scratch = new float[16];

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw square
        //
        if (mSquare != null) {
            // if(mSquare.size() >2)mSquare.remove(mSquare.size()-1);
            for (Square square : mSquare) {
                if (square != null) {
                    square.draw(mMVPMatrix);
                }
            }
        }

        if (mMarker != null) {
            // if(mSquare.size() >2)mSquare.remove(mSquare.size()-1);
            for (Marker marker : mMarker) {
                if (marker != null) {
                    marker.draw(mMVPMatrix);
                }
            }
        }

        if (mLines != null) {
            mLines.draw(mMVPMatrix);
        }


    }


    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }


    /**
     * Utility method for compiling a OpenGL shader.
     * <p/>
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type       - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     * <p/>
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }


    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mAngle;
    }


    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }


    public void createFigure() {
        if (m3DObject == null) {
            Log.e(TAG, "m3DObject == null");
            return;
        }
        final List<Figure> list = ImmutableList.copyOf(m3DObject.face);

        if (mSquare != null) {
            // mSquare.clear();
        } else {
            mSquare = new ArrayList<>();
        }

        for (Figure f : list) {

            float[] vertex = new float[f.vertex.size()];
            for (int i = 0; i < vertex.length - 1; i++) {
                vertex[i] = f.vertex.get(i);
            }
            mSquare.add(list.indexOf(f), new Square(vertex));
        }
    }


    public void addFigure() {
        if (m3DObject == null) {
            Log.e(TAG, "m3DObject == null");
            return;
        }
        final List<Figure> list = ImmutableList.copyOf(m3DObject.face);

        if (mSquare == null) {
            mSquare = new ArrayList<>();
        }

        for (Figure f : list) {
            if (m3DObject.changedFigureId == list.indexOf(f)) {

                float[] vertex = new float[f.vertex.size()];
                for (int i = 0; i < vertex.length - 1; i++) {
                    vertex[i] = f.vertex.get(i);
                }
                mSquare.set(list.indexOf(f), new Square(vertex));
            }
        }
    }


    public void setObject(Base3DObject object) {
        m3DObject = object;
    }


    public void resetObject(final Base3DObject object) {
        Log.d(TAG, "resetObject");
        if (object != null) {
            m3DObject = object;
            addFigure();
            createMarkers();
            setLine(true, m3DObject.selectedId);
        }
    }


    private void createMarkers() {
        if (m3DObject == null) {
            Log.e(TAG, "m3DObject == null");
            return;
        }
        if (mMarker != null) {
            mMarker.clear();
        } else {
            mMarker = new ArrayList<>();
        }

        List<Float[]> vertex = ImmutableList.copyOf(m3DObject.points);
        for (Float[] f : vertex) {
            Marker marker;

            marker = new Marker(f, false);
            mMarker.add(marker);
        }
    }


    public void setLine(boolean selected, int id) {
        int selectedId = id;
        Log.d(TAG, "setLine = " + selected + " id = " + selectedId);
        if (selected && selectedId < 0) {
            return;
        }
        if (selected) {
            Float[] vertex = m3DObject.points.get(selectedId);
            mLines = new Line(vertex, ratio);
        } else {
            mLines = null;
        }
    }


    public void stop() {
        if (mSquare != null) {
            mSquare.clear();
            mSquare = null;
        }
        if (mMarker != null) {
            mMarker.clear();
            mMarker = null;
        }
        mLines = null;
    }
}