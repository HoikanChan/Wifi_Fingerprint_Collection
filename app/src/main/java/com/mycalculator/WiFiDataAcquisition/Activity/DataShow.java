package com.mycalculator.WiFiDataAcquisition.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mycalculator.WiFiDataAcquisition.Adapter.MyCursorAdapter;
import com.mycalculator.WiFiDataAcquisition.Adapter.MyListAdapter;
import com.mycalculator.WiFiDataAcquisition.Bean.FingerprintBean;
import com.mycalculator.WiFiDataAcquisition.Bean.ListBean;
import com.mycalculator.WiFiDataAcquisition.Bean.RoomSettings;
import com.mycalculator.WiFiDataAcquisition.Bean.WifiFinPtBean;
import com.mycalculator.WiFiDataAcquisition.Bean.XYItemBean;
import com.mycalculator.WiFiDataAcquisition.R;
import com.mycalculator.WiFiDataAcquisition.util.Constant;
import com.mycalculator.WiFiDataAcquisition.util.DbManager;
import com.mycalculator.WiFiDataAcquisition.util.MathManager;
import com.mycalculator.WiFiDataAcquisition.util.MySqlitehelper;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataShow extends AppCompatActivity {
    private ListView mList;
    private MySqlitehelper helper;
    private MyListAdapter adapter;
    private List<XYItemBean> listData;
    private List<ListBean> mdatas;
    private TextView disSum_text;
    private TextView simSum_text;
     String[] ssids;
    ArrayList<String> ssidNames;
    final String FILE_NAME2 = "/wifilist.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);
        mList = (ListView) findViewById(R.id.fingerprintList);
        simSum_text = (TextView) findViewById(R.id.cosSimSum_text);
        disSum_text = (TextView) findViewById(R.id.euDisSum_text);
        listData =new ArrayList<>();
        mdatas = new ArrayList<>();
        Intent intent =getIntent();
        ssidNames = intent.getStringArrayListExtra("ssids");
        initDatas(ssidNames);
        getVariance(ssidNames);
        adapter = new MyListAdapter(this,listData);
        mList.setAdapter(adapter);
    }

    private void initDatas(ArrayList<String> ssidNames){
        //1.根据selectDistinctAll找出各不相同的结果
        helper = DbManager.getInstance(this);
        mdatas = DbManager.selectDistinctAll(this,ssidNames);
        //2.根据不同条目的x，y，ssid，用selectByX_Y_SSID找出重复采样的结果。
        //3.再将重复采样的结果算出提取出rssi，算出rssi的方差，放入之前找出的
        //  不同结果的beanList中。
        for (ListBean bean:mdatas){
            List<FingerprintBean> repeats= DbManager.selectByX_Y_SSID(this,
                    bean.getX()+"",bean.getY()+"",bean.getSSID());
            int[] rssiArray = new int[repeats.size()];
            for (int i=0;i<repeats.size();i++){
                rssiArray[i] = repeats.get(i).getRSSI();
            }
            double variance = MathManager.getVariance(rssiArray);
            DecimalFormat df   =new DecimalFormat("#0.00");
            bean.setVariance(df.format(variance));
        }
        //4.再将listbean按照x，y坐标分类，放入XYItemBean
        int[][] xy = DbManager.selectXY(this);
        for (int i=0; i<xy.length;i++){
            XYItemBean xyItemBean=new XYItemBean();
            xyItemBean.setX(xy[i][0]);
            xyItemBean.setY(xy[i][1]);
            List<ListBean> beanList =new ArrayList<>();
            for(ListBean bean:mdatas){
                if (bean.getX() == xy[i][0] && bean.getY() == xy[i][1] ){
                    beanList.add(bean);
                    Log.i("initDatas", "initDatas: "+bean.toString());
                }
            }
            xyItemBean.setmDatas(beanList);
            Log.i("initDatas", "size: "+beanList.size());
            listData.add(xyItemBean);
        }
    }

    private void getVariance(ArrayList<String> ssidNames){
        //1.提取所有x，y坐标
        int[][] xy = DbManager.selectXY(this);
        Log.i("getVariance", "xy size: "+xy.length);
        if (xy.length <2){return;}
        //2.提取所有ssid名字
        //DbManager.selectRssi(this,xy);
        Log.i("getVariance", "ssidNames: "+ssidNames.toString()
                +"ssid siez: "+ssidNames.size());
        //3.提取所有坐标的rssi
        double[][] rssies =new double[xy.length][ssidNames.size()];
        Log.i("getVariance", mdatas.toString());
        for (int i=0;i<xy.length;i++){
            int x=xy[i][0];
            int y=xy[i][1];
            Log.i("getVariance", "x:"+x+",y:"+y);
            //if(x == 0 && y == 0 && xy[i+1][0] == 0 && xy[i+1][1] == 0)break;
            for (int j=0;j<ssidNames.size();j++){
                String ssid = ssidNames.get(j);
                for (ListBean mdata :mdatas){
                    //if (mdata.getSSID().equals(ssid))Log.i("getVariance", "mdata:"+mdata.getSSID()+", ssid:"+ssid);
                    if (mdata.getX() == x && mdata.getY() == y && mdata.getSSID().equals(ssid)){
                        rssies[i][j] = mdata.getAvgRssi();
                        //Log.i("getVariance", "***************************");
                    }
                }
            }
        }
        //4.计算欧式距离和余弦相似度
        double euDistanceSum =0.0,cosSimSum=0.0;
        int calTimes = 0 ;

        for(int i=0;i<rssies.length;i++){
            for (int j=i+1;j<rssies.length;j++){
                euDistanceSum+=MathManager.getEuDistance(rssies[i],rssies[j]);
                cosSimSum+=MathManager.getCosSimilarity(rssies[i],rssies[j]);
                calTimes++;
            }
            calTimes=calTimes+6;
        }
        cosSimSum= cosSimSum/calTimes;
        DecimalFormat df   =new DecimalFormat("#0.00");
        disSum_text.setText(df.format(euDistanceSum));
        simSum_text.setText(df.format(cosSimSum));
    }
    //指纹库清空的onclick事件
    public void  truncate_event(View view){
        DbManager.deleteAll(this);
        listData.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(this,"指纹库清空成功",Toast.LENGTH_SHORT).show();
    }
    //指纹库导出的onclick事件
    public void  save_event(View view){

        String content ="";
        RoomSettings  settings = (RoomSettings)getApplication();
        int times = settings.getTimes();
        content= content+"-----Times："+times+"-----\n";
        content= content+"X:Y:SSID:RSSI"+"\n";
        content= content+"______________"+"\n";
        Log.i("ssidNames", "save_event: "+ssidNames);
         List<FingerprintBean> dataSource= DbManager.selectByAP(this,ssidNames);
        for(String ssid:ssidNames){
            for (FingerprintBean bean:dataSource){
                if (bean.getSSID().equals(ssid))
                content+= bean.getX() + ":" + bean.getY() + ":" + bean.getSSID() + ":" + bean.getRSSI()
                        + "\n";
            }
        }
        try
        {
            // 如果手机插入了SD 卡，而且应用程序具有访问SD 的权限
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                // 获取SD 卡的目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(sdCardDir
                        .getCanonicalPath() + FILE_NAME2);
                // File targetFile1 = new File(sdCardDir
                // .getCanonicalPath() + FILE_NAME2);
                // 以指定文件创建 RandomAccessFile 对象
                RandomAccessFile raf = new RandomAccessFile(
                        targetFile, "rw");
                // 将文件记录指针移动到最后
                raf.seek(targetFile.length());
                // 输出文件内容
                raf.write(content.getBytes());
                // 关闭RandomAccessFile
                raf.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Toast.makeText(this,"指纹库已保存在本地",Toast.LENGTH_LONG).show();
    }

}
