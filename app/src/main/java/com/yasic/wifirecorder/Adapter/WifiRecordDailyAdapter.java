package com.yasic.wifirecorder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasic.wifirecorder.JavaBean.WifiConnectionBean;
import com.yasic.wifirecorder.JavaBean.WifiRecordDaily;
import com.yasic.wifirecorder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Yasic on 2016/4/19.
 */
public class WifiRecordDailyAdapter extends RecyclerView.Adapter<WifiRecordDailyAdapter.MyViewHolder> {

    /**
     * 上下文
     */
    private Context context;

    private List<WifiRecordDaily> wifiRecordDailyList = new ArrayList<>();

    public WifiRecordDailyAdapter(Context context, List<WifiRecordDaily> wifiConnectionBeanList) {
        this.context = context;
        this.wifiRecordDailyList = wifiConnectionBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_wifirecord, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (wifiRecordDailyList.size() != 0){
            WifiRecordDaily wifiRecordDaily= wifiRecordDailyList.get(position);
            holder.tvWifiSSID.setText(wifiRecordDaily.getWifiName());
            holder.tvWifiTime.setText(wifiRecordDaily.getWifiTime());
        }
    }

    @Override
    public int getItemCount() {
        return wifiRecordDailyList.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvWifiSSID;
        private TextView tvWifiTime;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvWifiSSID = (TextView)itemView.findViewById(R.id.tv_WifiSSID);
            tvWifiTime = (TextView)itemView.findViewById(R.id.tv_WifiTime);
        }
    }
}
