package com.mycalculator.WiFiDataAcquisition.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenovo on 2017/3/19.
 */
public class MySqlitehelper extends SQLiteOpenHelper{
    public MySqlitehelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqlitehelper(Context context){
        super(context,Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);
    }

    //建表操作
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("tag","---------dataBase created----------");
        String sql = "create table " +
                Constant.TABLE_NAME+"(" +Constant.ID+" integer PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                Constant.COORIDATE_X+" Integer not null," +
                Constant.COORIDATE_Y+" Integer not null," +
                Constant.SSID+" varchar(50) not null,"+
                Constant.RSSI+" Integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("tag","---------dataBase upgraded----------");
    }
}
