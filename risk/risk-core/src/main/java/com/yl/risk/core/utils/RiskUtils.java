package com.yl.risk.core.utils;

import com.pay.common.util.AmountUtil;
import com.yl.risk.core.bean.LocationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 风控综合计算信息获取工具
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/17
 */
public class RiskUtils implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(RiskUtils.class);
    private static Properties prop = new Properties();
    static {
        try {
            prop.load(
                    new InputStreamReader(RiskUtils.class.getClassLoader().getResourceAsStream("system.properties")));
        } catch (IOException e) {
            logger.error("prop加载失败{}", e);
        }
    }
    private static boolean enableFileWatch = false;
    private static File ipFile;
    private static Long lastModifyTime = 0L;
    private static ReentrantLock lock = new ReentrantLock();
    private static int offset;
    private static int[] index = new int[256];
    private static ByteBuffer dataBuffer;
    private static ByteBuffer indexBuffer;

    /**
     * 借贷比例 + 差额计算
     *
     * @param proportion  借贷比例
     * @param settings    设置值
     * @param discrepancy 信用卡 借记卡交易金额差
     * @param setAmount   设置差额
     * @return false 校验通过   true 校验失败
     */
    public static boolean lendingRatio(Double proportion, Double settings, Double discrepancy, Double setAmount) {
        boolean flag = false;
        if (AmountUtil.bigger(proportion, settings)) {
            if (AmountUtil.bigger(discrepancy, setAmount)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * IP 地址获取
     *
     * @param ip IP地址
     * @return state 省份, city 城市, country 国家
     */
    public static LocationInfo getIPInfo(String ip) {
        load(prop.getProperty("dbFilePath"));
        return find(ip);
    }

    private static LocationInfo conver(String[] info) {
        LocationInfo locationInfo = new LocationInfo();
        if (info != null){
            if (info[0] != null && !"".equals(info[0])){
                locationInfo.setCountry(info[0]);
            }
            if (info[1] != null && !"".equals(info[1])){
                locationInfo.setState(info[1]);
            }
            if (info[2] != null && !"".equals(info[2])){
                locationInfo.setCity(info[2]);
            }
            if (info[3] != null && !"".equals(info[3])){
                locationInfo.setIsp(info[3]);
            }
        }
        return locationInfo;
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        System.out.println(getIPInfo("111.172.246.33").toString());
        System.out.println(getIPInfo("61.135.217.7").toString());
        System.out.println(getIPInfo("182.46.217.90").toString());
        System.out.println(getIPInfo("27.40.154.193").toString());
        System.out.println(getIPInfo("60.255.186.169").toString());
        System.out.println(getIPInfo("61.158.111.142").toString());
        System.out.println(getIPInfo("125.211.202.26").toString());
        System.out.println(getIPInfo("118.254.142.42").toString());
        System.out.println(getIPInfo("182.121.206.124").toString());
        System.out.println(getIPInfo("125.46.0.62").toString());
        System.out.println(System.currentTimeMillis() - start + " MS!");
    }

    public static void load(String filename) {
        ipFile = new File(filename);
        load();
        if (enableFileWatch) {
            Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    long time = ipFile.lastModified();
                    if (time > lastModifyTime) {
                        lastModifyTime = time;
                        load();
                    }
                }
            }, 1000L, 5000L, TimeUnit.MILLISECONDS);
        }
    }

    private static void load() {
        lastModifyTime = ipFile.lastModified();
        FileInputStream fin = null;
        lock.lock();
        try {
            dataBuffer = ByteBuffer.allocate(Long.valueOf(ipFile.length()).intValue());
            fin = new FileInputStream(ipFile);
            int readBytesLength;
            byte[] chunk = new byte[4096];
            while (fin.available() > 0) {
                readBytesLength = fin.read(chunk);
                dataBuffer.put(chunk, 0, readBytesLength);
            }
            dataBuffer.position(0);
            int indexLength = dataBuffer.getInt();
            byte[] indexBytes = new byte[indexLength];
            dataBuffer.get(indexBytes, 0, indexLength - 4);
            indexBuffer = ByteBuffer.wrap(indexBytes);
            indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
            offset = indexLength;

            int loop = 0;
            while (loop++ < 256) {
                index[loop - 1] = indexBuffer.getInt();
            }
            indexBuffer.order(ByteOrder.BIG_ENDIAN);
        } catch (IOException ioe) {
            logger.error("IO 读取异常{}", ioe);
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                logger.error("文件加载异常！{}", e);
            }
            lock.unlock();
        }
    }

    public static LocationInfo find(String ip) {
        int ip_prefix_value = new Integer(ip.substring(0, ip.indexOf(".")));
        long ip2long_value = ip2long(ip);
        int start = index[ip_prefix_value];
        int max_comp_len = offset - 1028;
        long index_offset = -1;
        int index_length = -1;
        byte b = 0;
        for (start = start * 8 + 1024; start < max_comp_len; start += 8) {
            if (int2long(indexBuffer.getInt(start)) >= ip2long_value) {
                index_offset = bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5), indexBuffer.get(start + 4));
                index_length = 0xFF & indexBuffer.get(start + 7);
                break;
            }
        }
        byte[] areaBytes;
        lock.lock();
        try {
            dataBuffer.position(offset + (int) index_offset - 1024);
            areaBytes = new byte[index_length];
            dataBuffer.get(areaBytes, 0, index_length);
        } finally {
            lock.unlock();
        }
        String[] arg = new String(areaBytes, Charset.forName("UTF-8")).split("\t", -1);
        return conver(arg);
    }

    private static int str2Ip(String ip) {
        String[] ss = ip.split("\\.");
        int a, b, c, d;
        a = Integer.parseInt(ss[0]);
        b = Integer.parseInt(ss[1]);
        c = Integer.parseInt(ss[2]);
        d = Integer.parseInt(ss[3]);
        return (a << 24) | (b << 16) | (c << 8) | d;
    }

    private static long bytesToLong(byte a, byte b, byte c, byte d) {
        return int2long((((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff)));
    }

    private static long int2long(int i) {
        long l = i & 0x7fffffffL;
        if (i < 0) {
            l |= 0x080000000L;
        }
        return l;
    }

    private static long ip2long(String ip) {
        return int2long(str2Ip(ip));
    }
}