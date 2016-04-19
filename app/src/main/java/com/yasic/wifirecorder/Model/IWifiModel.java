package com.yasic.wifirecorder.Model;

import com.yasic.wifirecorder.JavaBean.MobileDataBean;
import com.yasic.wifirecorder.JavaBean.WifiConnectionBean;

import io.realm.RealmResults;

/**
 * Created by ESIR on 2016/3/17.
 */
public interface IWifiModel {
    RealmResults<WifiConnectionBean> getWifiConnectionDataByDate(String date);
    RealmResults<WifiConnectionBean> getWifiConnectionDataByHour(String date, String Hour);
    RealmResults<WifiConnectionBean> getWifiConnectionDataByMonth(String month, String year);
    RealmResults<MobileDataBean> getMobileDataByDate(String date);
    RealmResults<MobileDataBean> getMobileDataByMonth(String month, String year);
    Boolean saveWifiConnectionData(WifiConnectionBean wifiConnectionBean);
    Boolean saveMobileData(MobileDataBean mobileDataBean);
}
