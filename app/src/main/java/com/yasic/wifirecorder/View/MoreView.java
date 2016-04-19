package com.yasic.wifirecorder.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.github.mikephil.charting.charts.LineChart;
import com.yasic.wifirecorder.Presenter.MoreViewPresenter;
import com.yasic.wifirecorder.R;

/**
 * Created by Yasic on 2016/4/19.
 */
public class MoreView implements BaseViewInterface<Activity, MoreViewPresenter> {
    private View view;
    private MoreViewPresenter moreViewPresenter;
    private ButtonFlat btfClearDB, btfSetting, btfUpdate, btfAbout;
    private LinearLayout liAboutInformation;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_more, container, false);
        btfClearDB = (ButtonFlat)view.findViewById(R.id.btf_ClearDB);
        btfSetting = (ButtonFlat)view.findViewById(R.id.btf_Setting);
        btfUpdate = (ButtonFlat)view.findViewById(R.id.btf_Update);
        btfAbout = (ButtonFlat)view.findViewById(R.id.btf_About);
        liAboutInformation = (LinearLayout)view.findViewById(R.id.li_AboutInformation);
        setClickFuntion();
    }

    private void setClickFuntion() {
        btfClearDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(moreViewPresenter.getContext(), "还没做呢.", Toast.LENGTH_SHORT).show();
            }
        });
        btfSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(moreViewPresenter.getContext(), "还没做呢..", Toast.LENGTH_SHORT).show();
            }
        });
        btfUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(moreViewPresenter.getContext(), "还没做呢...", Toast.LENGTH_SHORT).show();
            }
        });
        btfAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liAboutInformation.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setPresenter(Activity activity) {

    }

    @Override
    public void setPresenter(MoreViewPresenter moreViewPresenter) {
        this.moreViewPresenter = moreViewPresenter;
    }
}
