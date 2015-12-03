package com.mozidev.testopengl.service;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.mozidev.testopengl.Constants;
import com.mozidev.testopengl.R;

import java.io.File;

public class DownloadService extends Service {

    private long id;
    private DownloadReceiver receiver;


    public DownloadService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new DownloadReceiver();
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        startForeground();
    }

    private void startForeground() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Test download obj file")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        this.startForeground(1, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra("url")) {
            String uri = intent.getStringExtra("url");
            DownloadManager manager = ((DownloadManager) getSystemService(DOWNLOAD_SERVICE));
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
            Uri destinationUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "test.obj"));
            request.setDestinationUri(destinationUri);
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            id = manager.enqueue(request);
        }
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver != null) unregisterReceiver(receiver);
    }

    private class DownloadReceiver extends BroadcastReceiver {

        private static final String TAG = "DownloadReceiver";


        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            Log.d(TAG, "DownloadReceiver intent.getAction() = " + action);
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                Log.d(TAG, "onReceive " + downloadId);

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                DownloadManager manager = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE));
                Cursor cursor = null;
                try {
                    cursor = manager.query(query);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "cursor is malformed exception = " + e.getMessage());
                }

                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    int columnError = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                    int status = cursor.getInt(columnIndex);
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {

                        //// TODO: 02.12.15 processing event
                        Intent intent1 = new Intent(Constants.INTENT_FILTER_DOWNLOAD);
                        intent1.putExtra(Constants.EXTRA_DOWNLOAD, true);
                        sendBroadcast(intent1);

                    } else if (status == DownloadManager.STATUS_FAILED) {
                        // report by backend about failure
                        Log.e(TAG, "Download video id = " + downloadId + " FAILED");
                        int reason = cursor.getInt(columnError);
                        parseErrorMessage(reason);
                        Intent intent1 = new Intent(Constants.INTENT_FILTER_DOWNLOAD);
                        intent1.putExtra(Constants.EXTRA_DOWNLOAD, false);
                        sendBroadcast(intent1);
                    } else if (status == DownloadManager.STATUS_PAUSED) {

                    } else if (status == DownloadManager.STATUS_PENDING) {

                    } else if (status == DownloadManager.STATUS_RUNNING) {

                    }
                }
                cursor.close();
            }
        }


        private void parseErrorMessage(int reason) {
            String error;
            switch (reason) {
                case DownloadManager.ERROR_CANNOT_RESUME:
                    error = "ERROR_CANNOT_RESUME";
                    break;
                case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                    error = "ERROR_DEVICE_NOT_FOUND";
                    break;
                case DownloadManager.ERROR_FILE_ERROR:
                    error = "ERROR_FILE_ERROR";
                    break;
                case DownloadManager.ERROR_HTTP_DATA_ERROR:
                    error = "ERROR_HTTP_DATA_ERROR";
                    break;
                case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                    error = "ERROR_UNHANDLED_HTTP_CODE";
                    break;
                case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                    error = "ERROR_TOO_MANY_REDIRECTS";
                    break;
                case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                    error = "ERROR_INSUFFICIENT_SPACE";
                    break;
                case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                    error = "ERROR_FILE_ALREADY_EXISTS";
                    break;
                case DownloadManager.ERROR_UNKNOWN:
                    error = "ERROR_UNKNOWN";
                    break;
                default:
                    error = "code = " + reason;
            }
            Log.e(TAG, "DOWNLOAD failed reason = " + error);
        }
    }
}
