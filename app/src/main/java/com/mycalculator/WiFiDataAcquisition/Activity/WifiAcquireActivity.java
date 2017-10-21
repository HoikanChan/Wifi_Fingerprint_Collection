package com.mycalculator.WiFiDataAcquisition.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycalculator.WiFiDataAcquisition.Adapter.MyGridAdapter;
import com.mycalculator.WiFiDataAcquisition.R;
import com.mycalculator.WiFiDataAcquisition.util.CustomProgressDialog;
import com.mycalculator.WiFiDataAcquisition.util.DbManager;
import com.mycalculator.WiFiDataAcquisition.Bean.GridBean;
import com.mycalculator.WiFiDataAcquisition.util.MySqlitehelper;
import com.mycalculator.WiFiDataAcquisition.Bean.RoomSettings;
import com.mycalculator.WiFiDataAcquisition.util.WIFI;
import com.mycalculator.WiFiDataAcquisition.Bean.WifiFinPtBean;
import com.mycalculator.WiFiDataAcquisition.util.readTxt;

/**
 *
 */
public class WifiAcquireActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    WIFI wifi;
    EditText xEdit;
    EditText yEdit;
    Button submitButt;
    Button dataShowButt;
    GridView gridView;
    RoomSettings settings;
    private CustomProgressDialog dialog;
    int width;
    int length;
    private List<GridBean> mdatas;
    private MyGridAdapter myGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_acquire);
        settings=(RoomSettings)getApplication();
        width = (int)settings.getWidth();
        length = (int)settings.getLength();
        initView();
        initGridViewDatas();

        gridView.setNumColumns(width);
        gridView.setOnItemClickListener(this);
    }

    private void initGridViewDatas() {
        mdatas = new ArrayList<GridBean>();
        for(int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                GridBean bean = new GridBean(R.drawable.point,i,j);
                mdatas.add(bean);
            }
        }
        myGridAdapter =new MyGridAdapter(this,mdatas);
        gridView.setAdapter(myGridAdapter);
    }

    private void initView() {
        xEdit = (EditText) findViewById(R.id.xEditText);
        yEdit = (EditText)findViewById(R.id.yEditText);
        submitButt = (Button)findViewById(R.id.submitButton);
        dataShowButt = (Button)findViewById(R.id.dataCheckButton);
        gridView = (GridView) findViewById(R.id.wifiFPView);
    }
    /*
    * 指纹采集button的onclick事件
    */
    public void  submit_event(View view){
        wifi = new WIFI(this);
        String xEditText = xEdit.getText().toString();
        String yEditText = yEdit.getText().toString();
        int times = settings.getTimes();
        //String wifiList = "";
        if(xEditText.equals("") || yEditText.equals("")){
            Toast.makeText(this,"请输入所在的X和Y坐标",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Integer.parseInt(xEditText) >= length || Integer.parseInt(yEditText) >= width){
            Toast.makeText(this,"请输入房间范围内的X和Y坐标",Toast.LENGTH_SHORT).show();
            return;
        }
        dialog = CustomProgressDialog.createDialog(this);
        dialog.setMessage("Submitting");
        dialog.show();
        Log.i("test","x:"+xEditText+",y:"+yEditText);
        wifi.scanlist(dialog,xEditText,yEditText,times);
        xEdit.setText("");
        yEdit.setText("");
        int pointId =  width*Integer.parseInt(xEditText)+Integer.parseInt(yEditText);
        myGridAdapter.setSeclection(pointId);
        myGridAdapter.notifyDataSetChanged();
    }
    //查看指纹库button1的onclick事件
    public void  dataCheck_event(View view){
        Intent intent = new Intent(this,SelectApActivity.class);
        this.startActivity(intent);
    }

    //参考点地图各Item的onclick事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String x=(Integer)position/width+"";
        String y=position%width+"";
        myGridAdapter.setPointSelecting(position);
        myGridAdapter.notifyDataSetChanged();
        xEdit.setText(x);
        yEdit.setText(y);
    }

}
