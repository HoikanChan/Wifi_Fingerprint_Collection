package com.mycalculator.WiFiDataAcquisition.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by lenovo on 2017/2/21.
 */
public class readTxt {
    public static int[][] xy = new int[10000][2];
    public static int[][] strength = new int[10000][5];
    public  static String[] wifiNames ={"Annies","Annies2.4G","Annies5G","hlsshop","MERCURY_5G_24D5"};
    static String filePath = "C:\\wifilist.txt";




    public static void test(){

        String filePath = "C:\\wifilist.txt";

        readTxtFile(filePath);
        for(int i=0;i<xy.length;i++){

            if(xy[i][0] == 0 && xy[i+1][0] == 0 && xy[i+2][0] == 0){
                System.out.println(i);
                break;
            }
            Log.d("坐标",xy[i][0]+" "+xy[i][1]+"\n");
        }

        for(int i=0;i<strength.length;i++){

            if(strength[i][0] == 0 && strength[i+1][0] == 0 && strength[i+2][0] == 0){
                System.out.println(i);
                break;
            }
            Log.d("强度",strength[i][0]+" "+strength[i][1]+" "+strength[i][2]+" "+strength[i][3]+" "+strength[i][4]+"\n");
        }
    }

    public static int nextPos(int xy[][]) {
        int i;
        for(i=0;i<xy.length;i++){
            if(xy[i][1] == 0 && xy[i][0] == 0 ){
//    			System.out.println(""+i);
                break;
            }
//    		System.out.println(i);
        }
        return i;
    }

    public static int duplicPos(int xy[][],int x,int y,int p) {
        int i;
        for(i=0;i<=p;i++){
            if(xy[i][0] == x && xy[i][1] == y){
                break;
            }
        }
        if(i<=p){    //意味着之前已经有了该位置数据
            return i+1;
        }else{       //说明有新位置
            return 0;
        }
    }
    public static void readTxtFile(String filePath){
        int nextP=0,duplicP;
        int x = 0,y = 0;
        String s1=wifiNames[0];
        String s2=wifiNames[1];
        String s3=wifiNames[2];
        String s4=wifiNames[3];
        String s5=wifiNames[4];

        try {
            String encoding="GBK";
            File file=new File(filePath);

            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt ;

                while((lineTxt = bufferedReader.readLine()) != null){
                    String[] line = lineTxt.split(":");

                    if(line.length == 4){
                        x = Integer.parseInt(line[0]);

                        y = Integer.parseInt(line[1]);
                    }else{
                        break;
                    }
                    //返回当前第一个为零的坐标
                    nextP=nextPos(xy);
                    System.out.println("返回当前第一个为零的坐标"+nextP);
                    //坐标已重复出现位置
                    duplicP=duplicPos(xy,x,y,nextP);
                    System.out.println("坐标已重复出现位置:"+duplicP);

                    if(duplicP==0){
                        xy[nextP][0]=x;
                        xy[nextP][1]=y;
                        if(line[2].equals(s1)){
                            strength[nextP][0]=Integer.parseInt(line[3]);
                        }
                        if(line[2].equals(s2)){
                            strength[nextP][1]=Integer.parseInt(line[3]);
                        }
                        if(line[2].equals(s3)){
                            strength[nextP][2]=Integer.parseInt(line[3]);
                        }
                        if(line[2].equals(s4)){
                            strength[nextP][3]=Integer.parseInt(line[3]);
                        }
                        if(line[2].equals(s5)){
                            strength[nextP][4]=Integer.parseInt(line[3]);
                        }
                    }else{
                        duplicP=duplicP-1;
                        if(line[2].equals(s1)){
                            strength[duplicP][0]=Integer.parseInt(line[3]);
                        }
                        if(line[2].equals(s2)){
                            strength[duplicP][1]=Integer.parseInt(line[3]);
                        }
                        if(line[2].equals(s3)){
                            strength[duplicP][2]=Integer.parseInt(line[3]);
                        }
                        if(line[2].equals(s4)){
                            strength[duplicP][3]=Integer.parseInt(line[3]);
                        }
                        if(line[2].equals(s5)){
                            strength[duplicP][4]=Integer.parseInt(line[3]);
                        }

                    }
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }
}
