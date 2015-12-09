package com.mozidev.testopengl.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mozidev.testopengl.Constants;
import com.mozidev.testopengl.R;
import com.mozidev.testopengl.activity.DownloadActivity;
import com.mozidev.testopengl.activity.MappingActivity;
import com.mozidev.testopengl.model.Site;
import com.mozidev.testopengl.service.DownloadService;
import com.mozidev.testopengl.service.SocketService;
import com.mozidev.testopengl.utils.FileUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends BaseFragment {

    @Bind(R.id.site)
    TextView site;
    @Bind(R.id.UDID)
    TextView udid;
    @Bind(R.id.organisation)
    TextView organisation;
    @Bind(R.id.created)
    TextView created;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.is_file)
    TextView is_file;


    @Bind(R.id.btn_download)
    Button download;
    @Bind(R.id.btn_connect)
    Button connect;
    @Bind(R.id.btn_mapping)
    Button mapping;

    private Site mSite;
    private DownloadReceiver receiver;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        /*Bundle bundle = getArguments();
        mSite = (Site) bundle.getSerializable(Constants.ARG_SITE);*/
        mSite = ((DownloadActivity) getActivity()).getCurrentSite();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isFile = FileUtils.isFile();
        site.setText(site.getText() + "\t " +"\t " +"\t " + mSite.name);
        udid.setText(udid.getText() + "\t "+ "\t "+ "\t " + mSite.udid);
        organisation.setText(organisation.getText() + "\t " + mSite.organisation);
        created.setText(created.getText() + "\t "+ "\t "+ "\t " + mSite.created);
        status.setText(status.getText() + "\t "+ "\t "+ "\t " + mSite.status);
        is_file.setText(is_file.getText() + "\t " + "\t " + "\t " + (isFile ? "Yes" : "No"));
        mapping.setEnabled(isFile);
        download.setEnabled(!isFile);

    }


    @OnClick(R.id.btn_download)
    void download() {
        Intent intent = new Intent(getActivity(), DownloadService.class);
        intent.putExtra("url", "http://www.ex.ua/get/210726622");
        getActivity().startService(intent);
        download.setEnabled(false);
    }


    @OnClick(R.id.btn_connect)
    void connect() {
        if(!isServiceRunning(SocketService.class)){
            getActivity().startService(new Intent(getActivity(), SocketService.class));
            connect.setText("Disconnect");
        } else {
            getActivity().stopService(new Intent(getActivity(), SocketService.class));
            connect.setText("Connect");
        }
    }


    @OnClick(R.id.btn_mapping)
   public void mapping() {
        startActivity(new Intent(getActivity(), MappingActivity.class));
    }


    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }


    @Override
    public void onResume() {
        super.onResume();
        receiver = new DownloadReceiver();
        getActivity().registerReceiver(receiver, new IntentFilter(Constants.INTENT_FILTER_DOWNLOAD));
    }


    private class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Constants.EXTRA_DOWNLOAD)){
                if(intent.getBooleanExtra(Constants.EXTRA_DOWNLOAD, false)){
                    mapping.setEnabled(true);
                    Toast.makeText(getActivity(), "File successful downloaded, you may start mapping", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Download failed, try again", Toast.LENGTH_LONG).show();
                    download.setEnabled(true);
                }
            }
        }
    }
}
