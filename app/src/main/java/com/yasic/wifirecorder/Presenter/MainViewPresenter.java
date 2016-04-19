package com.yasic.wifirecorder.Presenter;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.yasic.wifirecorder.Model.WifiModel;
import com.yasic.wifirecorder.R;
import com.yasic.wifirecorder.Server.WifiListener;
import com.yasic.wifirecorder.Util.TimeUtils;
import com.yasic.wifirecorder.View.MainView;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasic on 2016/4/17.
 */
public class MainViewPresenter extends BasePresenterActivity<MainView> {
    private WifiModel wifiModel;
    private String targetDate;
    private Date targetDateValue;
    private TimeUtils timeUtils = new TimeUtils();
    int[] wifiTable = new int[24];
    int[] mobileTable = new int[24];
    private BarChart barChart;

    @Override
    protected void onBindBVI(){
        setSupportActionBar((Toolbar)BVIView.getView().findViewById(R.id.toolbar));
        wifiModel = new WifiModel(this);
        startService(new Intent(MainViewPresenter.this, WifiListener.class));
        BVIView.setPresenter(this);
        List<String> tabTitleList = new ArrayList<>();
        tabTitleList.add("Day");
        tabTitleList.add("Wifi List");
        tabTitleList.add("Month");
        tabTitleList.add("More");
        List<BasePresenterFragment> basePresenterFragmentList = new ArrayList<>();
        basePresenterFragmentList.add(new BarChartPresenter());
        basePresenterFragmentList.add(new WifiRecordeListPresenter());
        basePresenterFragmentList.add(new PieChartPresenter());
        basePresenterFragmentList.add(new MoreViewPresenter());
        BVIView.setViewPagerAndTablayout(tabTitleList, basePresenterFragmentList);
    }

    @Override
    protected Class getBVIClass() {
        return MainView.class;
    }
}
