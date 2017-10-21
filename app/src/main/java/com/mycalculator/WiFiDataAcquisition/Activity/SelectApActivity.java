package com.mycalculator.WiFiDataAcquisition.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.mycalculator.WiFiDataAcquisition.Adapter.MyListAdapter;
import com.mycalculator.WiFiDataAcquisition.Adapter.SelectListAdapter;
import com.mycalculator.WiFiDataAcquisition.Bean.ListBean;
import com.mycalculator.WiFiDataAcquisition.Bean.RoomSettings;
import com.mycalculator.WiFiDataAcquisition.R;
import com.mycalculator.WiFiDataAcquisition.util.DbManager;
import com.mycalculator.WiFiDataAcquisition.util.WIFI;

import java.util.ArrayList;
import java.util.List;

public class SelectApActivity extends AppCompatActivity {
    private ListView listView;
    private SelectListAdapter adapter;
    private List<ListBean> mdatas;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ap);
        listView = (ListView) findViewById(R.id.selectApListView);
        finishButton = (Button) findViewById(R.id.select_finish_button);
        initDatas();
        RoomSettings settings = (RoomSettings)getApplication();
        int times = settings.getTimes();

        adapter = new SelectListAdapter(this,mdatas,times);
        listView.setAdapter(adapter);

    }

    private void initDatas() {
        mdatas = DbManager.selectRssi(this);
       /* RoomSettings settings=(RoomSettings)getApplication();
        int times = settings.getTimes();
        for (ListBean mdata:mdatas){
            mdata.setQuantity(mdata.getQuantity()/times);
        }*/
    }
    //完成button的onclick事件
    public void select_finish_event(View view){
        ArrayList<String> ssids = adapter.getSsids();
        Intent intent = new Intent(this,DataShow.class);
        intent.putStringArrayListExtra("ssids",ssids);
        this.startActivity(intent);
    }
}
