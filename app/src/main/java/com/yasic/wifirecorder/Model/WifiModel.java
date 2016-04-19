package com.yasic.wifirecorder.Model;

import android.content.Context;

import com.yasic.wifirecorder.JavaBean.MobileDataBean;
import com.yasic.wifirecorder.JavaBean.WifiConnectionBean;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Yasic on 2016/4/17.
 */
public class WifiModel implements IWifiModel {
    private Realm wifiConnectionRealm;
    private Realm mobileDataRealm;

    public WifiModel(Context context){
        wifiConnectionRealm =  Realm.getInstance(new RealmConfiguration.Builder(context).name("WifiConnection.realm").build());
        mobileDataRealm = Realm.getInstance(new RealmConfiguration.Builder(context).name("MobileData.realm").build());
    }

    @Override
    public RealmResults<WifiConnectionBean> getWifiConnectionDataByDate(String date) {
        RealmResults<WifiConnectionBean> results = wifiConnectionRealm.where(WifiConnectionBean.class)
                .equalTo("connectionBeginDate",date)
                .findAll();
        if (results.size() == 0){
            return null;
        }
        else {
            return results;
        }
    }

    @Override
    public RealmResults<WifiConnectionBean> getWifiConnectionDataByHour(String date, String hour) {
        RealmResults<WifiConnectionBean> results = wifiConnectionRealm.where(WifiConnectionBean.class)
                .equalTo("connectionBeginHour",hour)
                .findAll();
        if (results.size() == 0){
            return null;
        }
        else {
            return results;
        }
    }

    @Override
    public RealmResults<WifiConnectionBean> getWifiConnectionDataByMonth(String month, String year) {
        RealmResults<WifiConnectionBean> results = wifiConnectionRealm.where(WifiConnectionBean.class)
                .contains("connectionBeginDate", year + " " + month)
                .findAll();
        return results;
    }


    @Override
    public RealmResults<MobileDataBean> getMobileDataByDate(String date) {
        RealmResults<MobileDataBean> results = mobileDataRealm.where(MobileDataBean.class)
                .equalTo("mobileDataBeginDate",date)
                .findAll();
        if (results.size() == 0){
            return null;
        }
        else {
            return results;
        }
    }

    @Override
    public RealmResults<MobileDataBean> getMobileDataByMonth(String month, String year) {
        RealmResults<MobileDataBean> results = mobileDataRealm.where(MobileDataBean.class)
                .contains("mobileDataBeginDate", year + " " + month)
                .findAll();
        return results;
    }

    @Override
    public Boolean saveWifiConnectionData(WifiConnectionBean wifiConnectionBean) {
        RealmResults<WifiConnectionBean> results = wifiConnectionRealm.where(WifiConnectionBean.class)
                .equalTo("connectionBeginTime",wifiConnectionBean.getConnectionBeginTime())
                .findAll();
        if (results.size() == 0){
            wifiConnectionRealm.beginTransaction();
            WifiConnectionBean target = wifiConnectionRealm.createObject(WifiConnectionBean.class);
            target.setWifiName(wifiConnectionBean.getWifiName());
            target.setConnectionEndTime(wifiConnectionBean.getConnectionEndTime());
            target.setConnectionBeginDate(wifiConnectionBean.getConnectionBeginDate());
            target.setConnectionBeginHour(wifiConnectionBean.getConnectionBeginHour());
            target.setConnectionBeginTime(wifiConnectionBean.getConnectionBeginTime());
            wifiConnectionRealm.commitTransaction();
        }
        else {
            wifiConnectionRealm.beginTransaction();
            WifiConnectionBean exitInstant = results.first();
            exitInstant.setConnectionEndTime(wifiConnectionBean.getConnectionEndTime());
            wifiConnectionRealm.commitTransaction();
        }
        return true;
    }

    @Override
    public Boolean saveMobileData(MobileDataBean mobileDataBean) {
        RealmResults<MobileDataBean> results = mobileDataRealm.where(MobileDataBean.class)
                .equalTo("mobileDataBeginTime",mobileDataBean.getMobileDataBeginTime())
                .findAll();
        if (results.size() == 0){
            mobileDataRealm.beginTransaction();
            MobileDataBean target = mobileDataRealm.createObject(MobileDataBean.class);
            target.setMobileDataBeginDate(mobileDataBean.getMobileDataBeginDate());
            target.setMobileDataBeginHour(mobileDataBean.getMobileDataBeginHour());
            target.setMobileDataBeginTime(mobileDataBean.getMobileDataBeginTime());
            target.setMobileDataEndTime(mobileDataBean.getMobileDataEndTime());
            mobileDataRealm.commitTransaction();
        }
        else {
            mobileDataRealm.beginTransaction();
            MobileDataBean exitInstant = results.first();
            exitInstant.setMobileDataEndTime(mobileDataBean.getMobileDataEndTime());
            mobileDataRealm.commitTransaction();
        }
        return true;
    }
}
