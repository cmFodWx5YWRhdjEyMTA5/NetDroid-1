package org.netdroid.fragments;

import android.content.Context;
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
    TextView txtSsid;
    TextView txtIpAddress;
    TextView txtMacAddress;
    TextView txtStatus;
    TextView txtLinkSpeed;

    public WirelessFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_wireless, container, false);
        txtSsid =v.findViewById(R.id.txtSsidVal);
        txtSsid.setText("");
        txtIpAddress =v.findViewById(R.id.txtIpVal);
        txtIpAddress.setText("");
        txtMacAddress =v.findViewById(R.id.txtMacVal);
        txtMacAddress.setText("");
        txtStatus =v.findViewById(R.id.txtStatusVal);
        txtStatus.setText("");
        txtLinkSpeed =v.findViewById(R.id.txtLinkVal);
        txtLinkSpeed.setText("");
        setConnectionInfo();
        return v;
    }

    private void setConnectionInfo() {
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;
        wifiInfo = wifiManager.getConnectionInfo();
        if(wifiManager.isWifiEnabled()==false){
            txtStatus.setText("Enable the WIFI adpater");
            txtStatus.setTextColor(Color.RED);

        }
        else if(wifiInfo.getSupplicantState()!= SupplicantState.COMPLETED){
            txtStatus.setText("Connect to a network");
            txtStatus.setTextColor(Color.RED);

        }
        else {
            txtStatus.setText("Connected");
            txtStatus.setTextColor(Color.GREEN);
            txtSsid.setText(wifiInfo.getSSID());
            txtMacAddress.setText(wifiInfo.getMacAddress());
            Long ip= Long.valueOf(wifiInfo.getIpAddress());
            txtIpAddress.setText(longToIp(ip));
            Integer ls=wifiInfo.getLinkSpeed();
            txtLinkSpeed.setText(ls.toString()+" mb/s");
        }
    }

    public String longToIp(long i) {

        return ((i & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." +((i >> 24) & 0xFF));

    }
}
