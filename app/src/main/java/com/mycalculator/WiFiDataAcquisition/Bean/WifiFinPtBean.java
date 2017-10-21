package com.mycalculator.WiFiDataAcquisition.Bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/3/16.
 */
public class WifiFinPtBean implements Serializable{
    private int[][] xy;
    private int [][] strength;

    public int[][] getXy() {
        return xy;
    }

    public void setXy(int[][] xy) {
        this.xy = xy;
    }

    public int[][] getStrength() {
        return strength;
    }

    public void setStrength(int[][] strength) {
        this.strength = strength;
    }
}
