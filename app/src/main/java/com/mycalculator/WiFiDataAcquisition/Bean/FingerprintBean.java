package com.mycalculator.WiFiDataAcquisition.Bean;

/**
 * Created by lenovo on 2017/3/26.
 */
public class FingerprintBean {
    private int _id;
    private int x;
    private int y;
    private String SSID;
    private int RSSI;

    @Override
    public String toString() {
        return "FingerprintBean{" +
                "_id=" + _id +
                ", x=" + x +
                ", y=" + y +
                ", SSID='" + SSID + '\'' +
                ", RSSI=" + RSSI +
                '}';
    }

    public FingerprintBean(int RSSI, String SSID, int y, int x) {
        this.RSSI = RSSI;
        this.SSID = SSID;
        this.y = y;
        this.x = x;
    }

    public String getSSID() {

        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getRSSI() {
        return RSSI;
    }

    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }
}
