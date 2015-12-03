package com.mozidev.testopengl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mozidev.testopengl.service.DownloadService;

public class DownloadActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private DownloadReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        progressBar = ((ProgressBar) findViewById(R.id.pb_download));
        receiver = new DownloadReceiver();
        registerReceiver(receiver, new IntentFilter(Constants.INTENT_FILTER_DOWNLOAD));
        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadActivity.this, DownloadService.class);
                intent.putExtra("url", "http://www.ex.ua/get/210726622");
                startService(intent);
                progressBar.setVisibility(View.VISIBLE);

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

    }


    private class DownloadReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Constants.EXTRA_DOWNLOAD)){
                if(intent.getBooleanExtra(Constants.EXTRA_DOWNLOAD, false)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DownloadActivity.this, "Download complete successfull", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(DownloadActivity.this, MainActivity.class));
                            DownloadActivity.this.finish();

                        }
                    }, 3000);
                } else {
                    Toast.makeText(DownloadActivity.this, "Download failed, try again", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}
