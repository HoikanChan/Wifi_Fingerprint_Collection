package com.mycalculator.WiFiDataAcquisition.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.util.Log;

import com.mycalculator.WiFiDataAcquisition.Bean.FingerprintBean;
import com.mycalculator.WiFiDataAcquisition.Bean.ListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/3/19.
 */
public class DbManager {
    private static MySqlitehelper mySqlitehelper;
    public static MySqlitehelper getInstance(Context context){
        if(mySqlitehelper == null){
            mySqlitehelper = new MySqlitehelper(context);
        }

        return mySqlitehelper;
    }
    //将SQL执行结果cursor转换为List
    public static List<FingerprintBean> cursorToList(Cursor cursor){
        List<FingerprintBean> list = new ArrayList<FingerprintBean>();
        if (cursor.getCount() == 0){
            Log.i("cursorToList", "cursorToList: cursor's count is 0");
        }
        while (cursor.moveToNext()){
            //int _id =cursor.getInt(cursor.getColumnIndex(Constant.ID));
            int x=cursor.getInt(cursor.getColumnIndex(Constant.COORIDATE_X));
            int y=cursor.getInt(cursor.getColumnIndex(Constant.COORIDATE_Y));
            String ssid=cursor.getString(cursor.getColumnIndex(Constant.SSID));
            int rssi=cursor.getInt(cursor.getColumnIndex(Constant.RSSI));
            FingerprintBean bean = new FingerprintBean(rssi,ssid,y,x);
            list.add(bean);
        }
        return list;
    }
    //将SQL执行结果cursor转换为List
    public static List<ListBean> cursorToListBeanList(Cursor cursor){
        List<ListBean> list = new ArrayList<ListBean>();
        if (cursor.getCount() == 0){
            Log.i("cursorToList", "cursorToList: cursor's count is 0");
        }
        Log.i("cursorToList", cursor.getCount()+"");
        while (cursor.moveToNext()){
            int times=cursor.getInt(cursor.getColumnIndex("times"));
            int x=cursor.getInt(cursor.getColumnIndex(Constant.COORIDATE_X));
            int y=cursor.getInt(cursor.getColumnIndex(Constant.COORIDATE_Y));
            String ssid=cursor.getString(cursor.getColumnIndex(Constant.SSID));
            int rssi=cursor.getInt(cursor.getColumnIndex(Constant.RSSI));
            ListBean bean = new ListBean(x,y,ssid,times,rssi);
            list.add(bean);
        }
        for (ListBean bean:list){
           // Log.i("cursorToList", bean.toString());
        }
        return list;
    }

