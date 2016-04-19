package com.yasic.wifirecorder.View;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yasic.wifirecorder.Adapter.WifiRecordDailyAdapter;
import com.yasic.wifirecorder.Presenter.WifiRecordeListPresenter;
import com.yasic.wifirecorder.R;

/**
 * Created by Yasic on 2016/4/19.
 */
public class WifiRecordeListView implements BaseViewInterface<Activity, WifiRecordeListPresenter> {
    private View view;
    private WifiRecordeListPresenter wifiRecordeListPresenter;
    private TextView tvDataDate;
    private ImageButton ibBeforeDay, ibAfterDay;
    private RecyclerView rvWifiRecord;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_wifirecordlist, container, false);
        tvDataDate = (TextView)view.findViewById(R.id.tv_DataDate);
        ibBeforeDay = (ImageButton)view.findViewById(R.id.ib_BeforeDay);
        ibAfterDay = (ImageButton)view.findViewById(R.id.ib_AfterDay);
        rvWifiRecord = (RecyclerView) view.findViewById(R.id.rv_WifiRecord);
        setDateChangeListener();
    }

    public void initRvWifiRecordDaily(WifiRecordDailyAdapter wifiRecordDailyAdapter){
        rvWifiRecord.setItemAnimator(new DefaultItemAnimator());
        rvWifiRecord.setLayoutManager(new LinearLayoutManager(wifiRecordeListPresenter.getContext()));
        rvWifiRecord.setAdapter(wifiRecordDailyAdapter);
    }

    private void setDateChangeListener(){
        ibBeforeDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiRecordeListPresenter.changeTargetDate(false);
            }
        });
        ibAfterDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiRecordeListPresenter.changeTargetDate(true);
            }
        });
    }

    public void setDataDate(String date){
        tvDataDate.setText(date);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setPresenter(Activity activity) {

    }

    @Override
    public void setPresenter(WifiRecordeListPresenter wifiRecordeListPresenter) {
        this.wifiRecordeListPresenter = wifiRecordeListPresenter;
    }
}
