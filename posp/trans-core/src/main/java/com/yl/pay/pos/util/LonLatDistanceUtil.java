/**
 * 
 */
package com.yl.pay.pos.util;

import com.yl.pay.pos.entity.LngAndLat;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014 
 * Company: com.yl.pay
 * 
 * @author zhendong.wu
 */
public class LonLatDistanceUtil {

private static final  double EARTH_RADIUS = 6371004;//赤道半径(单位m)  

/** 
     * 转化为弧度(rad) 
     * */  
    private static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  

    /** 
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下 
     * @param lonAndLat1 第一点的精度
     * @param lonAndLat2 第一点的纬度
     * @return 返回的距离，单位km
     * */  
    public static double GetDistance(LngAndLat lonAndLat1,LngAndLat lonAndLat2)  
    {  
       double radLat1 = rad(lonAndLat1.getLat());  
       double radLat2 = rad(lonAndLat2.getLat());  
       double latPoor = radLat1 - radLat2;  
       double lonPoor= rad(lonAndLat1.getLng()) - rad(lonAndLat2.getLng());  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(latPoor/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(lonPoor/2),2)));  
       s = s * EARTH_RADIUS;  
       s = Math.round(s * 10000) / 10000;  
       return s;  
    }
    
public static void main(String[] args) {

    System.out.println(GetDistance(new LngAndLat(108.647566,24.490442),new LngAndLat(108.657272678,24.4864640299))+"m");
    System.out.println(GetDistance(new LngAndLat(108.6572726780,24.4864640299),new LngAndLat(109.5562475586,22.270058865))+"m");
}

}
