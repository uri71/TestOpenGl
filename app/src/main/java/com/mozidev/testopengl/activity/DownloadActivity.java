package com.mozidev.testopengl.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mozidev.testopengl.Constants;
import com.mozidev.testopengl.R;
import com.mozidev.testopengl.fragment.SitesFragment;
import com.mozidev.testopengl.model.Site;

public class DownloadActivity extends AppCompatActivity {

    public Site getCurrentSite() {
        return currentSite;
    }


    public void setCurrentSite(Site currentSite) {
        this.currentSite = currentSite;
    }


    public Site currentSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SitesFragment(), Constants.TAG_SITE_FRAGMENT).commit();

    }
}
