package com.yl.risk.core;


import java.text.NumberFormat;

public class Main {

    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留3位小数
        nf.setMaximumFractionDigits(4);
        System.out.println(nf.format(0.994567));

        System.out.println(new Double(0.003867));
    }

}
