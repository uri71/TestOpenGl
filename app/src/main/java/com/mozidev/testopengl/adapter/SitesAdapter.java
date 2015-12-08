package com.mozidev.testopengl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mozidev.testopengl.R;
import com.mozidev.testopengl.model.Site;

import java.util.List;

/**
 * Created by y.storchak on 04.12.15.
 */
public class SitesAdapter extends ArrayAdapter{
    List <Site> list;
    Context mContext;


    public SitesAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        list = objects;
        mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list, parent, false);

        }
        int number = position + 1;
        ((TextView)view.findViewById(R.id.number)).setText("" + number);
        ((TextView)view.findViewById(R.id.site)).setText(list.get(position).name);
        ((TextView)view.findViewById(R.id.UDID)).setText(list.get(position).udid);
        ((TextView)view.findViewById(R.id.organisation)).setText(list.get(position).organisation);
        ((TextView)view.findViewById(R.id.status)).setText(list.get(position).status?"connected":"disconnected");
        //((TextView)view.findViewById(R.id.timeline)).setText(list.get(position).isFile + "");
        ((TextView)view.findViewById(R.id.created)).setText(list.get(position).created);

        return view;
    }


    @Override
    public int getCount() {
        return list.size();
    }
}
