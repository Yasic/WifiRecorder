package com.yasic.wifirecorder.JavaBean;

import io.realm.RealmObject;

/**
 * Created by Yasic on 2016/4/18.
 */
public class MobileDataBean extends RealmObject{
    private String mobileDataBeginTime;
    private String mobileDataEndTime;
    private String mobileDataBeginDate;
    private String mobileDataBeginHour;

    public MobileDataBean() {
    }

    public MobileDataBean(String mobileDataBeginTime, String mobileDataEndTime, String mobileDataBeginDate, String mobileDataBeginHour) {
        this.mobileDataBeginTime = mobileDataBeginTime;
        this.mobileDataEndTime = mobileDataEndTime;
        this.mobileDataBeginDate = mobileDataBeginDate;
        this.mobileDataBeginHour = mobileDataBeginHour;
    }

    public String getMobileDataBeginTime() {
        return mobileDataBeginTime;
    }

    public String getMobileDataEndTime() {
        return mobileDataEndTime;
    }

    public String getMobileDataBeginDate() {
        return mobileDataBeginDate;
    }

    public String getMobileDataBeginHour() {
        return mobileDataBeginHour;
    }

    public void setMobileDataBeginTime(String mobileDataBeginTime) {
        this.mobileDataBeginTime = mobileDataBeginTime;
    }

    public void setMobileDataEndTime(String mobileDataEndTime) {
        this.mobileDataEndTime = mobileDataEndTime;
    }

    public void setMobileDataBeginDate(String mobileDataBeginDate) {
        this.mobileDataBeginDate = mobileDataBeginDate;
    }

    public void setMobileDataBeginHour(String mobileDataBeginHour) {
        this.mobileDataBeginHour = mobileDataBeginHour;
    }
}
