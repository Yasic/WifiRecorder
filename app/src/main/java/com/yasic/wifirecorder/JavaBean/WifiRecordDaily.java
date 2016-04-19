package com.yasic.wifirecorder.JavaBean;

/**
 * Created by Yasic on 2016/4/19.
 */
public class WifiRecordDaily {
    private String wifiName;
    private String wifiTime;

    public WifiRecordDaily(String wifiName, String wifiTime) {
        this.wifiName = wifiName;
        this.wifiTime = wifiTime;
    }

    public String getWifiName() {
        return wifiName;
    }

    public String getWifiTime() {
        return wifiTime;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public void setWifiTime(String wifiTime) {
        this.wifiTime = wifiTime;
    }
}
