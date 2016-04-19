package com.yasic.wifirecorder.Presenter;

import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.yasic.wifirecorder.JavaBean.MobileDataBean;
import com.yasic.wifirecorder.JavaBean.WifiConnectionBean;
import com.yasic.wifirecorder.Model.WifiModel;
import com.yasic.wifirecorder.R;
import com.yasic.wifirecorder.Util.TimeUtils;
import com.yasic.wifirecorder.View.BarChartView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Yasic on 2016/4/19.
 */
public class BarChartPresenter extends BasePresenterFragment<BarChartView> {
    private WifiModel wifiModel;
    private String targetDate;
    private Date targetDateValue;
    int[] wifiTable = new int[24];
    int[] mobileTable = new int[24];
    private BarChart barChart;
    private TimeUtils timeUtils = new TimeUtils();

    @Override
    protected void onBindBVI(){
        BVIView.setPresenter(this);
        initBarChart();
        wifiModel = new WifiModel(getActivity());
        targetDate = timeUtils.getNowDate();
        targetDateValue = new Date();
        String[] temp = targetDate.split(" ");
        BVIView.setDataDate(temp[0] + "-" + temp[1] + "-" + temp[2]);

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

    private void initBarChart(){
        barChart = BVIView.getBcDailyChart();
        BarChart barChart = BVIView.getBcDailyChart();
        barChart.setDescription("");
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftYAxis = barChart.getAxisLeft();
        leftYAxis.setStartAtZero(true);
        leftYAxis.setDrawGridLines(false);
        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setDrawGridLines(true);
        try {
            showDataToChart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDataFromDatabase(){
        BarData barData = null;
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
                        wifiTable = new int[24];
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
                        Log.i("dberror1", throwable.getMessage());
                    }
                });
        Observable.create(new Observable.OnSubscribe<RealmResults<MobileDataBean>>() {
            @Override
            public void call(Subscriber<? super RealmResults<MobileDataBean>> subscriber) {
                RealmResults<MobileDataBean> results = wifiModel.getMobileDataByDate(targetDate);
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

    private void showDataToChart() throws Exception{
        ArrayList<String> xValues = new ArrayList<String>();
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        int wifiTotalTime = 0, mobileDataTotalTime = 0;
        for (int i = 0; i < 24; i++) {
            xValues.add(i + "");
            yVals.add(new BarEntry(new float[]{wifiTable[i], mobileTable[i]}, i));
            wifiTotalTime += wifiTable[i];
            mobileDataTotalTime += mobileTable[i];
        }
        BVIView.setWifiAndMobileDataTime(wifiTotalTime+"min", mobileDataTotalTime+"min");
        BarDataSet barDataSet = new BarDataSet(yVals, "");
        barDataSet.setColors(new int[]{getResources().getColor(R.color.colorWifiChart),getResources().getColor(R.color.colorMobileDataChart)});
        barDataSet.setStackLabels(new String[]{"Wifi","Mobile Data"});
        try {
            barChart.clearValues();
        }catch (Exception e){
            Log.e(this.toString(),e.getMessage());
        }finally {
            BarData barData = new BarData(xValues, barDataSet);
            barData.setDrawValues(false);
            barChart.setData(barData);
        }
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
    protected Class getBVIClass() {
        return BarChartView.class;
    }
}
