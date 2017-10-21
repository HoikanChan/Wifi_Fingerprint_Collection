package com.mycalculator.WiFiDataAcquisition.Bean;

/**
 * Created by lenovo on 2017/3/28.
 */
public class ListBean {

    private int x;
    private int y;
    private int quantity;
    private String SSID;
    private double avgRssi;
    private String variance;


    @Override
    public String toString() {
        return "ListBean{" +
                "x=" + x +
                ", y=" + y +
                ", SSID='" + SSID + '\'' +
                ", avgRssi=" + avgRssi +
                ", variance='" + variance + '\'' +
                '}';
    }

    public ListBean(String SSID, double avgRssi, int quantity) {
        this.quantity = quantity;
        this.avgRssi = avgRssi;
        this.SSID = SSID;
    }

    public ListBean(int x, int y, String SSID, int quantity, double avgRssi) {
        this.y = y;
        this.x = x;
        this.quantity = quantity;
        this.SSID = SSID;
        this.avgRssi = avgRssi;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getAvgRssi() {
        return avgRssi;
    }

    public void setAvgRssi(double avgRssi) {
        this.avgRssi = avgRssi;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getVariance() {
        return variance;
    }

    public void setVariance(String variance) {
        this.variance = variance;
    }
}
