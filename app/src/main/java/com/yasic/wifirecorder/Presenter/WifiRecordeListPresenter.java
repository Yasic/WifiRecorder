package com.yasic.wifirecorder.Presenter;

import android.util.Log;

import com.github.mikephil.charting.data.BarData;
import com.yasic.wifirecorder.Adapter.WifiRecordDailyAdapter;
import com.yasic.wifirecorder.JavaBean.MobileDataBean;
import com.yasic.wifirecorder.JavaBean.WifiConnectionBean;
import com.yasic.wifirecorder.JavaBean.WifiRecordDaily;
import com.yasic.wifirecorder.Model.WifiModel;
import com.yasic.wifirecorder.Util.TimeUtils;
import com.yasic.wifirecorder.View.WifiRecordeListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Yasic on 2016/4/19.
 */
public class WifiRecordeListPresenter extends BasePresenterFragment<WifiRecordeListView>{
    private WifiModel wifiModel;
    private String targetDate;
    private Date targetDateValue;
    private TimeUtils timeUtils = new TimeUtils();
    private List<WifiRecordDaily> wifiRecordDailyList = new ArrayList<>();
    private WifiRecordDailyAdapter wifiRecordDailyAdapter;
    @Override
    protected void onBindBVI(){
        BVIView.setPresenter(this);
        wifiModel = new WifiModel(getActivity());
        targetDate = timeUtils.getNowDate();
        targetDateValue = new Date();
        String[] temp = targetDate.split(" ");
        BVIView.setDataDate(temp[0] + "-" + temp[1] + "-" + temp[2]);
        wifiRecordDailyAdapter = new WifiRecordDailyAdapter(this.getContext(), wifiRecordDailyList);
        BVIView.initRvWifiRecordDaily(wifiRecordDailyAdapter);
        Observable.timer(0, 10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        setDataFromDatabase();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("throwableinbindview", throwable.getMessage());
                    }
                });
    }

    private void setDataFromDatabase(){
        BarData barData = null;
        wifiRecordDailyList.clear();
        Observable.create(new Observable.OnSubscribe<RealmResults<WifiConnectionBean>>() {
            @Override
            public void call(Subscriber<? super RealmResults<WifiConnectionBean>> subscriber) {
                RealmResults<WifiConnectionBean> results = wifiModel.getWifiConnectionDataByDate(targetDate);
                if (results != null){
                    subscriber.onNext(results);
                    subscriber.onCompleted();
                }else {
                    Log.i("results","== null");
                }
            }})
                .subscribe(new Action1<RealmResults<WifiConnectionBean>>() {
                    @Override
                    public void call(RealmResults<WifiConnectionBean> wifiConnectionBeen) {
                        if (wifiConnectionBeen != null && wifiConnectionBeen.size() != 0) {
                            String startTime, endTime;
                            String[] startTemp, endTemp;
                            int startHour, endHour;
                            int startMinute, endMinute;
                            for (WifiConnectionBean wifiConnectionBean : wifiConnectionBeen) {
                                startTime = wifiConnectionBean.getConnectionBeginTime();
                                endTime = wifiConnectionBean.getConnectionEndTime();
                                startTemp = startTime.split(" ");
                                endTemp = endTime.split(" ");
                                try {
                                    wifiRecordDailyList.add(new WifiRecordDaily(wifiConnectionBean.getWifiName(),
                                            startTemp[3] + ":" + startTemp[4] + "-" + endTemp[3] + ":" + endTemp[4]));
                                } catch (Exception e) {
                                    Log.e("getdatafromdb", e.getMessage());
                                }
                            }
                            setRvWifiRecordDaily();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("dberror1", throwable.getMessage());
                    }
                });
    }

    private void setRvWifiRecordDaily(){
        wifiRecordDailyAdapter.notifyDataSetChanged();
    }

    public void changeTargetDate(boolean isAdd){
        //Log.i("date", targetDateValue+"");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(targetDateValue);
        String dateString;
        if (isAdd){
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        }else {
            calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
        }
        targetDateValue = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        dateString = formatter.format(targetDateValue);
        Log.i("date",targetDateValue + " " + dateString);
        String[] temp = dateString.split("-");
        targetDate = temp[0] + " " + temp[1] + " " + temp[2];
        BVIView.setDataDate(dateString);
        setDataFromDatabase();
    }

    @Override
    protected Class<WifiRecordeListView> getBVIClass() {
        return WifiRecordeListView.class;
    }
}
