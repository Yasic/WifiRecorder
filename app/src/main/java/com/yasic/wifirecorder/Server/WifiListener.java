package com.yasic.wifirecorder.Server;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.yasic.wifirecorder.JavaBean.MobileDataBean;
import com.yasic.wifirecorder.JavaBean.WifiConnectionBean;
import com.yasic.wifirecorder.Model.WifiModel;
import com.yasic.wifirecorder.Presenter.MainViewPresenter;
import com.yasic.wifirecorder.R;
import com.yasic.wifirecorder.Util.TimeUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Yasic on 2016/4/17.
 */
public class WifiListener extends Service{
    private Long periodSecond = 2l;
    private Binder binder = new Binder();
    private WifiConnectionBean wifiConnectionBean;
    private MobileDataBean mobileDataBean;
    private WifiManager wifiManager;
    private WifiModel wifiModel;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, final int flag, final int startId){
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_service);
        Intent notificationIntent = new Intent(this, MainViewPresenter.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this)
                .setContent(mRemoteViews)
                .setAutoCancel(true)
                //.setContentTitle("Wifi Recorder")
                //.setContentText("Click to look over the record")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.wifii)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.wifii, new BitmapFactory.Options()))
                .setWhen(System.currentTimeMillis())
                .build();
        startForeground(1235, notification);

        wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        wifiModel = new WifiModel(this);
        Observable.timer(0, periodSecond * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        TimeUtils timeUtils = new TimeUtils();
                        ConnectivityManager connectivityManager = (ConnectivityManager)WifiListener.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo net = connectivityManager.getActiveNetworkInfo();
                        if (net == null){
                            return;
                        }
                        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        NetworkInfo.State state = networkInfo.getState();
                        if (net.getTypeName().equals("WIFI")) {
                            mobileDataBean = null;
                            if (wifiConnectionBean == null) {
                                wifiConnectionBean = new WifiConnectionBean(wifiManager.getConnectionInfo().getSSID(),
                                        timeUtils.getCurrentDateAndTime(),
                                        timeUtils.getCurrentDateAndTime(),
                                        timeUtils.getNowDate(),
                                        timeUtils.getNowHour(),
                                        true);
                                wifiModel.saveWifiConnectionData(wifiConnectionBean);
                            } else {
                                if (wifiManager.getConnectionInfo().getSSID().equals(wifiConnectionBean.getWifiName())
                                        && wifiConnectionBean.getConnectionBeginDate().equals(timeUtils.getNowDate())) {
                                    wifiConnectionBean.setConnectionEndTime(timeUtils.getCurrentDateAndTime());
                                    wifiModel.saveWifiConnectionData(wifiConnectionBean);
                                } else {
                                    wifiConnectionBean.setConnectionEndTime(timeUtils.getCurrentDateAndTime());
                                    wifiModel.saveWifiConnectionData(wifiConnectionBean);
                                    wifiConnectionBean = new WifiConnectionBean(wifiManager.getConnectionInfo().getSSID(),
                                            timeUtils.getCurrentDateAndTime(),
                                            timeUtils.getCurrentDateAndTime(),
                                            timeUtils.getNowDate(),
                                            timeUtils.getNowHour(),
                                            true);
                                    wifiModel.saveWifiConnectionData(wifiConnectionBean);
                                }
                            }
                        }
                        else if (net.getTypeName().equals("MOBILE")){
                            wifiConnectionBean = null;
                            if (mobileDataBean == null) {
                                mobileDataBean = new MobileDataBean(timeUtils.getCurrentDateAndTime(),
                                        timeUtils.getCurrentDateAndTime(),
                                        timeUtils.getNowDate(),
                                        timeUtils.getNowHour());
                                wifiModel.saveMobileData(mobileDataBean);
                            } else {
                                mobileDataBean.setMobileDataEndTime(timeUtils.getCurrentDateAndTime());
                                wifiModel.saveMobileData(mobileDataBean);
                            }
                        }
                    }
                });
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        startService(new Intent(this, WifiListener.class));
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