    //根据x，y，ssid，删除特定指纹
    public static void delete(Context context,int x,int y){
        MySqlitehelper helper = getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] args ={x+"",y+""};
        int count =db.delete(Constant.TABLE_NAME,Constant.COORIDATE_X+"=? and "
                +Constant.COORIDATE_Y+"=? ",args);
        if (count>0){
            Log.i("delete", "delete: succeed");
        }else {
            Log.i("delete", "delete: fail");
        }
        db.close();
    }

    //清空指纹库
    public static void deleteAll(Context context){
        MySqlitehelper helper = getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        int count =db.delete(Constant.TABLE_NAME,null,null);
        if (count>0){
            Log.i("delete", "delete all: succeed");
        }else {
            Log.i("delete", "delete all: fail");
        }
        db.close();
    }

    //选择所有指纹数据
    public static List<FingerprintBean> selectAll(Context context){
        MySqlitehelper helper = getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from "+ Constant.TABLE_NAME,null);
        List<FingerprintBean> beanList = cursorToList(cursor);
        db.close();
        return beanList;
    }

    //根据x，y，ssid选择特定指纹
    public static List<FingerprintBean> selectByX_Y_SSID(Context context,String x,String y,String SSID){
        MySqlitehelper helper = getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] selectionArgs ={x,y,SSID};
        Cursor cursor = db.rawQuery("select * from "+ Constant.TABLE_NAME+
                " where "+Constant.COORIDATE_X+" =? and "+Constant.COORIDATE_Y+" =? and "
                +Constant.SSID+" =?",selectionArgs);
        List<FingerprintBean> beanList = cursorToList(cursor);
        db.close();
        return beanList;
    }

    //根据ssid的所有指纹
    public static List<FingerprintBean> selectBySsid(Context context,String SSID){
        MySqlitehelper helper = getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select x,y,ssid,avg(rssi) as rssi" +
                " from "+ Constant.TABLE_NAME+
                " where " +Constant.SSID+" =?"+"group by x,y",new String[]{SSID});
        List<FingerprintBean> beanList = cursorToList(cursor);
        db.close();
        return beanList;
    }

    //根据指定AP，选择所有x，y，ssid不同的指纹
    public static List<ListBean> selectDistinctAll(Context context,ArrayList<String> ssids){
        MySqlitehelper helper = getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        StringBuffer sql = new StringBuffer("select x,y,ssid,avg(rssi) as rssi,count(rssi) as times from "
                + Constant.TABLE_NAME+" where ssid in(?");
        for (int i = 1; i < ssids.size(); i++) {
            sql.append(",?");
        }
        sql.append(") group by x,y,ssid");
        Cursor cursor = db.rawQuery(sql.toString(),(String[])ssids.toArray(new String[ssids.size()]));
        Log.i("select", "selectDistinctAll: "+cursor.getCount());
        List<ListBean> beanList = cursorToListBeanList(cursor);
        db.close();
        return beanList;
    }

    //根据指定AP，选择所有指纹
    public static List<FingerprintBean> selectByAP(Context context,ArrayList<String> ssids){
        MySqlitehelper helper = getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        StringBuffer sql = new StringBuffer("select x,y,ssid,rssi from "
                + Constant.TABLE_NAME+" where ssid in(?");
        for (int i = 1; i < ssids.size(); i++) {
            sql.append(",?");
        }
        sql.append(")");
        Cursor cursor = db.rawQuery(sql.toString(),(String[])ssids.toArray(new String[ssids.size()]));
        List<FingerprintBean> beanList = cursorToList(cursor);
        db.close();
        return beanList;
    }

    //选择所有x，y坐标
    public static int[][] selectXY(Context context){
        MySqlitehelper helper = getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select x,y,count(x) as quantity from "
                + Constant.TABLE_NAME+" group by x,y",null);
        Log.i("select", "selectDistinctAllXY: "+cursor.getCount());
        int[][] xy =new int[cursor.getCount()][2] ;
        if (cursor.getCount() == 0){
            Log.i("cursorToList", "cursorToList: cursor's count is 0");
        }
        for(int i=0;cursor.moveToNext();i++){
            xy[i][0]=cursor.getInt(cursor.getColumnIndex(Constant.COORIDATE_X));
            xy[i][1]=cursor.getInt(cursor.getColumnIndex(Constant.COORIDATE_Y));
        }
        db.close();
        return xy;
    }
    //按rssi进行查询
    public static List<ListBean> selectRssi(Context context){
        MySqlitehelper helper = getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        List<String> ssids =new ArrayList<>();
        Cursor cursor = db.rawQuery("select ssid ,avg(rssi) as rssi,count(rssi) as times " +
                "from "+ Constant.TABLE_NAME+" group by ssid"
                ,null);
        List<ListBean> list = new ArrayList<ListBean>();
        while (cursor.moveToNext()){
            int times=cursor.getInt(cursor.getColumnIndex("times"));
            String ssid=cursor.getString(cursor.getColumnIndex(Constant.SSID));
            int rssi=cursor.getInt(cursor.getColumnIndex(Constant.RSSI));
            ListBean bean = new ListBean(ssid,rssi,times);
            list.add(bean);
        }
        db.close();
        return list;
    }

}
