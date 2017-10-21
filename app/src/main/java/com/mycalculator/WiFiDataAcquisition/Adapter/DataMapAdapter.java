package com.mycalculator.WiFiDataAcquisition.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mycalculator.WiFiDataAcquisition.Bean.FingerprintBean;
import com.mycalculator.WiFiDataAcquisition.R;

import java.util.List;

/**
 * Created by lenovo on 2017/4/13.
 */
public class DataMapAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<FingerprintBean> mDatas;

    public DataMapAdapter(Context context,List<FingerprintBean> mDatas) {
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
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.data_map_item,parent,false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.data_map_item_text);
            holder.xText = (TextView) convertView.findViewById(R.id.colormap_x);
            holder.yText = (TextView) convertView.findViewById(R.id.colormap_y);
            holder.bg = (FrameLayout) convertView.findViewById(R.id.data_map_bg);
            convertView.setTag(holder);

        }else{
            holder =(ViewHolder)convertView.getTag();
        }
        FingerprintBean bean = mDatas.get(position);
        holder.textView.setText(bean.getRSSI()+"");
        holder.xText.setText(bean.getX()+"");
        holder.yText.setText(bean.getY()+"");

        //根据rssi设置item的bg color
        int rssi = bean.getRSSI();
        if (rssi == 0){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg0);
        }else if ( rssi <= -80){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg1);
        }else if(rssi>-80 && rssi<= -75){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg2);
        }else if(rssi>-75 && rssi<= -70){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg3);
        }else if(rssi>-70 && rssi<= -65){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg4);
        }else if(rssi>-65 && rssi<= -60){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg5);
        }else if(rssi>-60 && rssi<= -55){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg6);
        }else if(rssi>-55 && rssi<= -50){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg7);
        }else if(rssi>-50 && rssi<= -46){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg8);
        }else if(rssi>-46 && rssi<= -43){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg9);
        }else if(rssi>-43 && rssi<= -40){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg10);
        }else if(rssi>-40 && rssi<-10){
            holder.bg.setBackgroundResource(R.drawable.datamap_item_bg11);
        }
        return convertView;
    }

    private class ViewHolder{
        TextView xText;
        TextView yText;
        TextView textView;
        FrameLayout bg;
    }
}
