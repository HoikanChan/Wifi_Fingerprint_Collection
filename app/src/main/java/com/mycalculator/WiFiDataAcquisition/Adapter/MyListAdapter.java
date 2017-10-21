package com.mycalculator.WiFiDataAcquisition.Adapter;

import android.content.Context;
import android.util.Log;
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
import com.mycalculator.WiFiDataAcquisition.Bean.XYItemBean;
import com.mycalculator.WiFiDataAcquisition.R;
import com.mycalculator.WiFiDataAcquisition.util.DbManager;

import java.util.List;

/**
 * Created by lenovo on 2017/3/27.
 */
public class MyListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<XYItemBean> mDatas;
    private ViewHolder holder;
    private Context context;
    private MyListItemAdapter itemAdapter;

    public MyListAdapter(Context context, List<XYItemBean> mDatas) {
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

    public void removeItem(int position){
        int x=mDatas.get(position).getX();
        int y=mDatas.get(position).getY();
        DbManager.delete(context,x,y);
        mDatas.remove(position);
        Toast.makeText(context,"指纹删除成功",Toast.LENGTH_SHORT).show();
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.fingerprint_item,parent,false);
            holder = new ViewHolder();
            holder.x = (TextView) convertView.findViewById(R.id.item_x);
            holder.y = (TextView) convertView.findViewById(R.id.item_y);
            holder.listItem = (ListView) convertView.findViewById(R.id.listItemView);
            holder.deleteButton= (Button) convertView.findViewById(R.id.deleteButton);
            convertView.setTag(holder);

        }else{
            holder =(ViewHolder)convertView.getTag();
        }

        XYItemBean bean =mDatas.get(position);
        //Log.i("Adapter", bean.toString());
        holder.x.setText(bean.getX()+"");
        holder.y.setText(bean.getY()+"");
        List<ListBean> listBeans = mDatas.get(position).getmDatas();
        Log.i("adapter", "getView: "+listBeans.size());
        MyListItemAdapter adapter = new MyListItemAdapter(context,listBeans);
        holder.listItem.setAdapter(adapter);
        setListViewHeight(holder.listItem);
        holder.deleteButton.setOnClickListener(new deleteButtonListener(position));
        return convertView;
    }
    //LIST的删除按钮事件
    private class deleteButtonListener implements View.OnClickListener{
        private int position;

        public deleteButtonListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == holder.deleteButton.getId()){
                removeItem(position);
            }
        }
    }
    //设置每条Item的高度
    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private class ViewHolder{
        Button deleteButton;
        TextView x ;
        TextView y ;
        ListView listItem;
    }

}
