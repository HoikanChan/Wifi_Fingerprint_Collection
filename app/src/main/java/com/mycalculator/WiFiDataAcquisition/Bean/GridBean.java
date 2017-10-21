package com.mycalculator.WiFiDataAcquisition.Bean;

/**
 * Created by lenovo on 2017/3/21.
 */
public class GridBean {
    private int cooridateX;
    private int cooridateY;

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    private int rssi;
    private int image;

    public GridBean(int image, int cooridateX, int cooridateY) {
        this.image = image;
        this.cooridateX = cooridateX;
        this.cooridateY = cooridateY;
    }





    public int getCooridateX() {
        return cooridateX;
    }

    public void setCooridateX(int cooridateX) {
        this.cooridateX = cooridateX;
    }

    public int getCooridateY() {
        return cooridateY;
    }

    public void setCooridateY(int cooridateY) {
        this.cooridateY = cooridateY;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
