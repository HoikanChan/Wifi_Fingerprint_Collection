package com.mycalculator.WiFiDataAcquisition.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mycalculator.WiFiDataAcquisition.Bean.ListBean;
import com.mycalculator.WiFiDataAcquisition.R;
import com.mycalculator.WiFiDataAcquisition.util.DbManager;

import java.util.List;

/**
 * Created by lenovo on 2017/3/27.
 */
public class MyListItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ListBean> mDatas;
    private ViewHolder holder;
    private Context context;

    public MyListItemAdapter(Context context, List<ListBean> mDatas) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.fingerprint_item_item,parent,false);
            holder = new ViewHolder();
            holder.RSSI = (TextView) convertView.findViewById(R.id.item_RSSI);
            holder.SSID = (TextView) convertView.findViewById(R.id.item_SSID);
            holder.times = (TextView)convertView.findViewById(R.id.item_times) ;
            holder.variance = (TextView)convertView.findViewById(R.id.item_variance) ;
            convertView.setTag(holder);

        }else{
            holder =(ViewHolder)convertView.getTag();
        }

        ListBean bean =mDatas.get(position);
        holder.RSSI.setText(bean.getAvgRssi()+"");
        holder.SSID.setText(bean.getSSID()+"");
        holder.variance.setText(bean.getVariance()+"");
        holder.times.setText(bean.getQuantity()+"");

        return convertView;
    }

    private class ViewHolder{
        TextView RSSI;
        TextView SSID;
        TextView times;
        TextView variance;
    }

}
