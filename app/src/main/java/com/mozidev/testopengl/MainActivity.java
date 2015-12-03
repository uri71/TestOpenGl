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

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.mozidev.testopengl.service.DownloadService;

public class MainActivity extends Activity implements View.OnClickListener {

    private MyGLSurfaceView mGLView;
    Base3DObject object;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        ObjParser parser = new ObjParser();
        object = parser.parse();

        mGLView = (MyGLSurfaceView)findViewById(R.id.gl_view);
        mGLView.init(object);

        ImageButton ib1 = (ImageButton)findViewById(R.id.button_up);
        ImageButton ib2 = (ImageButton)findViewById(R.id.button_down);
        ImageButton ib3 = (ImageButton)findViewById(R.id.button_left);
        ImageButton ib4 = (ImageButton)findViewById(R.id.button_right);

        ib1.setOnClickListener(this);
        ib2.setOnClickListener(this);
        ib3.setOnClickListener(this);
        ib4.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }


    @Override
    public void onClick(View v) {
        mGLView.onClick(v);

    }
}