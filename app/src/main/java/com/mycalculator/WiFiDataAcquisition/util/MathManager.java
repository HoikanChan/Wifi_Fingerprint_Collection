package com.mycalculator.WiFiDataAcquisition.util;

import android.text.LoginFilter;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by lenovo on 2017/3/27.
 */
public class MathManager {
    //计算数组平方和
    public static double getsqrSum(double[] array){
        double sqrsum = 0.0;
        for (int i = 0; i <array.length; i++) {
            sqrsum = sqrsum + array[i] * array[i];
        }
        return sqrsum;
    }
    //计算数组平均值
    public static double getAverage(int[] array){
        if(array.length == 0){
            Log.i("MathManager", "getAverage:array's length is 0");
            return 0;
        }
        int sum = 0;
        for(int i = 0;i < array.length;i++){
            sum += array[i];
        }
        //Log.i("MathManager", "getAverage: "+sum / array.length);
        return (double)(sum / array.length);
    }
    //计算数组方差
    public static double getVariance(int[] array){
        if(array.length == 0){
            Log.i("MathManager", "getVariance:array's length is 0");
            return 0;
        }
        int len=array.length;
        double[] differArray = new double[len];
        double  average=getAverage(array);
        for(int i = 0;i < array.length;i++){
            differArray[i]=array[i]-average;
        }
        double sqrsum = getsqrSum(differArray),result = 0.0;
        result = sqrsum / len;
        return result;
    }
    //计算数组之间欧式距离和
    public static double getEuDistance(double[] arrayA,double[] arrayB){
        double euDistance=0.0,sqrDiffer=0.0;
        for (int i=0;i<arrayA.length;i++){
            sqrDiffer+=(arrayA[i]-arrayB[i])*(arrayA[i]-arrayB[i]);
            //Log.i("MathManager", "sqrDiffer:"+sqrDiffer);
        }
        euDistance =Math.sqrt(sqrDiffer);
        return euDistance;
    }
    //计算数组之间余弦相似度
    public static double getCosSimilarity(double[] arrayA,double[] arrayB){
        double mulsum=0.0,cosSim=0.0,sqrsumA=getsqrSum(arrayA),sqrsumB=getsqrSum(arrayB);
        for (int i=0;i<arrayA.length;i++){
            mulsum+=arrayA[i]*arrayB[i];
        }
        if (sqrsumA == 0 || sqrsumB== 0){
            return -100.0;
        }
        cosSim=mulsum/(Math.sqrt(sqrsumA)*Math.sqrt(sqrsumB));
        return cosSim;
    }
}
