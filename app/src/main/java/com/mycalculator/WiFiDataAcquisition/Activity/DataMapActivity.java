package com.mycalculator.WiFiDataAcquisition.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.mycalculator.WiFiDataAcquisition.Adapter.DataMapAdapter;
import com.mycalculator.WiFiDataAcquisition.Bean.FingerprintBean;
import com.mycalculator.WiFiDataAcquisition.Bean.RoomSettings;
import com.mycalculator.WiFiDataAcquisition.R;
import com.mycalculator.WiFiDataAcquisition.util.DbManager;

import java.util.ArrayList;
import java.util.List;

public class DataMapActivity extends AppCompatActivity {
    GridView gridView;
    DataMapAdapter adapter;
    List<FingerprintBean> mdatas;
    TextView titleText;
    String ssid;
    RoomSettings settings;
    int width ;
    int length ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_map);
        gridView = (GridView) findViewById(R.id.data_map_grid);
        titleText = (TextView) findViewById(R.id.data_map_title);

        settings=(RoomSettings)getApplication();
        width = (int)settings.getWidth();
        length = (int)settings.getLength();

        Intent intent = getIntent();
        ssid = intent.getStringExtra("ssidToMap");
        gridView.setNumColumns(width);
        initData();

        titleText.setText(ssid);
        adapter = new DataMapAdapter(this,mdatas);
        gridView.setAdapter(adapter);
    }

    private void initData() {
        mdatas = new ArrayList<FingerprintBean>();
        for(int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                FingerprintBean bean = new FingerprintBean(0,ssid,j,i);
                mdatas.add(bean);
            }
        }
        List<FingerprintBean> sqlDatas = new ArrayList<FingerprintBean>();
        sqlDatas = DbManager.selectBySsid(this,ssid);
        for (FingerprintBean bean:sqlDatas){
            int x =bean.getX();
            int y = bean.getY();
            int position = x*width+y;
            mdatas.set(position,bean);
        }
        for(FingerprintBean bean:mdatas){
            Log.i("datamap", bean.toString());
        }
    }
}
