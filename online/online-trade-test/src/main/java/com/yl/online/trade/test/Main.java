package com.yl.online.trade.test;

import com.lefu.commons.utils.lang.AmountUtils;

import java.math.RoundingMode;

public class Main {
    public static double compRatio(double amount, double fee) {
        double remitAmount = amount / (1 + fee);
        return AmountUtils.round(remitAmount, 2, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        int i = 100000;
        while (i >= 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    double amount = getRandomNumStr();
                    double remitAmount = compRatio(amount, 0.0032);
                    double fee = AmountUtils.round(AmountUtils.multiply(remitAmount, 0.0032), 2, RoundingMode.HALF_UP);
                    if (AmountUtils.less(amount, AmountUtils.add(remitAmount, fee))) {
                        remitAmount = AmountUtils.subtract(remitAmount, 0.01d);
                        if (AmountUtils.less(amount, AmountUtils.add(remitAmount, fee))) {
                            System.out.println(Thread.currentThread().getName() + " 可提现金额:" + remitAmount);
                            System.out.println(Thread.currentThread().getName() + " 手续费:" + fee);
                            System.out.println(Thread.currentThread().getName() + " 总费用费:" + AmountUtils.round(fee + remitAmount, 2, RoundingMode.HALF_UP));
                            System.out.println(Thread.currentThread().getName() + " 账户金额" + amount);
                        }
                    }
                }
            }).start();
            i--;
        }
    }

    public static double getRandomNumStr() {
        double randomNum = (Math.random() * 1 + 9) * 123456;
        return AmountUtils.round(randomNum, 2, RoundingMode.HALF_UP);
    }
}
