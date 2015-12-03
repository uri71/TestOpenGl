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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.mozidev.testopengl.service.DownloadService;

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageButton buttonUp;
    private ImageButton buttonDown;
    private ImageButton buttonLeft;
    private ImageButton buttonRight;
    private ImageButton buttonShow;
    private ImageButton buttonHide;

    private Animation animation1;
    private Animation animation2;

    private MyGLSurfaceView mGLView;

    private Base3DObject object;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        ObjParser parser = new ObjParser();
        object = parser.parse();

        animation1 = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation2 = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        animation1.setDuration(500);
        animation2.setDuration(500);

        mGLView = (MyGLSurfaceView)findViewById(R.id.gl_view);
        mGLView.init(object);

        buttonUp = (ImageButton)findViewById(R.id.button_up);
        buttonDown = (ImageButton)findViewById(R.id.button_down);
        buttonLeft = (ImageButton)findViewById(R.id.button_left);
        buttonRight = (ImageButton)findViewById(R.id.button_right);
        buttonShow = (ImageButton)findViewById(R.id.button_control_panel_show);
        buttonHide = (ImageButton)findViewById(R.id.button_control_panel_hide);

        buttonUp.setVisibility(View.GONE);
        buttonDown.setVisibility(View.GONE);
        buttonLeft.setVisibility(View.GONE);
        buttonRight.setVisibility(View.GONE);
        buttonHide.setVisibility(View.GONE);

        buttonUp.setOnClickListener(this);
        buttonDown.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
        buttonShow.setOnClickListener(this);
        buttonHide.setOnClickListener(this);
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
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("url", "http://www.ex.ua/get/210726622");
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_up :
                Log.d("OnClickListener", "button up clicked!");
            break;
            case R.id.button_down :
                Log.d("OnClickListener", "button down clicked!");
                break;
            case R.id.button_left :
                Log.d("OnClickListener", "button left clicked!");
                break;
            case R.id.button_right :
                Log.d("OnClickListener", "button right clicked!");
                break;
            case R.id.button_control_panel_show :
                buttonShow.startAnimation(animation2);
                buttonUp.setVisibility(View.VISIBLE);
                buttonDown.setVisibility(View.VISIBLE);
                buttonLeft.setVisibility(View.VISIBLE);
                buttonRight.setVisibility(View.VISIBLE);
                buttonShow.setVisibility(View.GONE);
                buttonHide.setVisibility(View.VISIBLE);
                buttonUp.startAnimation(animation1);
                buttonDown.startAnimation(animation1);
                buttonLeft.startAnimation(animation1);
                buttonRight.startAnimation(animation1);
                buttonHide.startAnimation(animation1);
                break;
            case R.id.button_control_panel_hide :
                buttonUp.startAnimation(animation2);
                buttonDown.startAnimation(animation2);
                buttonLeft.startAnimation(animation2);
                buttonRight.startAnimation(animation2);
                buttonHide.startAnimation(animation2);
                buttonUp.setVisibility(View.GONE);
                buttonDown.setVisibility(View.GONE);
                buttonLeft.setVisibility(View.GONE);
                buttonRight.setVisibility(View.GONE);
                buttonShow.setVisibility(View.VISIBLE);
                buttonHide.setVisibility(View.GONE);
                buttonShow.startAnimation(animation1);
                break;
        }
    }
}