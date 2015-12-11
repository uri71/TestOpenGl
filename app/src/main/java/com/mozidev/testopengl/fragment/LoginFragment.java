package com.mozidev.testopengl.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mozidev.testopengl.Constants;
import com.mozidev.testopengl.R;
import com.mozidev.testopengl.api.ApiHelper;
import com.mozidev.testopengl.api.LoginResponseWrapper;
import com.mozidev.testopengl.network.BusEvent;
import com.mozidev.testopengl.network.JsonField;
import com.mozidev.testopengl.network.Message;
import com.mozidev.testopengl.table.WorkLog;
import com.mozidev.testopengl.utils.Connectivity;
import com.mozidev.testopengl.utils.PrefUtils;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by y.storchak on 28.07.15.
 */
public class LoginFragment extends BaseFragment implements TextView.OnEditorActionListener {

    private static final String TAG = "LoginFragment";
    private final int SHOW_ERROR_ALERT_TIMEOUT = 5000;
    private final int SHOW_PROGRESS_FINISH_TIMEOUT = 3000;
    private final int PROGRESS_REFRESH_TIMEOUT = 1000;

    @Bind(R.id.login)
    EditText login;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.iv_alert)
    ImageView alert;
    @Bind(R.id.ring_1)
    ImageView ring1;
    @Bind(R.id.ring_2)
    ImageView ring2;
    @Bind(R.id.ring_3)
    ImageView ring3;
    @Bind(R.id.ring_4)
    ImageView ring4;
    @Bind(R.id.box)
    ImageView box;
    @Bind(R.id.et_container)
    LinearLayout login_container;
    @Bind(R.id.logo)
    ImageView logo;
    @Bind(R.id.tv_log)
    TextView tv_log;
    @Bind(R.id.send)
    Button send;
    @Bind(R.id.progress_container)
    RelativeLayout progress_container;

    private List<ImageView> rings;
    private Timer mTimer;
    private int mProgressCount = 0;
    private boolean newDevice = true;
    private int mConnectionAlertTimeout = 5000;
    private Gson gson = new GsonBuilder().create();
    private LoginResponseWrapper wrapper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login1, container, false);
    }


    @Override
    public void onViewCreated(View view,
                              @Nullable
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        password.setOnEditorActionListener(this);
        login.setOnEditorActionListener(this);
        setupRings();
        //TypefaceHelper.typeface(view);
    }


    private void setupRings() {
        rings = new ArrayList<>();
        rings.add(ring1);
        rings.add(ring2);
        rings.add(ring3);
        rings.add(ring4);
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.send)
    public void send() {
        hideKeyboard();
        if (Connectivity.isConnected(getActivity())) {
            sendUserData();
        } else {
            alert.setVisibility(View.VISIBLE);
            alert.setImageResource(R.drawable.no_internet_alert);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alert.setVisibility(View.GONE);
                }
            }, mConnectionAlertTimeout);
        }
        ApiHelper.login(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    String json = response.body().string();
                    Log.d(TAG, json);
                    wrapper = gson.fromJson(json, LoginResponseWrapper.class);

                        if (!wrapper.isError) {
                            PrefUtils.setSoketUrl(getContext(), wrapper.data.socketUrl);
                            Log.d(TAG, wrapper.data.socketUrl);
                            PrefUtils.setToken(getContext(), wrapper.data.authToken);
                            Log.d(TAG, wrapper.data.authToken);
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        }, getActivity(), login.getText().toString(), password.getText().toString());
    }

    private void showErrorAlert(String message) {
        boolean showAlert = false;
        if(TextUtils.isEmpty(message)) {
            progress_container.setVisibility(View.GONE);
            return;
        }

        switch (message){
            case Message.invalidLicense:
                showAlert = true;
                alert.setVisibility(View.VISIBLE);
                alert.setImageResource(R.drawable.login_invalid_license_alert);
                break;
            case Message.invalidOrganization:
            case Message.invalidPassword:
                showAlert = true;
                alert.setVisibility(View.VISIBLE);
                alert.setImageResource(R.drawable.login_invalid_password_alert);
                break;
            default:
                progress_container.setVisibility(View.GONE);

        }
        if (!showAlert) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alert.setVisibility(View.GONE);
            }
        }, SHOW_ERROR_ALERT_TIMEOUT);

    }


    private void sendUserData() {
        /*if ((login.getText() != null && login.getText().length() > loginLength)
                && (password.getText() != null && password.getText().length() > passwordLength)) {
            User.setCredentials(login.getText().toString(), password.getText().toString());*/
        //loginToService();
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.login:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    password.requestFocus();
                    return true;
                }
                break;
            case R.id.password:
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard();
                    sendUserData();

                    return true;
                }
                break;
        }
        return false;
    }


    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(password.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }



    private void showProgressView(final boolean showProgress, final boolean success, final String message) {
        if (showProgress) {
            showLoginScreen(false);
            progress_container.setVisibility(View.VISIBLE);
            mTimer = new Timer();
            mTimer.schedule(new ProgressTask(), PROGRESS_REFRESH_TIMEOUT, PROGRESS_REFRESH_TIMEOUT);
        } else {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
                for (ImageView ring : rings) {
                    ring.setImageResource(R.drawable.login_checking_dark_ring);
                }
            }
            if (success) {
                box.setImageResource(R.drawable.login_checking_box_success);
            } else {
                box.setImageResource(R.drawable.login_checking_box_fail);
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(success){

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new SitesFragment(), Constants.TAG_SITE_FRAGMENT)
                                .commit();
                    } else {
                        //handling error events
                        if (message != null && (message.contains(Message.invalidPassword) || message.contains(Message.invalidOrganization))) {
                            box.setImageResource(R.drawable.login_checking_empty_box);
                            progress_container.setVisibility(View.GONE);

                            showErrorAlert(message);
                            Log.e(TAG, message);
                        }
                        else if(message != null && message.contains(Message.invalidLicense)){
                            showErrorAlert(message);

                        } else /*if(message != null)*/{
                            showErrorAlert(message);
                        }
                        showLoginScreen(true);
                    }
                }
            }, SHOW_PROGRESS_FINISH_TIMEOUT);
        }
    }


    private void showLoginScreen(boolean b) {
        int visible = b? View.VISIBLE: View.GONE;
        login_container.setVisibility(visible);
        tv_log.setVisibility(visible);
        send.setVisibility(visible);
        logo.setVisibility(visible);
    }


    private class ProgressTask extends TimerTask {

        @Override
        public void run() {
            changeProgress();

        }
    }


    private void changeProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressCount != 0) {
                    Log.d(TAG, "changeProgress mProgressCount = " + mProgressCount);
                    rings.get(mProgressCount - 1).setImageResource(R.drawable.login_checking_dark_ring);
                    mProgressCount = mProgressCount < 4 ? mProgressCount + 1 : 0;

                } else {
                    Log.d(TAG, "changeProgress ELSE mProgressCount = " + mProgressCount);
                    for (ImageView ring : rings) {
                        ring.setImageResource(R.drawable.login_checking_empty_ring);
                    }
                    mProgressCount++;
                }
            }
        });
    }
}
