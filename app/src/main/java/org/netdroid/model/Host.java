package org.netdroid.model;

import java.net.Inet4Address;

/**
 * Created by lewin on 09.05.2018.
 */

public class Host {
    private String hostName;
    private Inet4Address ipAddress;
    private byte[] macAddress;
    private String macVendor;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Inet4Address getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Inet4Address ipAddress) {
        this.ipAddress = ipAddress;
    }

    public byte[] getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(byte[] macAddress) {
        this.macAddress = macAddress;
    }

    public String getMacVendor() {
        return macVendor;
    }

    public void setMacVendor(String macVendor) {
        this.macVendor = macVendor;
    }
}
