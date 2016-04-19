package com.yasic.wifirecorder.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.yasic.wifirecorder.Presenter.PieChartPresenter;
import com.yasic.wifirecorder.R;

/**
 * Created by Yasic on 2016/4/19.
 */
public class PieChartView implements BaseViewInterface<Activity,PieChartPresenter> {
    private PieChartPresenter pieChartPresenter;
    private View view;
    private PieChart pieChartView;
    private TextView tvDataDate;
    private ImageButton ibBeforeDay, ibAfterDay;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_piechart, container, false);
        pieChartView = (PieChart)view.findViewById(R.id.pc_StatisticalChart);
        tvDataDate = (TextView)view.findViewById(R.id.tv_DataMonth);
        ibBeforeDay = (ImageButton)view.findViewById(R.id.ib_BeforeMonth);
        ibAfterDay = (ImageButton)view.findViewById(R.id.ib_AfterMonth);
        setDateChangeListener();
    }

    private void setDateChangeListener(){
        ibBeforeDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChartPresenter.changeTargetDate(false);
            }
        });
        ibAfterDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChartPresenter.changeTargetDate(true);
            }
        });
    }

    public void setDataDate(String date){
        tvDataDate.setText(date);
    }

    public PieChart getPieChartView(){
        return pieChartView;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setPresenter(Activity activity) {

    }

    @Override
    public void setPresenter(PieChartPresenter pieChartPresenter) {
        this.pieChartPresenter = pieChartPresenter;
    }

}
