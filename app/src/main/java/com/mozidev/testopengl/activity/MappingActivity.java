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
package com.mozidev.testopengl.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.mozidev.testopengl.R;
import com.mozidev.testopengl.opengl.BaseObject;
import com.mozidev.testopengl.utils.JsonUtils;
import com.mozidev.testopengl.view.MyGLSurfaceView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MappingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.button_up)
     ImageButton buttonUp;
    @Bind(R.id.button_down)
     ImageButton buttonDown;
    @Bind(R.id.button_left)
     ImageButton buttonLeft;
    @Bind(R.id.button_right)
     ImageButton buttonRight;
    @Bind(R.id.button_control_panel_show)
     ImageButton buttonShow;
    @Bind(R.id.button_control_panel_hide)
     ImageButton buttonHide;
    @Bind(R.id.btn_container)
     RelativeLayout btnContainer;
    @Bind(R.id.button_save)
     ImageButton buttonSave;

    private Animation animation1;
    private Animation animation2;

    private MyGLSurfaceView mGLView;

    private BaseObject object;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity

        object = JsonUtils.jsonToObject();

        animation1 = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation2 = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        animation1.setDuration(500);
        animation2.setDuration(500);

        mGLView = (MyGLSurfaceView) findViewById(R.id.gl_view);
        mGLView.init(object);

        btnContainer.setVisibility(View.GONE);
        buttonHide.setVisibility(View.GONE);

        buttonUp.setOnClickListener(this);
        buttonDown.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
        buttonShow.setOnClickListener(this);
        buttonHide.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mGLView.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_control_panel_show:
                buttonShow.startAnimation(animation2);
                buttonShow.setVisibility(View.GONE);
                buttonHide.setVisibility(View.VISIBLE);
                buttonHide.startAnimation(animation1);
                btnContainer.setVisibility(View.VISIBLE);
                btnContainer.startAnimation(animation1);
                return;
            case R.id.button_control_panel_hide:
                btnContainer.startAnimation(animation2);
                btnContainer.setVisibility(View.GONE);
                buttonHide.startAnimation(animation2);
                buttonShow.setVisibility(View.VISIBLE);
                buttonHide.setVisibility(View.GONE);
                buttonShow.startAnimation(animation1);
                return;
        }
        mGLView.onClick(v);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}