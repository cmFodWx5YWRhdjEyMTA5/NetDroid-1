package org.netdroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by lewin on 22.05.2018.
 */

public class WiFiCustomAdapter extends ArrayAdapter<WifiNetworkBean> {

    Context context;
    private static ArrayList<WifiNetworkBean> data;
    LayoutInflater mInflater;

    public WiFiCustomAdapter(Context context, int id, ArrayList<WifiNetworkBean> data){
        super(context, id,data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        WifiHolder holder = null;
        mInflater = LayoutInflater.from(context);
        if(row==null){
            row = mInflater.inflate(R.layout.wireless_list_item, null);

            holder = new WifiHolder();
            TextView ssid = row.findViewById(R.id.txtItemSsid);
            TextView channel = row.findViewById(R.id.txtItemChannel);
            TextView freq = row.findViewById(R.id.txtItemFreq);
            WifiNetworkBean object = data.get(position);
            ssid.setText(object.ssid);
            channel.setText(object.channel);
            freq.setText(object.freq);

            //row.setTag(holder);
        }
        else{
            holder = (WifiHolder)row.getTag();
        }

        return row;
    }


    static class WifiHolder{
        TextView ssid;
        TextView freq;
        TextView channel;
    }
}

