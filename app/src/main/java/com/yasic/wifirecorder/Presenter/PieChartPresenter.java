package com.yasic.wifirecorder.Presenter;

import android.util.DisplayMetrics;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.yasic.wifirecorder.JavaBean.MobileDataBean;
import com.yasic.wifirecorder.JavaBean.WifiConnectionBean;
import com.yasic.wifirecorder.Model.WifiModel;
import com.yasic.wifirecorder.R;
import com.yasic.wifirecorder.Util.TimeUtils;
import com.yasic.wifirecorder.View.PieChartView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Yasic on 2016/4/19.
 */
public class PieChartPresenter extends BasePresenterFragment<PieChartView> {
    private WifiModel wifiModel;
    private PieChart pieChart;
    private String targetMonth, targetYear;
    int[] wifiTable = new int[24];
    int[] mobileTable = new int[24];

    @Override
        protected void onBindBVI(){
        BVIView.setPresenter(this);
        wifiModel = new WifiModel(getActivity());
        pieChart = BVIView.getPieChartView();
        pieChart.setDescription("");
        TimeUtils timeUtils = new TimeUtils();
        targetMonth = timeUtils.getNowMonth();
        targetYear = timeUtils.getNowYear();
        BVIView.setDataDate(targetYear + " " + targetMonth);
        Observable.timer(0, 60, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        setPieChart();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("throwableinbindview", throwable.getMessage());
                    }
                });
    }

    private void setPieChart(){
        Observable.create(new Observable.OnSubscribe<RealmResults<WifiConnectionBean>>() {
            @Override
            public void call(Subscriber<? super RealmResults<WifiConnectionBean>> subscriber) {
                RealmResults<WifiConnectionBean> results = wifiModel.getWifiConnectionDataByMonth(targetMonth, targetYear);
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
                                    startHour = Integer.parseInt(startTemp[3]);
                                    endHour = Integer.parseInt(endTemp[3]);
                                    startMinute = Integer.parseInt(startTemp[4]);
                                    endMinute = Integer.parseInt(endTemp[4]);
                                    if (startHour == endHour) {
                                        wifiTable[startHour] += endMinute - startMinute;
                                    } else {
                                        if (startHour > endHour) {
                                            wifiTable[startHour] += 60 - startMinute;
                                            for (int j = startHour + 1; j < 23; j++) {
                                                wifiTable[j] += 60;
                                            }
                                        } else {
                                            wifiTable[startHour] += 60 - startMinute;
                                            wifiTable[endHour] += endMinute;
                                            for (int j = 1; j < endHour - startHour; j++) {
                                                wifiTable[startHour + j] += 60;
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e("getdatafromdb", e.getMessage());
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("dberror", throwable.getMessage());
                    }
                });
        Observable.create(new Observable.OnSubscribe<RealmResults<MobileDataBean>>() {
            @Override
            public void call(Subscriber<? super RealmResults<MobileDataBean>> subscriber) {
                RealmResults<MobileDataBean> results = wifiModel.getMobileDataByMonth(targetMonth, targetYear);
                if (results != null){
                    subscriber.onNext(results);
                    subscriber.onCompleted();
                }
                else {
                    Log.e("error", "results == null");
                }
            }
        })
                .subscribe(new Action1<RealmResults<MobileDataBean>>() {
                    @Override
                    public void call(RealmResults<MobileDataBean> mobileDataBeen) {
                        mobileTable = new int[24];
                        if (mobileDataBeen != null && mobileDataBeen.size() != 0) {
                            String startTime, endTime;
                            String[] startTemp, endTemp;
                            int startHour, endHour;
                            int startMinute, endMinute;
                            for (MobileDataBean mobileDataBean : mobileDataBeen) {
                                startTime = mobileDataBean.getMobileDataBeginTime();
                                endTime = mobileDataBean.getMobileDataEndTime();
                                startTemp = startTime.split(" ");
                                endTemp = endTime.split(" ");
                                try {
                                    startHour = Integer.parseInt(startTemp[3]);
                                    endHour = Integer.parseInt(endTemp[3]);
                                    startMinute = Integer.parseInt(startTemp[4]);
                                    endMinute = Integer.parseInt(endTemp[4]);
                                    if (startHour == endHour) {
                                        mobileTable[startHour] += endMinute - startMinute;
                                    } else {
                                        if (startHour > endHour) {
                                            mobileTable[startHour] += 60 - startMinute;
                                            for (int j = startHour + 1; j < 23; j++) {
                                                mobileTable[j] += 60;
                                            }
                                        } else {
                                            mobileTable[startHour] += 60 - startMinute;
                                            mobileTable[endHour] += endMinute;
                                            for (int j = 1; j < endHour - startHour; j++) {
                                                mobileTable[startHour + j] += 60;
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e("getdatafromdb", e.getMessage());
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("dberror2", throwable.getMessage());
                    }
                });
        try {
            showDataToChart();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mobileTable = new int[24];
        wifiTable = new int[24];
    }

    private void showDataToChart(){
        ArrayList<String> xValues = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        int wifiTotalTime = 0, mobileDataTotalTime = 0;
        for (int i = 0; i < 24; i++) {
            wifiTotalTime += wifiTable[i];
            mobileDataTotalTime += mobileTable[i];
        }
        xValues.add("Wifi");
        xValues.add("Mobile Data");
        Log.i("data", mobileDataTotalTime + " " + wifiTotalTime);
        yVals.add(new Entry((float) wifiTotalTime / (float)(wifiTotalTime + mobileDataTotalTime), 0));
        yVals.add(new Entry((float) mobileDataTotalTime / (float) (wifiTotalTime + mobileDataTotalTime), 1));
        PieDataSet pieDataSet = new PieDataSet(yVals, "");
        pieDataSet.setSliceSpace(0f);
        pieDataSet.setColors(new int[]{getResources().getColor(R.color.colorWifiChart),getResources().getColor(R.color.colorMobileDataChart)});
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        try {
            pieChart.clearValues();
        }catch (Exception e){
            Log.e(this.toString(),e.getMessage());
        }finally {
            PieData pieData = new PieData(xValues, pieDataSet);
            pieChart.setData(pieData);
        }
    }

    public void changeTargetDate(boolean isAdd){
        int month = Integer.parseInt(targetMonth);
        int year = Integer.parseInt(targetYear);
        if (isAdd){
            month += 1;
            if (month == 13){
                month = 1;
                year += 1;
            }
        }
        else {
            month -= 1;
            if (month == 0){
                month = 12;
                year -= 1;
            }
        }
        if (month <= 9){
            targetMonth = "0" + month;
        }
        else targetMonth = month+"";
        targetYear = year + "";
        BVIView.setDataDate(targetYear + " " + targetMonth);
        setPieChart();
    }

    @Override
    protected Class<PieChartView> getBVIClass() {
        return PieChartView.class;
    }
}
