package com.mycalculator.WiFiDataAcquisition.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycalculator.WiFiDataAcquisition.R;

import java.util.ArrayList;
import java.util.List;

import com.mycalculator.WiFiDataAcquisition.Bean.GridBean;

/**
 * Created by lenovo on 2017/3/16.
 */
public class MyGridAdapter extends BaseAdapter{
    private int pointSelecting=-1;

    public int getPointSelecting() {
        return pointSelecting;
    }

    public void setPointSelecting(int pointSelecting) {
        this.pointSelecting = pointSelecting;
    }

    private LayoutInflater mInflater;
    private List<GridBean> mDatas;
    private List<Integer> selectedPositions = new ArrayList<Integer>();
    //标识采集完成的Item
    public void setSeclection(int position) {
        selectedPositions.add(position);
    }


    public MyGridAdapter(Context context, List<GridBean> mDatas) {
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
            convertView = mInflater.inflate(R.layout.item,parent,false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);

        }else{
            holder =(ViewHolder)convertView.getTag();
        }

        GridBean bean =mDatas.get(position);
        holder.imageView.setImageResource(bean.getImage());
        String cooridate = "x:"+bean.getCooridateX()+",y:"+bean.getCooridateY();
        holder.textView.setText(cooridate);
        //点击变为勾选状态
        if (position == getPointSelecting()){
            holder.imageView.setImageResource(R.drawable.point_now);
        }
        //刷新为完成状态
        for (int i=0 ;i <selectedPositions.size();i++){
            if(selectedPositions.get(i)==position){
                holder.imageView.setImageResource(R.drawable.compoint);
            }
        }
        return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
