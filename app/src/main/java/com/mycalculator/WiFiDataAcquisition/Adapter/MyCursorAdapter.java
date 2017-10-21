package com.mycalculator.WiFiDataAcquisition.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycalculator.WiFiDataAcquisition.Bean.GridBean;
import com.mycalculator.WiFiDataAcquisition.R;
import com.mycalculator.WiFiDataAcquisition.util.Constant;

import java.util.List;

/**
 * Created by lenovo on 2017/3/25.
 */
public class MyCursorAdapter extends CursorAdapter{

    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View view =inflater.inflate(R.layout.fingerprint_item,parent,false);
        holder.x = (TextView) view.findViewById(R.id.item_x);
        holder.y = (TextView) view.findViewById(R.id.item_y);
        holder.RSSI = (TextView) view.findViewById(R.id.item_RSSI);
        holder.SSID = (TextView) view.findViewById(R.id.item_SSID);
        view.setTag(holder);
        Log. i("cursor" ,"newView=" +view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log. i("cursor" ,"bindView=" +view);
        ViewHolder viewHolder=(ViewHolder) view.getTag();

        viewHolder.x.setText(cursor.getInt(cursor.getColumnIndex(Constant.COORIDATE_X))+"");
        viewHolder.y.setText(cursor.getInt(cursor.getColumnIndex(Constant.COORIDATE_Y))+"");
        viewHolder.RSSI.setText(cursor.getString(cursor.getColumnIndex(Constant.SSID)));
        viewHolder.SSID.setText(cursor.getInt(cursor.getColumnIndex(Constant.RSSI))+"");
        Log.i("TAG", "bindView: binded "+cursor.getString(cursor.getColumnIndex(Constant.SSID))+"");
    }

    private class ViewHolder{
        TextView x ;
        TextView y ;
        TextView RSSI;
        TextView SSID;
    }


}
