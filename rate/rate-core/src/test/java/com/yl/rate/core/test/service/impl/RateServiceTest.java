package com.yl.rate.core.test.service.impl;

import com.yl.rate.core.test.BaseTest;
import org.junit.Test;

import javax.management.j2ee.statistics.TimeStatistic;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RateServiceTest extends BaseTest {

    @Test
    public void sel() {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
//            System.out.println("打印：" + d1.format(new Date()));
            System.out.println("打印2：" + df.parse(df.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
