package com.yasic.wifirecorder.JavaBean;

import io.realm.RealmObject;

/**
 * Created by Yasic on 2016/4/17.
 */
public class WifiConnectionBean extends RealmObject{
    private String wifiName;
    private String connectionBeginTime;
    private String connectionEndTime;
    private String connectionBeginDate;
    private String connectionBeginHour;
    private boolean isConnected;

    public WifiConnectionBean() {
    }

    public WifiConnectionBean(String wifiName, String connectionBeginTime, String connectionEndTime, String connectionBeginDate, String connectionBeginHour, boolean isConnected) {
        this.wifiName = wifiName;
        this.connectionBeginTime = connectionBeginTime;
        this.connectionEndTime = connectionEndTime;
        this.connectionBeginDate = connectionBeginDate;
        this.connectionBeginHour = connectionBeginHour;
        this.isConnected = isConnected;
    }

    public String getWifiName() {
        return wifiName;
    }

    public String getConnectionBeginTime() {
        return connectionBeginTime;
    }

    public String getConnectionEndTime() {
        return connectionEndTime;
    }

    public String getConnectionBeginDate() {
        return connectionBeginDate;
    }

    public String getConnectionBeginHour() {
        return connectionBeginHour;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public void setConnectionBeginTime(String connectionBeginTime) {
        this.connectionBeginTime = connectionBeginTime;
    }

    public void setConnectionEndTime(String connectionEndTime) {
        this.connectionEndTime = connectionEndTime;
    }

    public void setConnectionBeginDate(String connectionBeginDate) {
        this.connectionBeginDate = connectionBeginDate;
    }

    public void setConnectionBeginHour(String connectionBeginHour) {
        this.connectionBeginHour = connectionBeginHour;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
