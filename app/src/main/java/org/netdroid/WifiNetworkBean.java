package org.netdroid;

/**
 * Created by lewin on 22.05.2018.
 */

public class WifiNetworkBean {
    public String ssid;
    public String freq;
    public String channel;

    public WifiNetworkBean(String ssid, String freq, String channel) {
        this.ssid = ssid;
        this.freq = freq;
        this.channel = channel;
    }
}
