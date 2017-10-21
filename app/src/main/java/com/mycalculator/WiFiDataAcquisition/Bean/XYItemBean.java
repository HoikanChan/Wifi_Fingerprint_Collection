package com.mycalculator.WiFiDataAcquisition.Bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/12.
 */
public class XYItemBean {
    private int x;
    private int y;
    private List<ListBean> mDatas;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public List<ListBean> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<ListBean> mDatas) {
        this.mDatas = mDatas;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
