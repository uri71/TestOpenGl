package com.mozidev.testopengl.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.support.v4.app.NotificationCompat;

import com.mozidev.testopengl.Constants;
import com.mozidev.testopengl.R;

public class SocketService extends Service {


    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;


    public SocketService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("SocketConnection",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        startForeground();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        stopForeground(true);
        mServiceHandler.getConnection().disconnect();
        super.onDestroy();
    }

    private void startForeground() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        this.startForeground(Constants.ID_NOTIFICATION_SOCKET_SERVICE, notification);
    }


    private final class ServiceHandler extends Handler {

        public SocketConnection getConnection() {
            return connection;
        }


        SocketConnection connection;


        public ServiceHandler(Looper looper) {
            super(looper);
            connection = new SocketConnection(SocketService.this);
        }


        @Override
        public void handleMessage(Message msg) {
        }
    }
}
