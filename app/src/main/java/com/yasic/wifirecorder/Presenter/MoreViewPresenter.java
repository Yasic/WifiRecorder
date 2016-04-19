package com.yasic.wifirecorder.Presenter;

import com.yasic.wifirecorder.View.MoreView;

/**
 * Created by Yasic on 2016/4/19.
 */
public class MoreViewPresenter extends BasePresenterFragment<MoreView>{
    @Override
    protected void onBindBVI(){
        BVIView.setPresenter(this);
    }

    @Override
    protected Class<MoreView> getBVIClass() {
        return MoreView.class;
    }

}
