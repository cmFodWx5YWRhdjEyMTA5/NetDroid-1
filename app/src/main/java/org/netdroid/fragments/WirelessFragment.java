package org.netdroid.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.netdroid.R;

/**
 * Created by lewin on 10.05.2018.
 */

public class WirelessFragment extends Fragment {
    //TODO: ON RESUME() -> IT SHOULD REFRESH DATA
    TextView ssid,ipAddress,macAddress,status,linkSpeed;

    public WirelessFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_wireless, container, false);
        ssid=v.findViewById(R.id.ssid);
        ipAddress=v.findViewById(R.id.ipAddress);
        macAddress=v.findViewById(R.id.macAddress);
        status=v.findViewById(R.id.status);
        linkSpeed=v.findViewById(R.id.linkSpeed);
        setConnectionInfo();
        return v;
    }

    private void setConnectionInfo() {
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;
        wifiInfo = wifiManager.getConnectionInfo();
        if(wifiManager.isWifiEnabled()==false){
            status.setText("Enable WIFI adpater");
            status.setTextColor(Color.RED);

        }
        else if(wifiInfo.getSupplicantState()!= SupplicantState.COMPLETED){
            status.setText("Connect to the network");
            status.setTextColor(Color.RED);

        }
        else {
            status.setText("Connected");
            status.setTextColor(Color.GREEN);
            ssid.setText(wifiInfo.getSSID());
            macAddress.setText(wifiInfo.getMacAddress());
            Long ip= Long.valueOf(wifiInfo.getIpAddress());
            ipAddress.setText(longToIp(ip));
            Integer ls=wifiInfo.getLinkSpeed();
            linkSpeed.setText(ls.toString()+" mb/s");
        }
    }

    public String longToIp(long i) {

        return ((i & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." +((i >> 24) & 0xFF));

    }
}
