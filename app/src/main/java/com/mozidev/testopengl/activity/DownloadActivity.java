package com.mozidev.testopengl.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.mozidev.testopengl.Constants;
import com.mozidev.testopengl.R;
import com.mozidev.testopengl.fragment.LoginFragment;
import com.mozidev.testopengl.fragment.SitesFragment;
import com.mozidev.testopengl.model.Site;
import com.mozidev.testopengl.utils.PrefUtils;

public class DownloadActivity extends BaseActivity {

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
        if (TextUtils.isEmpty(PrefUtils.getToken(this))) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new LoginFragment(), Constants.TAG_SITE_FRAGMENT)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new SitesFragment(), Constants.TAG_SITE_FRAGMENT)
                    .commit();
        }

    }
}
