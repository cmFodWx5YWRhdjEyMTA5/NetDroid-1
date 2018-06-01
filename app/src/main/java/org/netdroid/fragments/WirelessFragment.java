package org.netdroid.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.netdroid.R;
import org.netdroid.WiFiCustomAdapter;
import org.netdroid.WifiNetworkBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lewin on 10.05.2018.
 */

public class WirelessFragment extends Fragment implements android.view.View.OnClickListener {
    //TODO: ON RESUME() -> IT SHOULD REFRESH DATA
    TextView txtSsid;
    TextView txtIpAddress;
    TextView txtMacAddress;
    TextView txtStatus;
    TextView txtLinkSpeed;
    ListView listView;
    WifiManager wifiManager;

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getContext(), "Test", Toast.LENGTH_LONG).show();
            List<ScanResult> mScanResults = wifiManager.getScanResults();
            ArrayList<WifiNetworkBean> beans = new ArrayList<WifiNetworkBean>();
            for(ScanResult s : mScanResults) {
                beans.add(new WifiNetworkBean(s.SSID, Integer.toString(s.frequency), Integer.toString(s.channelWidth)));
            }
            listView.setAdapter(new WiFiCustomAdapter(getContext(), R.layout.wireless_list_item, beans));
        }
    };

    public WirelessFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        getActivity().registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
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
        listView = v.findViewById(R.id.listWiFi);
        setConnectionInfo();
        wifiManager.startScan();
        List<ScanResult> mScanResults = wifiManager.getScanResults();
        Toast.makeText(getActivity().getApplicationContext(), Integer.toString(mScanResults.size()), Toast.LENGTH_LONG).show();
        //scanNetworks();
        return v;
    }

    private void setConnectionInfo() {
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

    private void scanNetworks(){
        ArrayList<WifiNetworkBean> beans = new ArrayList<WifiNetworkBean>();
        beans.add(new WifiNetworkBean("A","1","2"));

        listView.setAdapter(new WiFiCustomAdapter(getContext(), R.layout.wireless_list_item, beans));
    }

    public String longToIp(long i) {

        return ((i & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." +((i >> 24) & 0xFF));

    }

    @Override
    public void onClick(View view) {

    }
}
