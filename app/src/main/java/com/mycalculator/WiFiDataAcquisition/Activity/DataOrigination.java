package com.mycalculator.WiFiDataAcquisition.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.mycalculator.WiFiDataAcquisition.R;
import com.mycalculator.WiFiDataAcquisition.Bean.RoomSettings;
import com.mycalculator.WiFiDataAcquisition.util.WIFI;

public class DataOrigination extends AppCompatActivity {
    WIFI wifi;
    EditText width;
    EditText length;
    EditText gatherTimes;
    Button startButton;
    Switch wifiSwitch;
    RoomSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_origination);
        getWindow().setTitle("设置基础数据");
        width = (EditText) findViewById(R.id.roomWidth);
        length = (EditText)findViewById(R.id.roomLength);
        gatherTimes = (EditText)findViewById(R.id.gatherTimes);
        startButton = (Button)findViewById(R.id.start);
        wifiSwitch = (Switch)findViewById(R.id.wifiSwitch);

        wifi = new WIFI(this);
        Boolean wifiEnabled = wifi.getWifiState();
        if(wifiEnabled != null && wifiEnabled){
            wifiSwitch.setChecked(true);
        }else {
            wifiSwitch.setChecked(false);
        }
        //switch元件的监听器
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifi.openWifi();

                } else {
                    wifiSwitch.setChecked(true);
                    toastReminder();
                }
            }
        });
    }

    public void toastReminder(){
        Toast.makeText(this,"请勿关闭WiFi",Toast.LENGTH_SHORT);
    }

    //开始button的onclick事件
    public void start_event(View view){

        String lengthEdit = length.getText().toString();
        String widthEdit = width.getText().toString();
        String timesEdit = gatherTimes.getText().toString();

        if(lengthEdit.equals("") || widthEdit.equals("") ||timesEdit.equals("")){
            Toast.makeText(this,"请设置好数据",Toast.LENGTH_SHORT).show();
            return;
        }
        //房间尺寸放在全局变量中
        settings =(RoomSettings)getApplication();
        settings.setLength(Float.parseFloat(lengthEdit));
        settings.setWidth(Float.parseFloat(widthEdit));
        settings.setTimes(Integer.parseInt(timesEdit));

        Intent intent = new Intent(this,WifiAcquireActivity.class);
        this.startActivity(intent);
    }
}
