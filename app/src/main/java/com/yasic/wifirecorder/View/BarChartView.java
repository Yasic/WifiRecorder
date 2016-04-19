package com.yasic.wifirecorder.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.yasic.wifirecorder.Presenter.BarChartPresenter;
import com.yasic.wifirecorder.R;

/**
 * Created by Yasic on 2016/4/19.
 */
public class BarChartView implements BaseViewInterface<Activity, BarChartPresenter> {
    private View view;
    private BarChartPresenter barChartPresenter;
    private BarChart bcDailyChart;
    private TextView tvDataDate;
    private ImageButton ibBeforeDay, ibAfterDay;
    private TextView tvWifiConnectionTime,tvMobileDataTime;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_barchart, container, false);
        bcDailyChart = (BarChart)view.findViewById(R.id.bc_DailyChart);
        tvDataDate = (TextView)view.findViewById(R.id.tv_DataDate);
        ibBeforeDay = (ImageButton)view.findViewById(R.id.ib_BeforeDay);
        ibAfterDay = (ImageButton)view.findViewById(R.id.ib_AfterDay);
        tvWifiConnectionTime = (TextView)view.findViewById(R.id.tv_WifiConnectionTime);
        tvMobileDataTime = (TextView)view.findViewById(R.id.tv_MobileDataTime);
        setDateChangeListener();
    }

    private void setDateChangeListener(){
        ibBeforeDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChartPresenter.changeTargetDate(false);
            }
        });
        ibAfterDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChartPresenter.changeTargetDate(true);
            }
        });
    }

    public void setDataDate(String date){
        tvDataDate.setText(date);
    }

    public void setWifiAndMobileDataTime(String wifiTotalTime, String mobieDataTotalTime){
        tvWifiConnectionTime.setText(wifiTotalTime);
        tvMobileDataTime.setText(mobieDataTotalTime);
    }

    public BarChart getBcDailyChart(){
        return bcDailyChart;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setPresenter(Activity activity) {

    }

    @Override
    public void setPresenter(BarChartPresenter barChartPresenter) {
        this.barChartPresenter =barChartPresenter;
    }

}
