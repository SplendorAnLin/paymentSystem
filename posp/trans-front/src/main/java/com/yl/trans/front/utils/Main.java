package com.yl.trans.front.utils;

import sun.misc.BASE64Encoder;

/**
 * @author AnLin
 * @since 2017/7/4
 */
public class Main {

    public static void main(String[] args) throws Exception{
        long timestemp = System.currentTimeMillis();
        String roundStr = "1w43esa90231323";
        String part1 = String.valueOf(timestemp % 1000) + timestemp / (10*9);
        String part2 = roundStr.substring(1,5) + roundStr.substring(7,13);
        System.out.println(new BASE64Encoder().encode(encrypt(part1, part2).getBytes()));
    }

    public static String encrypt(String part1, String part2){
        char[] charArray1 = part1.toCharArray();
        char[] charArray2 = part2.toCharArray();
        char[] charArray;
        int tmpIndex = 0;
        if(charArray1.length > charArray2.length){
            charArray = new char[charArray1.length];
            for(int i =0;i<charArray1.length;i++){
                tmpIndex = i;
                if(tmpIndex >= charArray2.length -1){
                    tmpIndex = 0;
                }
                charArray[i]=(char)(charArray1[i]^charArray2[tmpIndex]);
            }
        }else{
            charArray = new char[charArray2.length];
            for(int i =0;i<charArray2.length;i++){
                tmpIndex = i;
                if(tmpIndex >= charArray1.length -1){
                    tmpIndex = 0;
                }
                charArray2[i]=(char)(charArray2[i]^charArray1[tmpIndex]);
            }
        }

        return String.valueOf(charArray);
    }
}
