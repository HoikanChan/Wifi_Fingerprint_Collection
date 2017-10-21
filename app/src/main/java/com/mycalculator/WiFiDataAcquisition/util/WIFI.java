package com.mycalculator.WiFiDataAcquisition.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.mycalculator.WiFiDataAcquisition.Bean.ListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/2/21.
 */
public class WIFI {
    List<ScanResult> results;
    WifiManager wm;
    WifiInfo wi;
    Boolean wifiEnabled;
    private MySqlitehelper helper;

    private String level;

    public WIFI(Context context) {
        wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wi = wm.getConnectionInfo();
        helper= DbManager.getInstance(context);
    }

    public Boolean getWifiState(){
        wifiEnabled = wm.isWifiEnabled();
        return wifiEnabled;
    }

    //打开wifi
    public void openWifi(){
        if(!wm.isWifiEnabled()){
            wm.setWifiEnabled(true);
        }
    }
    //关闭wifi
    public void closeWifi(){
        if(!wm.isWifiEnabled()){
            wm.setWifiEnabled(false);
        }
    }

    //扫描wifi情况并写入到数据库
    public void scanlist(final CustomProgressDialog dialog, final String x, final String y, final int times) {
        Thread submitThread=new Thread() {
            public void run() {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.beginTransaction();
                for(int i=0; i<=times ;i++){
                    if (i==times) {
                        dialog.dismiss();
                        break;}
                    wm.startScan();
                    results = wm.getScanResults();
                    for (ScanResult result : results) {
                        ContentValues values = new ContentValues();
                        values.put(Constant.COORIDATE_X,Integer.parseInt(x));
                        values.put(Constant.COORIDATE_Y,Integer.parseInt(y));
                        values.put(Constant.SSID,result.SSID);
                        values.put(Constant.RSSI,result.level);
                        long sqlResult = db.insert(Constant.TABLE_NAME,null,values);
                        if (sqlResult>0){
                            Log.i("TAG", "-------Inserted---------");
                        }else {
                            Log.i("TAG", "-------Insert failed---------");
                        }
                    }
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
            }
        };
        submitThread.start();
        if (!submitThread.isAlive()){
            dialog.dismiss();
        }
    }
}
