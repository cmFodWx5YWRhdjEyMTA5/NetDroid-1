package org.netdroid;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Michał on 03.06.2018.
 */

public class NetworkScan extends AsyncTask<String, Void, ArrayList<String>> {

    Activity activity;

    public NetworkScan(Activity a) {
        this.activity = a;
    }

    @Override
    protected ArrayList<String> doInBackground(String... hostList) {

        for (int i = 0; i < hostList.length; i++) {
            try {
                Socket s = new Socket();
                s.connect(new InetSocketAddress(hostList[i], 80), 100);

            } catch (java.net.ConnectException e) {


            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return readArpData();

    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        TextView txt = (TextView) activity.findViewById(R.id.txt);
        for (int i = 0; i < result.size(); i++) {
            txt.setText(txt.getText() + "\n" + result.get(i));

        }
    }


    private ArrayList<String> readArpData() {
        ArrayList<String> arpResult = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;

            while ((line = reader.readLine()) != null) {
                arpResult.add(line);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<String> iter = arpResult.iterator();

        while (iter.hasNext()) {
            String str = iter.next();
            if (str.contains("00:00:00:00:00:00"))
                iter.remove();
        }
        return arpResult;

    }
}




