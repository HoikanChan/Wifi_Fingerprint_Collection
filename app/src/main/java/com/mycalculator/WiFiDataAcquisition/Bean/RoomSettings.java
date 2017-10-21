package com.mycalculator.WiFiDataAcquisition.Bean;

import android.app.Application;

/**
 * Created by lenovo on 2017/2/23.
 */
public class RoomSettings extends Application{
    private float length;
    private float width;
    private int times;

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
