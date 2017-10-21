package com.mycalculator.WiFiDataAcquisition.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mycalculator.WiFiDataAcquisition.Activity.DataMapActivity;
import com.mycalculator.WiFiDataAcquisition.Activity.OpenGLChart;
import com.mycalculator.WiFiDataAcquisition.Bean.ListBean;
import com.mycalculator.WiFiDataAcquisition.Bean.RoomSettings;
import com.mycalculator.WiFiDataAcquisition.R;
import com.mycalculator.WiFiDataAcquisition.openGL.OpenGLView;
import com.mycalculator.WiFiDataAcquisition.util.DbManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/11.
 */
public class SelectListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ListBean> mDatas;
    private ViewHolder holder;
    private Context context;
    private ArrayList<String> ssids = new ArrayList<String>();
    private ArrayList<Boolean> isCheckeds = new ArrayList<Boolean>();
    private int times;
    public ArrayList<String> getSsids() {
        return ssids;
    }

    public SelectListAdapter(Context context, List<ListBean> mDatas,int times) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.times = times;
        initIsCheckd(mDatas.size());
    }
    //记录被选择的checkBox
    private void initIsCheckd(int size) {
        for (int i=0;i<size;i++){
            Boolean b = false;
            isCheckeds.add(b);
        }
    }
    //checkBox事件
    public void OnCheckedChange(int position,Boolean isChecked){
        if (isChecked == true){
            ssids.add(mDatas.get(position).getSSID());
        }
        if (isChecked == false){
            ssids.remove(mDatas.get(position).getSSID());
        }
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
            convertView = mInflater.inflate(R.layout.select_ap_item,parent,false);
            holder = new ViewHolder();
            holder.RSSI = (TextView) convertView.findViewById(R.id.select_ap_avg);
            holder.SSID = (TextView) convertView.findViewById(R.id.select_ap_ssid);
            holder.quantity = (TextView)convertView.findViewById(R.id.select_ap_quantity) ;
            holder.checkBox= (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.button = (Button) convertView.findViewById(R.id.select_map_button);
            convertView.setTag(holder);

        }else{
            holder =(ViewHolder)convertView.getTag();
        }

        ListBean bean =mDatas.get(position);
        //Log.i("Adapter", bean.toString());

        holder.RSSI.setText(bean.getAvgRssi()+"");
        holder.SSID.setText(bean.getSSID()+"");
        float quantity = bean.getQuantity()/times;
        Log.i("times", "times: "+times);
        Log.i("times", "quantity: "+quantity);
        holder.quantity.setText(quantity+"");
        holder.checkBox.setTag(position);
        holder.checkBox.setChecked(isCheckeds.get(position));
        holder.checkBox.setOnCheckedChangeListener(new checkedChangeListener(position));
        holder.button.setOnClickListener(new oncheckListener(position));
        return convertView;
    }

    private class ViewHolder{
        CheckBox checkBox;
        Button button;
        TextView SSID;
        TextView RSSI;
        TextView quantity;
    }
    //checkBox监听器
    private class checkedChangeListener implements CompoundButton.OnCheckedChangeListener {
        private int position;
        public checkedChangeListener(int position) {
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isCheckeds.set((Integer) buttonView.getTag(), isChecked);
            if (buttonView.getId() == holder.checkBox.getId()){
                OnCheckedChange((Integer) buttonView.getTag(),isChecked);
            }
        }
    }

    //强度分布图查看Button的onclick事件
    private class oncheckListener implements View.OnClickListener {
        private int position;
        public oncheckListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == holder.button.getId()){
                click_event(position);
            }
        }
    }

    private void click_event(int position) {
        Intent intent =new Intent(context, DataMapActivity.class);
        intent.putExtra("ssidToMap",mDatas.get(position).getSSID());
        context.startActivity(intent);

    }
}
