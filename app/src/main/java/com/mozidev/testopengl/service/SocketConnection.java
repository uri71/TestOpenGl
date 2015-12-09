package com.mozidev.testopengl.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.mozidev.testopengl.Constants;
import com.mozidev.testopengl.model.JsonField;
import com.mozidev.testopengl.model.SocketEvent;
import com.mozidev.testopengl.utils.JsonUtils;
import com.mozidev.testopengl.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Date;

import de.greenrobot.event.EventBus;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by y.storchak on 09.12.15.
 */
public class SocketConnection {


    private Socket socket;
    private String TAG = "SocketConnection";
    private String socketToken;
    private Context mContext;
    private boolean isAuthenticated;
    private String token;


    public SocketConnection(Context context) {
        mContext = context;
    }

    public void connect(){
        EventBus.getDefault().register(this);
        IO.Options options = new IO.Options();
        options.forceNew = true;
        options.reconnection = true;
        String uri = "";//// TODO: 09.12.15
        if (token.isEmpty()) {
            token = PrefUtils.getToken(mContext);
            if (token.isEmpty()) {
                // TODO: 04.08.15 PROCESS EMPTY TOKEN
                Log.e(TAG, "token is empty");
                return;
            }
        }
        try {
            socket = IO.socket(uri, options);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Log.d(TAG, "SOCKET_CONNECT");

                socketConnect();
            }
        }).on(SocketEvent.authenticated, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                if (isAuthenticated) {
                    Log.e(TAG, "isAuthenticated true");
                }
                if (args.length == 0) {
                    Log.e(TAG, "Event.authenticated args is empty" + args[0].toString());

                    return;
                }
                isAuthenticated = true;
                //subscribableSocketState.onSocketStateChanged(null);
                JSONObject answer = (JSONObject) args[0];

                socketToken = answer.optString(JsonField.auzToken);
                mContext.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
                        .edit()
                        .putString(Constants.PREFS_TOKEN_TEMP, socketToken)
                        .commit();

                Log.d(TAG, "SOCKET_AUTHENTICATED: auzToken = " + socketToken);
                try {
                    /*WorkLog log = new WorkLog(new Date().getTime(), "connect to socket and authenticated successful ");
                    log.save();*/
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).on(SocketEvent.command, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                if (args.length == 0) {
                    Log.e(TAG, "EVENT_COMMAND args is empty");
                    return;
                }
                JSONObject request = (JSONObject) args[0];
                if (!request.optString(JsonField.auzToken).equals(socketToken.trim())) {
                    Log.e(TAG, "Event.command WRONG auzToken");
                    return;
                }
                Log.d(TAG, "SOCKET_COMMAND: " + SocketEvent.command + " args = " + args[0].toString());

                determineCommand(request);
            }

        }).on(SocketEvent.authenticationFailed, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                isAuthenticated = false;
                Log.d(TAG, "SOCKET_AUTHENTICATED" + args[0].toString());
                socketToken = "";
                socketConnect();

            }

        }).on(SocketEvent.BACK_command, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, SocketEvent.BACK_command);
            }

        }).on(SocketEvent.BACK_connect, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, SocketEvent.BACK_connect);
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "SOCKET_DISCONNECT");
                isAuthenticated = false;
                socketToken = "";
                try {
                    /*WorkLog log = new WorkLog(new Date().getTime(), "socket disconnected");
                    log.save();*/
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                // socketConnect();

            }

        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "SOCKET_CONNECT_ERROR" + args[0].toString());
                isAuthenticated = false;
                socketToken = "";
                try {
                    /*WorkLog log = new WorkLog(new Date().getTime(), "connect error + " + args.toString());
                    log.save();*/
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                socketConnect();

            }
        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "SOCKET_CONNECT_TIMEOUT");
                isAuthenticated = false;
                socketToken = "";
                try {
                    /*WorkLog log = new WorkLog(new Date().getTime(), "connect timeout ");
                    log.save();*/
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                socketConnect();

            }
        }).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "SOCKET_RECONNECT");
            }
        }).on(Socket.EVENT_RECONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                isAuthenticated = false;
                Log.d(TAG, "SOCKET_RECONNECT_ERROR" + args[0].toString());
                socketToken = "";
            }
        }).on(Socket.EVENT_RECONNECT_FAILED, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                isAuthenticated = false;
                Log.d(TAG, "SOCKET_RECONNECT_FAILED");
                socketToken = "";
            }
        }).on(Socket.EVENT_RECONNECT_ATTEMPT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "SOCKET_RECONNECT_ATTEMPT");
                socketToken = "";
            }
        }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "SOCKET_MESSAGE");
            }
        }).on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "SOCKET_RECONNECTING");
            }
        });
        socket.io().timeout(600000);

        socket.connect();

    }


    private void determineCommand(JSONObject request) {

    }


    private void socketConnect() {
        Log.d(TAG, "socketConnect()");

        try {
            JSONObject object = JsonUtils.getSocketAuthJson(mContext, token);
            if(object != null)socket.emit(SocketEvent.authenticate, object);
            Log.d(TAG, "socketConnect auth json = " + object.toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void disconnect(){
        EventBus.getDefault().unregister(this);
    }
}
