package org.netdroid.fragments;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.netdroid.NetworkFragmentRecyclerViewAdapter;
import org.netdroid.NetworkScan;
import org.netdroid.R;
import org.netdroid.model.Host;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by lewin on 10.05.2018.
 */

public class NetworkFragment extends Fragment {

    private TextView txt;
    private Button scanButton;
    public RecyclerView recyclerView;



    public NetworkFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_network, container, false);
        txt=(TextView)v.findViewById(R.id.errorTxt);
        scanButton = (Button) v.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAScan();
            }
        });
        recyclerView=(RecyclerView)v.findViewById(R.id.rView);
        return v;
    }

    private void performAScan() {
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;
        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiManager.isWifiEnabled() == false) {
            txt.setText("Enable the WIFI adapter");
        } else if (wifiInfo.getSupplicantState() != SupplicantState.COMPLETED) {
            txt.setText("Connect to a network");
        } else {
            int networkAddress = wifiManager.getDhcpInfo().netmask & wifiManager.getDhcpInfo().ipAddress;
            int hostCount = (int) Math.pow(2, (32 - Integer.bitCount(wifiManager.getDhcpInfo().netmask)));
            String[] hostList = new String[hostCount];
            for (int i = 0; i <= hostCount - 1; i++) {
                hostList[i] = longToIp(Integer.reverseBytes(networkAddress) + (i + 1));
            }
            new NetworkScan(this.getActivity(),this).execute(hostList);

        }
    }

    public String longToIp(int i) {

        return ((i >> 24) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + (i & 0xFF);
    }

}

