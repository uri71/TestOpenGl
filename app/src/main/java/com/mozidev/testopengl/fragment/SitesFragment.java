package com.mozidev.testopengl.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mozidev.testopengl.Constants;
import com.mozidev.testopengl.R;
import com.mozidev.testopengl.activity.DownloadActivity;
import com.mozidev.testopengl.adapter.SitesAdapter;
import com.mozidev.testopengl.model.Site;
import com.mozidev.testopengl.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SitesFragment extends Fragment implements AdapterView.OnItemClickListener{


    private List<Site> sites;


    public SitesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sites, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sites = generateDummyList(100);
        ListView list = ((ListView) view.findViewById(R.id.list));
        list.setAdapter(new SitesAdapter(getActivity(), R.layout.item_list, sites));
        list.setOnItemClickListener(this);

    }


    private List generateDummyList(int i) {
        List <Site>list = new ArrayList();

        for (int y = 0; y<i; y++){
            list.add(new Site("Name" + (y+1), "Udid"+(y+1), "Organisation"+(y+1), y%2==0, FileUtils.isFile(), "2015_11_22"));
        }
        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((DownloadActivity) getActivity()).setCurrentSite(sites.get(position));
        DetailsFragment detailsFragment = new DetailsFragment();
        /*Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ARG_SITE, sites.get(position));
        detailsFragment.setArguments(bundle);*/
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, detailsFragment, Constants.TAG_DETAILS_FRAGMENT).commit();
    }
}
