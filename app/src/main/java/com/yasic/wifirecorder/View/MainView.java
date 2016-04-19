package com.yasic.wifirecorder.View;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yasic.wifirecorder.Adapter.ViewPagerMainInterfaceAdapter;
import com.yasic.wifirecorder.Presenter.BasePresenterFragment;
import com.yasic.wifirecorder.Presenter.MainViewPresenter;
import com.yasic.wifirecorder.R;

import java.util.List;

/**
 * Created by Yasic on 2016/4/17.
 */
public class MainView implements BaseViewInterface<MainViewPresenter, Fragment> {
    private View view;
    private MainViewPresenter mainViewPresenter;
    private ViewPager vpMainInterface;
    private TabLayout tlBottomBar;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.activity_mainview, container, false);
        vpMainInterface = (ViewPager)view.findViewById(R.id.vp_MainInterface);
        tlBottomBar = (TabLayout)view.findViewById(R.id.tl_BottomBar);
    }

    public void setViewPagerAndTablayout(List<String> tabTitleList, List<BasePresenterFragment> basePresenterFragmentList){
        vpMainInterface.setAdapter(new ViewPagerMainInterfaceAdapter<>(
                mainViewPresenter.getSupportFragmentManager(),
                tabTitleList,
                basePresenterFragmentList));
        vpMainInterface.setOffscreenPageLimit(3);
        tlBottomBar.setupWithViewPager(vpMainInterface);
        tlBottomBar.setTabMode(TabLayout.MODE_FIXED);
        mainViewPresenter.getSupportFragmentManager().beginTransaction().commit();
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setPresenter(MainViewPresenter mainViewPresenter) {
        this.mainViewPresenter = mainViewPresenter;
    }

    @Override
    public void setPresenter(Fragment fragment) {

    }
}
